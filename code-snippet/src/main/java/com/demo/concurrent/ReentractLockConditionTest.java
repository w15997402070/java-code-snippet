package com.demo.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wang on 2020/5/11
 */
public class ReentractLockConditionTest implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();
    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println("进入 run 方法");
            condition.await();
//            Thread.sleep(3000);
            System.out.println("Thread is going on");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println("解锁代码执行");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentractLockConditionTest lockConditionTest = new ReentractLockConditionTest();
        Thread t1 = new Thread(lockConditionTest);
        t1.start();
        Thread.sleep(1000);
        lock.lock();
        try {
            System.out.println("main 方法等待");
            condition.signal();
            System.out.println("main 方法通知后");
        }finally {
//            System.out.println("main 方法解锁");
            lock.unlock();
        }
    }
}
