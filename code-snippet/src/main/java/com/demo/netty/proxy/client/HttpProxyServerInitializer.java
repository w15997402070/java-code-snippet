package com.demo.netty.proxy.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpProxyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast("request-encoder", new HttpServerCodec());

        p.addLast("response-decoder", new HttpResponseDecoder());

        // Remove the following line if you don't want automatic content decompression.
//        p.addLast("inflater", new HttpContentDecompressor());

        //HttpObjectAggregator会把多个消息转换为 一个单一的FullHttpRequest或是FullHttpResponse
        p.addLast("aggregator", new HttpObjectAggregator(1048576));
        p.addLast("httpServerHandler",new HttpProxyServerHanlder());

    }
}
