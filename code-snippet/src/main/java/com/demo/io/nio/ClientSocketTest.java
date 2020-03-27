package com.demo.io.nio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocketTest {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8899);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("我是客户端".getBytes());
            outputStream.flush();
//            Thread.sleep(2000);
//            InputStream inputStream = socket.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String s = bufferedReader.readLine();
//            while (s != null){
//                System.out.println(s);
//                s = bufferedReader.readLine();
//            }
//            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
