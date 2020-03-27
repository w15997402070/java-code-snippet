package com.demo.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.time.LocalDateTime;

/**
 * @author wang
 */
public class TestLongEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("["+LocalDateTime.now().toString()+"]进入编码器");
        System.out.println("["+LocalDateTime.now().toString()+"]编码消息为 : "+msg);
        out.writeLong(msg);
    }
}
