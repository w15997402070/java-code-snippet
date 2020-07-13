package com.demo.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wang on 2020/5/7
 */
public class ReentrantLockTest {
   static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {

        Class<String> stringClass = String.class;
        for (int i = 0; i < 10; i++) {
            final int j = i;
            new Thread(() -> {
                new ReentrantLockTest().test(j);
            }).start();
        }
    }

    public  void test(int i){
        lock.lock();
        try {
            System.out.println(i+" Thread name start "+Thread.currentThread().getName());
            Thread.sleep(1000);
            System.out.println(i+" Thread name end "+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    static class StaticTest{

    }
}
