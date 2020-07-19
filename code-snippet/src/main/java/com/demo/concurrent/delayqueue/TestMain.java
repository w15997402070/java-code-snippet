package com.demo.concurrent.delayqueue;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020/7/19
 *
 * @author wang
 */
public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Event> delayQueue = new DelayQueue<>();
        Thread [] threads = new Thread [5];
        for (int i = 0; i < threads.length; i++) {
            Task task = new Task(i + 1, delayQueue);
            threads[i] = new Thread(task);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        do {
            int counter = 0;
            Event event;
            do {
                event = delayQueue.poll();
                if (event != null){
                    counter++;
                }
            }while (event != null);
            System.out.printf("At %s you have read %d events\n", new Date(), counter);
            TimeUnit.MILLISECONDS.sleep(500);
        }while (delayQueue.size() > 0);
    }
}
