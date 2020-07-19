package com.demo.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wang on 2020/5/11
 */
public class ReadWriteLockTest {

    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;

    public Object handlerRead(Lock lock) throws InterruptedException {
        lock.lock();
        try {
            Thread.sleep(1000);
            return value;
        }  finally {
            lock.unlock();
        }
    }

    public void handlerWrite(Lock lock, int index) throws InterruptedException {
        lock.lock();
        try {
            Thread.sleep(1000);
            value = index;
            System.out.println(" handlerWrite : "+value);
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
        Runnable readRunnable = new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println(readWriteLockTest.handlerRead(readLock));
//                    System.out.println(readWriteLockTest.handlerRead(lock));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable writeRunnable = new Runnable(){
            @Override
            public void run() {
                try {
                    readWriteLockTest.handlerWrite(writeLock,new Random().nextInt());
//                    readWriteLockTest.handlerWrite(lock,new Random().nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        long start = System.currentTimeMillis();
        for (int i = 0; i < 18 ; i++) {
            new Thread(readRunnable).start();
        }
        long readStop = System.currentTimeMillis();
        System.out.println("read 耗时 : "+(readStop-start));
        for (int i = 18; i < 20 ; i++) {
            new Thread(writeRunnable).start();
        }
        long writeStop = System.currentTimeMillis();
        System.out.println("write 耗时 : "+(writeStop - readStop));
    }
}
