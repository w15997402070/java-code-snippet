package com.demo.leetcode.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * 1114
 * 三个线程依次执行 first -> second -> third 方法
 */
public class Foo {

    private CountDownLatch countDownLatchTwo = new CountDownLatch(1);
    private CountDownLatch countDownLatchThird = new CountDownLatch(1);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        countDownLatchTwo.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        countDownLatchTwo.await();
        printSecond.run();
        countDownLatchThird.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        countDownLatchThird.await();
        printThird.run();
    }
}

/**
 * 1115. 交替打印FooBar
 */
class FooBar {
    private Semaphore semaphore1 = new Semaphore(1);
    private Semaphore semaphore2 = new Semaphore(0);
    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // printFoo.run() outputs "foo". Do not change or remove this line.
            semaphore1.acquire();
            printFoo.run();
            semaphore2.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            semaphore2.acquire();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            semaphore1.release();
        }
    }
}
