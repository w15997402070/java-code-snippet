package com.demo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServerTest {

    public static void main(String[] args) throws IOException {

        int port = 8899;

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        serverSocketChannel.socket().bind(inetSocketAddress,1024);
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server  started on port : "+port);

        while (true){
            selector.select(10000);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            SelectionKey key = null;
            while (keyIterator.hasNext()){
                key  = keyIterator.next();
                keyIterator.remove();
                if (key.isValid()){
                    if (key.isAcceptable()){
                        //这里强转对应的是 serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);,所以转换成ServerSocketChannel
                        ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector,SelectionKey.OP_READ);
                    }
                    if (key.isReadable()){
                        //这里强转对应的是 socketChannel.register(selector,SelectionKey.OP_READ),所以转换成SocketChannel
                        SocketChannel sc = (SocketChannel)key.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int readLength = sc.read(readBuffer);
                        if (readLength > 0){
                            readBuffer.flip();
                            byte [] bytes = new byte[readBuffer.remaining()];
                            readBuffer.get(bytes);
                            String body = new String(bytes,"UTF-8");
                            System.out.println("服务端接收到信息 : "+body);
                            //返回消息
                            ByteBuffer writeBufer = ByteBuffer.allocate(1024);
                            writeBufer.put("服务端返回消息".getBytes());
                            writeBufer.flip();
                            sc.write(writeBufer);
                        }else if (readLength < 0){
                            key.cancel();
                            sc.close();
                        }
                    }
                }
            }
        }

//        selector.close();
    }
}
