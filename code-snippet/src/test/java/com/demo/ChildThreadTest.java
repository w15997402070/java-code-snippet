package com.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wang
 * @since 2020/8/25
 */
public class ChildThreadTest {

    public  static ThreadLocal<String> mainThreadLocal = new InheritableThreadLocal<>();
    public static void main(String[] args) throws InterruptedException {
        mainThreadLocal.set("main Thread");
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorService.submit(() -> {
            System.out.println(mainThreadLocal.get());
        });
        Thread.sleep(100);
        executorService.shutdown();
    }
}
