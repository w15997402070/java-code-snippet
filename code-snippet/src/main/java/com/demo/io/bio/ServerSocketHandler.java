package com.demo.io.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

@Slf4j
public class ServerSocketHandler implements Runnable {

    private Socket socket;

    public ServerSocketHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader bf = null;
        try {
            InputStream inputStream = socket.getInputStream();
            bf = new BufferedReader(new InputStreamReader(inputStream));
            String body = null;
            while ((body = bf.readLine()) != null){
                body = bf.readLine();
                log.info(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bf != null){
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
