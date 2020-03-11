package com.demo.netty.heartcheck;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartCheckHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            String eventType = null;
            switch (event.state()){
                case ALL_IDLE:
                    eventType = "读写空闲";break;
                case READER_IDLE:
                    eventType = "读空闲";break;
                case WRITER_IDLE:
                    eventType = "写空闲";break;
                default:
                    eventType = "状态错误";break;
            }
            System.out.println("超时事件 : "+eventType);
            ctx.channel().close();
        }
    }
}
