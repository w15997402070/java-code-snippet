package com.demo.netty.protobuf.mutidomain;

import com.demo.netty.protobuf.domain.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufHandler extends SimpleChannelInboundHandler<MyDataInfo.Mymessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Mymessage msg) throws Exception {
        MyDataInfo.Mymessage.DataType dataType = msg.getDataType();
        if (dataType == MyDataInfo.Mymessage.DataType.PersonType){
            System.out.println( msg.getPerson().getName());
        }else if (dataType == MyDataInfo.Mymessage.DataType.DogType){
            System.out.println( msg.getDog().getName());
        }else if (dataType == MyDataInfo.Mymessage.DataType.CatType){
            System.out.println( msg.getCat().getName());
        }else {
            System.out.println("数据错误");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
