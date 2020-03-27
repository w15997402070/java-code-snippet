package com.demo.io.scalable_io.classic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wang
 */
public class Server implements Runnable {
    @Override
    public void run() {
        int port = 8899;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            while (!Thread.interrupted()){
                new Thread(new Handler(ss.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class Handler implements Runnable{
    final Socket socket;
    Handler (Socket s){
        this.socket = s;
    }
    @Override
    public void run() {
        try {
            byte[] input = new byte[1024];
            socket.getInputStream().read(input);
            byte [] output = process(input);
            socket.getOutputStream().write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte []  process(byte[] input) {
        return null;
    }
}
