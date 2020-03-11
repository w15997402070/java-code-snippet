package com.demo.netty.protobuf.singledomain;

import com.demo.netty.protobuf.domain.AddressBookProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufHandler extends SimpleChannelInboundHandler<AddressBookProtos.Person> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddressBookProtos.Person msg) throws Exception {
        System.out.println(msg.getName());
    }
}
