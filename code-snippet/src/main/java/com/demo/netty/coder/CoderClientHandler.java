package com.demo.netty.coder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;


/**
 * @author wang
 */
@Slf4j
public class CoderClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
      System.out.println("["+LocalDateTime.now().toString()+"]客户端接收到消息 : "+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(123456L);
    }
}
