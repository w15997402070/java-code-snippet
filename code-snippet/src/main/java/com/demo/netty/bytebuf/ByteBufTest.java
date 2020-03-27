package com.demo.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wang
 */
public class ByteBufTest {

    public static void main(String[] args) {
        //Unpooled非池化的
        ByteBuf byteBuf = Unpooled.buffer(10);
        for (int i = 0; i < 10 ; i++) {
            byteBuf.writeByte(i);
        }
        for (int i = 0; i < byteBuf.capacity() ; i++) {
            System.out.println(byteBuf.getByte(i));
        }
    }

    public static void test1(){
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello world", StandardCharsets.UTF_8);
    }
}
