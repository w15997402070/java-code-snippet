package com.demo.netty.file;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class FileInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8))
                .addLast(new LineBasedFrameDecoder(1024))
                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                .addLast(new FileServerHandler());
    }
}
