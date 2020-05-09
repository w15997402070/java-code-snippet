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
//        MyThread myThread = new MyThread();
//        myThread.start();

//        Thread.yield();

       Thread threadOne =  new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread one ");
        });

        Thread threadTwo =  new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread two ");
        });

        threadOne.start();
        threadTwo.start();
        System.out.println("wait all thread");
        try {
            threadOne.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            threadTwo.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("all thread over");
    }
}
