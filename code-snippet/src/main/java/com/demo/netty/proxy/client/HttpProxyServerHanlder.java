package com.demo.netty.proxy.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.QueryStringEncoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.apache.http.MethodNotSupportedException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpProxyServerHanlder extends SimpleChannelInboundHandler<HttpObject> {

    public  static  FullHttpResponse response = null;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            FullHttpRequest fullHttpRequest = (FullHttpRequest)msg;
            Map<String, String> paramMap = parse(fullHttpRequest);
            String uri = fullHttpRequest.uri();
            String method = fullHttpRequest.method().name();
            HttpRequest request = getRequestMethod(paramMap, uri, method);
            boolean run = new HttpProxyClient().run(uri, request);
            if (run && response != null){
                ctx.writeAndFlush(response);
            }else {
                ctx.writeAndFlush(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK));
            }
        }else {
            System.out.println("msg : "+msg);
        }

    }

    /**
     * 解析请求参数
     * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
     *
     * @throws IOException
     */
    public Map<String, String> parse(FullHttpRequest fullReq) throws IOException {
        HttpMethod method = fullReq.method();

        Map<String, String> parmMap = new HashMap<>();

        if (HttpMethod.GET == method) {
            // 是GET请求
            QueryStringDecoder decoder = new QueryStringDecoder(fullReq.uri());
            decoder.parameters().entrySet().forEach( entry -> {
                // entry.getValue()是一个List, 只取第一个元素
                parmMap.put(entry.getKey(), entry.getValue().get(0));
            });
        } else if (HttpMethod.POST == method) {
            // 是POST请求
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullReq);
            decoder.offer(fullReq);

            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();

            for (InterfaceHttpData parm : parmList) {

                Attribute data = (Attribute) parm;
                parmMap.put(data.getName(), data.getValue());
            }

        } else {

        }
        return parmMap;
    }
    public static HttpRequest getRequestMethod(Map<String, String> parameter,  String url, String method) throws HttpPostRequestEncoder.ErrorDataEncoderException {
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
        } else {
            System.err.println("this method is not support!");
        }
        return request;
    }

}
