package com.demo.io.bio;



import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

@Slf4j
public class SocketTest {
    public static void main(String[] args) {
//        try {
//            ServerSocket serverSocket = new ServerSocket(8888);
//            Socket socket = null;
//            while (true){
//                socket = serverSocket.accept();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        testSocket2();
    }

    public static void testSocket(){
        try(Socket s = new Socket("www.baidu.com",80)) {
            InputStream inputStream = s.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()){
                String s1 = scanner.nextLine();
                log.info(s1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testAddress(){
        String host = "www.baidu.com";
        try {
            InetAddress[] allByName = InetAddress.getAllByName(host);
            InetAddress localHost = InetAddress.getLocalHost();
            for (InetAddress i:allByName) {
                log.info("address : {}",i);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void testSocket2(){

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
            Socket socket = null;
            while (true){
                socket = serverSocket.accept();
                new Thread(new ServerSocketHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
            }
        }

    }
}
