package com.demo.netty.protobuf.mutidomain;

import com.demo.netty.protobuf.domain.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class ProtobufClientHandler extends SimpleChannelInboundHandler<MyDataInfo.Mymessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Mymessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        System.out.println(randomInt);
        MyDataInfo.Mymessage mymessage = null;
        if (randomInt == 1){
            mymessage = MyDataInfo.Mymessage.newBuilder()
                    .setDataType(MyDataInfo.Mymessage.DataType.PersonType)
                    .setPerson(MyDataInfo.Person.newBuilder()
                            .setName("张三")
                            .setAddress("湖北").build()

                    ).build();
        }else if (randomInt == 2){
            mymessage = MyDataInfo.Mymessage.newBuilder()
                    .setDataType(MyDataInfo.Mymessage.DataType.DogType)
                    .setDog(MyDataInfo.Dog.newBuilder()
                            .setName("小狗")
                            .setAge(2).build()

                    ).build();
        }else {
            mymessage = MyDataInfo.Mymessage.newBuilder()
                    .setDataType(MyDataInfo.Mymessage.DataType.CatType)
                    .setCat(MyDataInfo.Cat.newBuilder()
                            .setName("小猫")
                            .setCity("湖北").build()

                    ).build();
        }
        ctx.writeAndFlush(mymessage);
    }
}
