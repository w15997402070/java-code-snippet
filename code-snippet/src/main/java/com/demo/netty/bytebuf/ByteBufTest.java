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

    public static void read(){
        //Unpooled非池化的
        ByteBuf byteBuf = Unpooled.buffer(10);

        //从 readerIndex 开始获取boolean 值,readerIndex 增加1
        boolean b = byteBuf.readBoolean();
        //从 readerIndex开始获取字节值, readerIndex 增加1
        byte b1 = byteBuf.readByte();
        //从 readerIndex开始获取无符号字节值, readerIndex 增加1
        short s = byteBuf.readUnsignedByte();
        //从 readerIndex开始获取短整型值, readerIndex 增加2
        short s2 = byteBuf.readShort();
        //从 readerIndex开始获取无符号短整型值, readerIndex 增加2
        int i = byteBuf.readUnsignedShort();

    }

}
