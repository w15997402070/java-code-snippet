package com.demo.netty.coder;

import com.demo.netty.chat.ChatServerhandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class CoderServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new TestLongDecoder());
        pipeline.addLast(new TestLongEncoder());
        pipeline.addLast(new LongEncoderHandler());
    }
}
