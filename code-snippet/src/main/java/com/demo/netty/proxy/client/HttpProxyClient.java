package com.demo.netty.proxy.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.ClientCookieEncoder;
import io.netty.handler.codec.http.DefaultCookie;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringEncoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpProxyClient {

    public static void main(String[] args) throws HttpPostRequestEncoder.ErrorDataEncoderException, InterruptedException {

        String url = "https://blog.csdn.net/ToBeTheEnder/article/details/52987025";
        Map<String, String> getData = new HashMap<String, String>();
//        getData.put("tags", "806:938356;");
//        getData.put("sort", "_p");

        HttpRequest get = getRequestMethod(getData, url, "get");
        new HttpProxyClient().run(url, get);
    }

    public static HttpRequest getRequestMethod(Map<String, String> parameter, String url, String method) throws HttpPostRequestEncoder.ErrorDataEncoderException {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        String path = uri.getRawPath();
        String host = uri.getHost();

        HttpRequest request = null;
        if ("post".equalsIgnoreCase(method)) {
            request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.POST, path);

            HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
            // This encoder will help to encode Request for a FORM as POST.
            HttpPostRequestEncoder bodyRequestEncoder = new HttpPostRequestEncoder(factory, request, false);
            // add Form attribute
            if (parameter != null) {
                Set<Map.Entry<String, String>> entrySet = parameter.entrySet();
                for (Map.Entry<String, String> e : entrySet) {
                    String key = e.getKey();
                    String value = e.getValue();
                    bodyRequestEncoder.addBodyAttribute(key, value);
                }
                try {
                    request = bodyRequestEncoder.finalizeRequest();
                } catch (HttpPostRequestEncoder.ErrorDataEncoderException e) {
                    // if an encoding error occurs
                    e.printStackTrace();
                }
            }

            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
            request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
            request.headers().set(HttpHeaders.Names.COOKIE, ClientCookieEncoder.encode(
                    new DefaultCookie("my-cookie", "foo"),
                    new DefaultCookie("another-cookie", "bar")));
        } else if ("get".equalsIgnoreCase(method)) {
            //uri.toString()没有查询参数的uri
            QueryStringEncoder encoder = new QueryStringEncoder(uri.toString());
            if (parameter != null) {
                Set<Map.Entry<String, String>> entrySet = parameter.entrySet();
                for (Map.Entry<String, String> e : entrySet) {
                    String key = e.getKey();
                    String value = e.getValue();
                    encoder.addParam(key, value);
                }
            }
            //encoder.toString()有查询参数的uri
            request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.GET, encoder.toString());
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaders.Names.HOST, host);
            headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
            headers.set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP.toString() + ','
                    + HttpHeaders.Values.DEFLATE.toString());

            headers.set(HttpHeaders.Names.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaders.Names.ACCEPT_LANGUAGE, "fr");
            headers.set(HttpHeaders.Names.USER_AGENT, "Netty Simple Http Client side");
            headers.set(HttpHeaders.Names.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            headers.set(HttpHeaders.Names.COOKIE, ClientCookieEncoder.encode(
                    new DefaultCookie("my-cookie", "foo"),
                    new DefaultCookie("another-cookie", "bar"))
            );
        } else {
            System.err.println("this method is not support!");
        }
        return request;
    }

    public boolean run(String url, HttpRequest request) throws HttpPostRequestEncoder.ErrorDataEncoderException, InterruptedException {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
        String host = uri.getHost() == null ? "localhost" : uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }
        if (port == -1){
            return false;
        }

        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
            System.err.println("Only HTTP(S) is supported.");
        }

        boolean ssl = "https".equalsIgnoreCase(scheme);

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HttpClientInitializer(ssl));
            // Make the connection attempt.
            Channel ch = b.connect(host, port).sync().channel();
            ChannelPromise channelPromise = ch.newPromise();
            channelPromise.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("请求成功");
                    } else {
                        channelFuture.cancel(true);
                    }
                }
            });
            // send request
            ch.writeAndFlush(request).sync();
            ch.closeFuture().sync();
            System.out.println("请求成功2");
            return true;
        } finally {
            group.shutdownGracefully();
        }
    }
}
