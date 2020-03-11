package com.demo.netty.protobuf.singledomain;

import com.demo.netty.protobuf.domain.AddressBookProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufClientHandler extends SimpleChannelInboundHandler<AddressBookProtos.Person> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddressBookProtos.Person msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder()
                .setName("张三")
                .setEmail("159@163.com").build();
        ctx.writeAndFlush(person);
    }
}
