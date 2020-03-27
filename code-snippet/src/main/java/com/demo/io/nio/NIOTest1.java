package com.demo.io.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;

public class NIOTest1 {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println("是否直接缓冲区 : "+byteBuffer.isDirect());


        mappedByteBufferTest();

    }

    public static void intBufferTest(){
        IntBuffer intBuffer = IntBuffer.allocate(10);
        for (int i = 0; i < intBuffer.capacity() ; i++) {
            int randomInt = new SecureRandom().nextInt(20);
            intBuffer.put(randomInt);
        }
        //读写转换
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }

    public static void fileInputStreamTest(){
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\data\\javaTest\\github\\java-code-snippet\\code-snippet\\file\\nio\\NIOTest.txt");
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            channel.read(byteBuffer);
            byteBuffer.flip();
            //读取字符串
            byte[] array = byteBuffer.array();
            String s = new String(array);
            System.out.println(s);
            //读取字节
            while (byteBuffer.remaining() > 0){
                byte b = byteBuffer.get();
                System.out.println((char)b);
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileOutputStreamTest(){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("D:\\data\\javaTest\\github\\java-code-snippet\\code-snippet\\file\\nio\\NIOTest2.txt");
            FileChannel channel = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            byte[] bytes = "这是测试写入文件".getBytes();
            for (int i = 0; i < bytes.length ; i++) {
                byteBuffer.put(bytes[i]);
            }

            byteBuffer.flip();
            channel.write(byteBuffer);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 内存映射文件
     */
    public static void mappedByteBufferTest(){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\data\\javaTest\\github\\java-code-snippet\\code-snippet\\file\\nio\\NIOTest.txt","rw");
            FileChannel channel = randomAccessFile.getChannel();
            //MappedByteBuffer 内存映射
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

            mappedByteBuffer.put(0,(byte)'a');
            mappedByteBuffer.put(1,(byte)'b');

            randomAccessFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
