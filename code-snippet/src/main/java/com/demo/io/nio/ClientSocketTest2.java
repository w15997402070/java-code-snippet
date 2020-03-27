package com.demo.io.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSocketTest2 {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost",8899);
            char [] chars = new char[3];
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            int readLength = inputStreamReader.read(chars);
            while (readLength != -1){
                System.out.println(new String(chars,0,readLength));
                readLength = inputStreamReader.read(chars);
            }
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
