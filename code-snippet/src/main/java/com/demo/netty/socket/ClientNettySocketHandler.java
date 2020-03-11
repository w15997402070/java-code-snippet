package com.demo.netty.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author wang
 */
@Slf4j
public class ClientNettySocketHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info(ctx.channel().remoteAddress()+"");
        log.info("Client output : "+msg);
        ctx.writeAndFlush("From client : "+ LocalDateTime.now());
    }

}
