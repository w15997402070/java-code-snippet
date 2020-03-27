package com.demo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NIOServerTest2 {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            SelectionKey selectionKey = null;
            while (keyIterator.hasNext()){
                selectionKey = keyIterator.next();
                if (selectionKey.isAcceptable()){
                    ServerSocketChannel ssc = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel client = ssc.accept();
                    client.configureBlocking(false);
                    client.register(selector,SelectionKey.OP_READ);
                    String key = "["+ UUID.randomUUID().toString() +"]";
                    clientMap.put(key,client);
                }else if (selectionKey.isReadable()){
                    SocketChannel client = (SocketChannel)selectionKey.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readLength = client.read(readBuffer);
                    if (readLength > 0){
                        readBuffer.flip();
                        Charset charset = StandardCharsets.UTF_8;
                        String receiveMsg = String.valueOf(charset.decode(readBuffer).array());
                        System.out.println(client + " : "+receiveMsg );

                        String sendKey = null;
                        for (Map.Entry<String,SocketChannel> entry:clientMap.entrySet()){
                            if (client == entry.getValue()){
                                sendKey = entry.getKey();
                                break;
                            }
                        }

                        for (Map.Entry<String,SocketChannel> entry:clientMap.entrySet()){
                            SocketChannel value = entry.getValue();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((sendKey + ":"+receiveMsg).getBytes());
                            writeBuffer.flip();
                            value.write(writeBuffer);
                        }
                    }

                }
                keyIterator.remove();
            }
        }

    }
}
