package com.demo.concurrent;

/**
 * Created by Administrator on 2020/1/15.
 */
public class ThreadTest {

    public static class MyThread extends Thread {

        @Override
        public void run(){
            System.out.println("这是新线程");
        }
    }

    public static void main(String [] args){
        MyThread myThread = new MyThread();
        myThread.start();
    }
}
