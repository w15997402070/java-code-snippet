package com.demo.netty.firstdemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketAddress;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author wang
 */
@Slf4j
public class TestHttpServletHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream output = null;
        try {
            if (msg instanceof HttpRequest){
                HttpRequest request = (HttpRequest)msg;
                String uri = request.uri();
                log.info("uri : "+uri);
                HttpMethod method = request.method();
                log.info("Method : "+method.name());
                String headerHost = request.headers().get("Host");
                log.info("headerHost: "+ headerHost);
                String host = "";
                //端口默认80
                int port = 80;
                //可能有请求是 host:port的情况，
                String[] split = headerHost.split(":");
                host = split[0];
                if (split.length > 1) {
                    port = Integer.valueOf(split[1]);
                }
                HttpHeaders headers = request.headers();
                if (HttpMethod.GET.equals(method)){
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    HttpGet httpGet = new HttpGet(uri);
                    String scheme = httpGet.getURI().getScheme() == null ? "http" : httpGet.getURI().getScheme();
                    if ("http".equalsIgnoreCase(scheme)) {
                        httpclient = HttpClients.createDefault();
                    } else if ("https".equalsIgnoreCase(scheme)) {
                        httpclient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).build();
                    }
                    CloseableHttpResponse response1 = httpclient.execute(httpGet);
                    log.info("statusCode : "+response1.getStatusLine().getStatusCode());
                    //返回的数据
                    inputStream = response1.getEntity().getContent();
                    output = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024*4];
                    int n = 0;
                    while (-1 != (n = inputStream.read(buffer))) {
                        output.write(buffer, 0, n);
                    }
                    ByteBuf byteBuf = Unpooled.copiedBuffer(output.toByteArray());
                    //协议版本
                    ProtocolVersion protocolVersion = response1.getProtocolVersion();
                    boolean keep_alive = false;
                    try {
                        String connection = response1.getFirstHeader("Connection").getValue();
                        if ("keep-alive".equalsIgnoreCase(connection)){
                            keep_alive = true;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    HttpVersion httpVersion = new HttpVersion(protocolVersion.getProtocol(),
                            protocolVersion.getMajor(),
                            protocolVersion.getMinor(),
                            keep_alive);
                    FullHttpResponse response = new DefaultFullHttpResponse(httpVersion, HttpResponseStatus.OK,byteBuf);
                    Header[] allHeaders = response1.getAllHeaders();
                    for (Header h : allHeaders) {
                        response.headers().set(h.getName(),h.getValue());
                    }
                    ctx.writeAndFlush(response);
                }else if (HttpMethod.POST.equals(method)){
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
                    response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
                    response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
                    ctx.writeAndFlush(response);
                }
            }
        }finally {
            if (inputStream != null){
                inputStream.close();
            }
            if (output != null){
                output.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }
}
