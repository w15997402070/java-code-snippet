package com.demo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClientTest {

    public static void main(String[] args) throws IOException {

        String host = "127.0.0.1";
        int port = 8899;
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
        if (socketChannel.connect(inetSocketAddress)){
            socketChannel.register(selector,SelectionKey.OP_READ);
            doWrite(socketChannel);
        }else {
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
        while (true){
            selector.select(1000);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            SelectionKey key = null;
            while (keyIterator.hasNext()){
                key = keyIterator.next();
                keyIterator.remove();
                if (key.isValid()){
                    SocketChannel sc = (SocketChannel)key.channel();
                    if (key.isConnectable()){
                        if (sc.finishConnect()){
                            sc.register(selector,SelectionKey.OP_READ);
                            doWrite(sc);
                        }else {
                            System.exit(1);
                        }
                    }
                    if (key.isReadable()){
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int readLength = sc.read(readBuffer);
                        if (readLength > 0){
                            readBuffer.flip();

                            byte[] bytes = new byte[readBuffer.remaining()];
                            readBuffer.get(bytes);
                            String body = new String(bytes,"UTF-8");
                            System.out.println("来自服务端消息 : "+body);
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

    private static void doWrite(SocketChannel socketChannel) throws IOException {
        byte [] req = "这是客户端消息".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put(req);
        writeBuffer.flip();
        socketChannel.write(writeBuffer);
        if (!writeBuffer.hasRemaining()){
            System.out.println("Send order 2 server succeed.");
        }
    }
}
