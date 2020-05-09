package com.demo.netty.firstdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class TestServerInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new StringDecoder());
        pipeline.addLast("testHttpServletHandler",new FirstHandler());
    }
}
