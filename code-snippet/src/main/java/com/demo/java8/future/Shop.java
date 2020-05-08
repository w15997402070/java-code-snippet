package com.demo.java8.future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2020/5/8.
 *
 */
public class Shop {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shop(String name) {
        this.name = name;
    }

    public double getPrice(String product){
        return calculatePrice(product);
    }

    public String getPriceWithDiscount(String product){
        double price = calculatePrice(product);
        Random random = new Random();
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }
//    public Future<Double>getPriceAsync(String product){
//        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
//        new Thread(() -> {
//            double price = calculatePrice(product);
//            futurePrice.complete(price);
//        }).start();
//        return futurePrice;
//    }

    /**
     * 上面方法不会处理计算价格的方法(calculatePrice)发生异常时的处理情况
     * 这个方法处理异常
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync(String product){
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            }catch (Exception e){
                //出现异常时,客户端会收到一个ExecutionException参数,即价格计算方法抛出的异常
                futurePrice.completeExceptionally(e);
            }

        }).start();
        return futurePrice;
    }

    /**
     * getPriceAsync方法精简版本
     * CompletableFuture 的工厂方法精简代码,
     * supplyAsync 方法接收一个生产者(Supplier)作为参数,返回一个CompletableFuture对象,
     * 该对象完成异步执行后会读取调用生产者方法的返回值
     * 生产者的方法会交由ForkJoinPool池中的某个执行线程执行
     *
     * @return future
     */
//    public Future<Double> getPriceAsync(String product){
//        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
//    }

    /**
     * 睡眠延时 1 秒模拟耗时操作
     */
    public static void delay(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 价格计算方法
     * @param product
     * @return
     */
    public double calculatePrice(String product){
        delay();
        Random random = new Random();
        return random.nextDouble() * product.charAt(0)+product.charAt(1);
    }

    public static void main1(String [] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long  invocationTime = (System.nanoTime() -start)/1_000_000;

        System.out.println("Invocation time : "+invocationTime+" msecs");
        doSomethingElse();
        try {
            Double aDouble = futurePrice.get();
            System.out.printf("price : %.2f%n ",aDouble);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long  retrievalTime = (System.nanoTime() -start)/1_000_000;
        System.out.println("Price return time after : "+retrievalTime+" msecs");

    }

    private static void doSomethingElse() {
    }

    static List<Shop> shops = new ArrayList<>();
    public static void main(String [] args) {

        shops.add(new Shop("BestShop"));
        shops.add(new Shop("LetsSaveBig"));
        shops.add(new Shop("MyFavoriteShop"));
        shops.add(new Shop("BuyItAll"));

        long startTime = System.nanoTime();
        System.out.println(findPriceDiscountWithCompletableFuture("myPhone275"));
        long  retrievalTime = (System.nanoTime() -startTime)/1_000_000;
        System.out.println("Done in : "+retrievalTime+" msecs");
    }

    /**
     * stream流处理
     * @param product
     * @return
     */
//    public static List<String> findPrices(String product){
//        //异步调用
//        return shops.parallelStream()
//                .map(shop -> String.format("%s price is %.2f",shop.getName(),shop.getPrice(product)))
//                .collect(Collectors.toList());
//        //同步调用
////        return shops.stream()
////                .map(shop -> String.format("%s price is %.2f",shop.getName(),shop.getPrice(product)))
////                .collect(Collectors.toList());
//    }

    /**
     * CompletableFuture 异步处理
     * @param product
     * @return
     */
    public static List<String> findPrices(String product){
        //异步调用
        List<CompletableFuture<String>> futureList = shops.stream()
                .map((shop) -> CompletableFuture.supplyAsync(() -> shop.getName()+"price is "+shop.getPrice(product)))
                .collect(Collectors.toList());
        return futureList.stream()
                .map(CompletableFuture :: join)
                .collect(Collectors.toList());
    }

    /**
     * 这个耗费 Done in : 8330 msecs
     * 顺序查询商店的价格耗费 4 秒
     * 四个商店申请折扣又耗费 4 秒
     * 其他的耗费 330 毫秒
     * @param product
     * @return
     */
    public static List<String> findPriceWithDiscount(String product){
        return shops.stream()
                .map(shop -> shop.getPriceWithDiscount(product))
                .map(Quote :: parse)
                .map(Discount :: applyDiscount)
                .collect(Collectors.toList());
    }

    public static List<String> findPriceDiscountWithCompletableFuture(String product) {
        List<CompletableFuture<String>> futureList =  shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() ->  shop.getPriceWithDiscount(product)))
                .map(future -> future.thenApply(Quote :: parse))
                .map(future -> future.thenCompose(quote ->
                                                      CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote))))
                .collect(Collectors.toList());
        return futureList.stream()
                .map(CompletableFuture :: join)
                .collect(Collectors.toList());
    }
}
