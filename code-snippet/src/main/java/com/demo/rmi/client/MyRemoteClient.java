package com.demo.rmi.client;


import com.demo.rmi.server.MyRemote;

import java.rmi.Naming;


public class MyRemoteClient {

    public static void main(String[] args) {
        new MyRemoteClient().go();
    }

    private void go() {
        // TODO Auto-generated method stub
        try {
            MyRemote servive = (MyRemote) Naming.lookup("rmi://127.0.0.1/RemoteHello");
            String s = servive.sayHello();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
