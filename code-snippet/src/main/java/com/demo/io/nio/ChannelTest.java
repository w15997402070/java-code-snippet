package com.demo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 服务端
 */
public class ChannelTest {

    public static void main(String[] args) {


        try {
            //1.服务端通过创建channel
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //2.绑定端口
            serverSocketChannel.bind(new InetSocketAddress(8000));
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //3.监听客户端连接
            SocketChannel acceptChannel = serverSocketChannel.accept();
            //4.客户端连接远程主机及端口

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
