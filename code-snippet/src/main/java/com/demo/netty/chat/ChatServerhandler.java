package com.demo.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class ChatServerhandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        log.info(msg);
        log.info(channelGroup.size()+"");
        channelGroup.forEach(ch -> {
            if (channel != ch){
                ch.writeAndFlush("来自客户端 : ["+ch.id()+"] 的信息 ["+msg+"]"+"\r\n");
            }else {
                channel.writeAndFlush("[自己发送的消息]"+"\r\n");
            }
        });
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
////        super.channelActive(ctx);
//        log.info("上线 :  "+ctx.channel().id().toString());
////        ctx.writeAndFlush("From Server channelActive");
//    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("服务器handlerAdded : 客户端"+channel.id()+"加入\r\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("服务器handlerRemoved : 客户端"+channel.id()+"离开\r\n");
        log.info(""+channelGroup.size());
        //这一行代码加不加都可以,客户端断掉之后,channelGroup 会自动删掉断掉的客户端Channel
//        channelGroup.remove(channel);
    }

//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
////        super.channelInactive(ctx);
//        log.info("channelInactive : "+ctx.channel().remoteAddress()+"   "+ctx.channel().id().toString());
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
