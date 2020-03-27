package com.demo.io.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOClientTest2 {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector,SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                if (selectionKey.isConnectable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    if (client.isConnectionPending()){
                        try {
                            client.finishConnect();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);
                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {
                                while (true){
                                    writeBuffer.flip();
                                    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                    String sendMessage = bufferedReader.readLine();
                                    writeBuffer.put(sendMessage.getBytes());
                                    writeBuffer.flip();
                                    client.write(writeBuffer);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            client.register(selector,SelectionKey.OP_READ);
                        } catch (ClosedChannelException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (selectionKey.isReadable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readLength = 0;
                    try {
                        readLength = client.read(readBuffer);
                        if (readLength > 0){
                            String receiveMsg = new String(readBuffer.array(),0,readLength);
                            System.out.println(receiveMsg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            selectionKeys.clear();
        }
    }
}
