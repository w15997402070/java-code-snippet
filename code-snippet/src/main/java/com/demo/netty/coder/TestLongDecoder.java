package com.demo.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 解码器,将ByteBuf解析成Long
 * @author wang
 */
public class TestLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("["+LocalDateTime.now().toString()+"]进入解码器");
        int length = in.readableBytes();
        if (length >= 8){
            long l = in.readLong();
            System.out.println("["+LocalDateTime.now().toString()+"]解码后的消息为 : "+l);
            out.add(l);
        }
    }
}
