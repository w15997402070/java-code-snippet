package com.demo.netty.proxy.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class HttpClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            System.out.println("STATUS: " + response.getStatus());
            System.out.println("VERSION: " + response.getProtocolVersion());
            System.out.println();

            if (!response.headers().isEmpty()) {
                for (String name : response.headers().names()) {
                    for (String value : response.headers().getAll(name)) {
                        System.out.println("HEADER: " + name + " = " + value);
                    }
                }
                System.out.println();
            }

            if (HttpHeaders.isTransferEncodingChunked(response)) {
                System.out.println("CHUNKED CONTENT {");
            } else {
                System.out.println("CONTENT {");
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;

//            System.out.print(content.content().toString(CharsetUtil.UTF_8));
            System.out.flush();

            if (content instanceof LastHttpContent) {
                System.out.println("} END OF CONTENT");
            }
        }
//        ctx.writeAndFlush("成功");
    }

    public void doChannelRead(ChannelHandlerContext ctx, FullHttpResponse msg){
        HttpProxyServerHanlder.response = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
        doChannelRead(ctx, msg);
    }
}
