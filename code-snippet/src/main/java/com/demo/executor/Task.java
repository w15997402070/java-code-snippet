package com.demo.executor;

import org.joda.time.LocalDateTime;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020/7/13
 *
 * @author wang
 */
public class Task implements Runnable {

    private final Date initDate;
    private final String name;

    public Task(String name){
        initDate = new Date();
        this.name = name;
    }
    @Override
    public void run() {
        System.out.printf("%s: Task %s Created on: %s\n", Thread.currentThread().getName(), name, initDate);
        System.out.printf("%s: Task %s Started on: %s\n", Thread.currentThread().getName(), name, LocalDateTime.now().toString());
        try {
            long duration = ThreadLocalRandom.current().nextLong(5);
            System.out.printf("%s: Task %s Doing a task during: %d seconds\n", Thread.currentThread().getName(), name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: Task %s Finished on: %s\n", Thread.currentThread().getName(), name, LocalDateTime.now().toString());
    }
}
