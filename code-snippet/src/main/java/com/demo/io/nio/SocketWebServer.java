package com.demo.io.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketWebServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8899);
            Socket socket = serverSocket.accept();
            System.out.println("Server stared!");
//            InputStream inputStream = socket.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String getString = "";
//            getString = bufferedReader.readLine();
//            System.out.println(getString);
//            while (getString != null && !"".equals(getString)){
//                System.out.println(getString);
//                getString = bufferedReader.readLine();
//            }
            OutputStream outputStream = socket.getOutputStream();
//            outputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            outputStream.write("我是服务端".getBytes());
            outputStream.flush();
//            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
