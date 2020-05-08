package com.demo.java8.future;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2020/5/8.
 */
public class FutureTest {

    public static void main(String [] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> future = executorService.submit(() -> doSomethingLongComputation());
        doSomethingElse();
        try {
            Double aDouble = future.get();
            Double aDouble1 = future.get(1, TimeUnit.SECONDS);
            System.out.println("Future 结果 : "+aDouble);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }

    private static void doSomethingElse() {
        int j = 0;
        for (int i = 0; i < 10 ; i++) {
            j += i;
        }
        System.out.println(j);
    }

    private static double doSomethingLongComputation() {
        double d = 0D;
        for (int i = 0; i < 5 ; i++) {
            d += i;
        }
        return d;
    }
}
