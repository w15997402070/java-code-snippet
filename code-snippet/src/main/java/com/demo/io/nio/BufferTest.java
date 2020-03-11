package com.demo.io.nio;

import java.nio.ByteBuffer;

public class BufferTest {

    public static void main(String[] args) {

        byte[] bytes = new byte[]{1, 2, 3};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        System.out.println("capacity : " + byteBuffer.capacity());
//        System.out.println("limit : "+byteBuffer.limit());
//        System.out.println("position : "+byteBuffer.position());

        byteBuffer.position(1);
        byteBuffer.mark();
        System.out.println("position : " + byteBuffer.position());
        byteBuffer.position(2);
        byteBuffer.reset();
        System.out.println("position : " + byteBuffer.position());
        byte[] bytes2 = new byte[]{1, 2, 3, 4, 5};
        byteBuffer = ByteBuffer.wrap(bytes2);
        System.out.println("capacity : " + byteBuffer.capacity());

    }
}
