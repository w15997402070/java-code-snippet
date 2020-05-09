
# CompletableFuture 异步编程

CompletableFuture 实现了 Future 接口

采用Future接口可以对异步计算建模,返回一个指向执行结果的引用,运算结束后,调用方可以通过该引用执行访问的结果

```java
public interface Future<V> {
    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    /**判断是否已经结束*/
    boolean isDone();
    /**获取操作结果,这个方法会阻塞*/
    V get() throws InterruptedException, ExecutionException;
    /**在一定的时间内等待获取结果,等待超时[抛出异常*/
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

Future简单示例

```java
public static void main(String [] args) {
   //1. 创建一个 ExecutorService
    ExecutorService executorService = Executors.newCachedThreadPool();
    //2. 向ExecutorService中提交耗时任务,一个Callable对象
    Future<Double> future = executorService.submit(() -> doSomethingLongComputation());
    //3. 提交之后异步执行,此时可以做其他的事情
    doSomethingElse();
    try {
      //4. 获取结果,这个方法将会阻塞直到获取到结果
        Double aDouble = future.get();
        System.out.println("Future 结果 : "+aDouble);
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    } finally {
        executorService.shutdown();
    }
}
```
上面的操作步骤为:
1. 创建一个线程池
2. 向线程池中提交执行任务
3. 执行其他任务,2和3将会同时执行
4. 获取结果,这一步阻塞直到获取到结果

上面有一个问题就是 `future.get()`方法如果一直获取不到结果,线程就会一直阻塞,所以最好是使用
`Double d = future.get(1, TimeUnit.SECONDS)`方法,这个方法传递两个参数,第一个是数值,第二个是事件单位,这个方法在等待超时时会抛出
`TimeoutException`异常


## CompletableFuture 类

假设一个获取商店价格的操作是一个耗费时间的操作  
简单示例  
创建一个类`Shop`
```java

public class Shop {

    private String name;

    public Shop(String name) {
            this.name = name;
    }
    //省略get set方法

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
}
```

getPrice()方法获取价格,会调用延迟的方法模拟耗时操作
```java
    /**
     * 获取价格
     * @param product
     * @return
     */
    public double getPrice(String product){
        return calculatePrice(product);
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
```
上面同步获取价格的方法,每次获取价格都同步等待价格返回,可以使用异步方法  
使用`CompletableFuture`异步获取价格的方法
```java
    /**
     * 异步获取价格的方法
     */
    public Future<Double> getPriceAsync(String product){
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        //在另一个线程中计算
        new Thread(() -> {
            double price = calculatePrice(product);
            futurePrice.complete(price);
        }).start();
        //不等待计算结果,直接返回 CompletableFuture 的实例
        return futurePrice;
    }
```
上面那个异步获取价格方法中 `calculatePrice(product)`发生异常时会出问题,发生异常时这个线程程序会一直等待
所以需要处理异常,在异常发生时抛出异常

```java
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
```

测试异步获取价格的方法
```java
    public static void main(String [] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long  invocationTime = (System.nanoTime() -start)/1_000_000;

        System.out.println("Invocation time : "+invocationTime+" msecs");
        doSomethingElse();
        try {
            //下面这个获取结果的方法也最好用带超时参数的方法来代替使用futurePrice.get(1, TimeUnit.SECONDS)
            Double aDouble = futurePrice.get();
            System.out.printf("price : %.2f%n ",aDouble);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long  retrievalTime = (System.nanoTime() -start)/1_000_000;
        System.out.println("Price return time after : "+retrievalTime+" msecs");
    }
```

上面已经可以异步的计算价格了  
异步计算的方法可以用`CompletableFuture`的工厂方法来简化
```java
    /**
     * getPriceAsync方法精简版本
     * CompletableFuture 的工厂方法精简代码,
     * supplyAsync 方法接收一个生产者(Supplier)作为参数,返回一个CompletableFuture对象,
     * 该对象完成异步执行后会读取调用生产者方法的返回值
     * 生产者的方法会交由ForkJoinPool池中的某个执行线程执行
     *
     * @return future
     */
    public Future<Double> getPriceAsync(String product){
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }
```

现在需要查询几家商店的价格

```java
List<Shop> shops = new ArrayList<>();
shops.add(new Shop("BestShop"));
shops.add(new Shop("LetsSaveBig"));
shops.add(new Shop("MyFavoriteShop"));
shops.add(new Shop("BuyItAll"));
```

1. 遍历顺序调用获取价格的方法

```java
   /** 
     * stream流处理
     * @param product
     * @return
     */
    public static List<String> findPrices(String product){
        //同步调用
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f",shop.getName(),shop.getPrice(product)))
                .collect(Collectors.toList());
    }
```

测试遍历调用执行
```java
    long startTime = System.nanoTime();
    //获取价格
    System.out.println(findPrices("myPhone275"));
    long  retrievalTime = (System.nanoTime() -startTime)/1_000_000;
    System.out.println("Done in : "+retrievalTime+" msecs");
```
同步调用结果
```java
[BestShop price is 153.56, LetsSaveBig price is 157.47, MyFavoriteShop price is 174.63, BuyItAll price is 211.78]
Done in : 4081 msecs
```
大概4秒多一点,主要是每个商店获取都需要1秒  
可以使用并行流来获取价格
```java
   /** 
     * 可以使用流处理
     * stream流处理
     * @param product
     * @return
     */
    public static List<String> findPrices(String product){
        //异步调用
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f",shop.getName(),shop.getPrice(product)))
                .collect(Collectors.toList());
    }
```
并行流处理的结果
```java
[BestShop price is 188.61, LetsSaveBig price is 220.97, MyFavoriteShop price is 140.97, BuyItAll price is 214.13]
Done in : 1076 msecs
```
只需要1秒多一点了

现在使用`CompletableFuture`异步调用

```java
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
```
结果为
```java
[BestShopprice is 197.14515548566516, LetsSaveBigprice is 153.8154879949503, MyFavoriteShopprice is 152.50381212325743, BuyItAllprice is 213.13890464020585]
Done in : 1083 msecs
```
这里使用了两个不同的Stream流水线，而不是在同一个处理流的流水线上一个接一个地放置两个map操作——这其实是有缘由的。考虑流操作之间的延迟特性，如果你在单一流水线中处理流，那么发向不同商家的请求只能以同步、顺序执行的方式才会成功。因此，每个创建CompletableFuture对象只能在前一个操作结束之后执行查询指定商家的动作，通知join方法返回计算结果
具体流程看`CompletableFuture.png`

上面stream的并行流`parallelStream`视乎已经很好的解决了问题,方便而且直观.但是这个取决于电脑执行的线程
`Runtime.getRuntime().availableProcessors()`,现在本地获取的这个值是8
也就是一次可以开启8个线程,在商店个数小于等于8时它的执行结果

```java
shops.add(new Shop("BestShop"));
shops.add(new Shop("LetsSaveBig"));
shops.add(new Shop("MyFavoriteShop"));
shops.add(new Shop("BuyItAll"));
shops.add(new Shop("BestShop"));
shops.add(new Shop("LetsSaveBig"));
shops.add(new Shop("MyFavoriteShop"));
shops.add(new Shop("BuyItAll"));
//结果
[BestShop price is 171.19, LetsSaveBig price is 214.16, MyFavoriteShop price is 121.96, BuyItAll price is 224.73, BestShop price is 214.92, LetsSaveBig price is 134.82, MyFavoriteShop price is 126.39, BuyItAll price is 197.59]
Done in : 1079 msecs
```

可以看到8个商店还是只要1秒多一点,再增加一个商店,9个看一下结果

```java
shops.add(new Shop("BestShop"));
shops.add(new Shop("LetsSaveBig"));
shops.add(new Shop("MyFavoriteShop"));
shops.add(new Shop("BuyItAll"));
shops.add(new Shop("BestShop"));
shops.add(new Shop("LetsSaveBig"));
shops.add(new Shop("MyFavoriteShop"));
shops.add(new Shop("BuyItAll"));
shops.add(new Shop("BuyItAll"));
//结果
[BestShop price is 167.92, LetsSaveBig price is 137.07, MyFavoriteShop price is 155.16, BuyItAll price is 166.06, BestShop price is 143.66, LetsSaveBig price is 220.45, MyFavoriteShop price is 225.35, BuyItAll price is 152.44, BuyItAll price is 225.11]
Done in : 2075 msecs
```

9个商店就需要2秒多了,因为一次只能开8个线程,还剩一个商店需要等到前面8个中的一个执行完也就是1秒钟之后再执行也是1秒所以是2秒

现在看`CompletableFuture`如果不使用线程池和stream并行是一样的,也是限制在`Runtime.getRuntime().availableProcessors()`的返回值
但是它可以定制线程池执行,控制线程的个数,现在创建一个10个线程的线程池执行9个商店的获取价格
```java
shops.add(new Shop("BestShop"));
shops.add(new Shop("LetsSaveBig"));
shops.add(new Shop("MyFavoriteShop"));
shops.add(new Shop("BuyItAll"));
shops.add(new Shop("BestShop"));
shops.add(new Shop("LetsSaveBig"));
shops.add(new Shop("MyFavoriteShop"));
shops.add(new Shop("BuyItAll"));
shops.add(new Shop("BuyItAll"));

//创建线程池
ExecutorService executorService = Executors.newFixedThreadPool(10);
long startTime = System.nanoTime();
System.out.println(findPricesWithCompletableFuture("myPhone275",executorService));
long  retrievalTime = (System.nanoTime() -startTime)/1_000_000;
System.out.println("Done in : "+retrievalTime+" msecs");
executorService.shutdown();

/**
 * CompletableFuture 线程池异步处理
 * @param product
 * @return
 */
public static List<String> findPricesWithCompletableFuture(String product,ExecutorService executorService){
    //异步调用
    List<CompletableFuture<String>> futureList = shops.stream()
            .map((shop) -> CompletableFuture.supplyAsync(() -> shop.getName()+"price is "+shop.getPrice(product),executorService))
            .collect(Collectors.toList());
    return futureList.stream()
            .map(CompletableFuture :: join)
            .collect(Collectors.toList());
}
```

结果为
```java
[BestShopprice is 191.77640426758057, LetsSaveBigprice is 213.66361744571256, MyFavoriteShopprice is 162.51653489926292, BuyItAllprice is 174.71642702941662, BestShopprice is 209.44534349828626, LetsSaveBigprice is 213.32774687278368, MyFavoriteShopprice is 162.02810553789504, BuyItAllprice is 217.7200430692656, BuyItAllprice is 149.01547843627716]
Done in : 1062 msecs
```

还是只要1秒多
所以使用 `CompletableFuture`可以很方便的配合线程池执行多个商店的操作

## 对多个任务进行流水线操作
现在假设每个商店都有打折活动

```java
public class Discount {
    /**
     * 折扣代码
     */
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage){
            this.percentage = percentage;
        }
    }

    /**
     * 每个商店打折后的价格
     * @param quote
     * @return
     */
    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is "+Discount.apply(quote.getPrice(),quote.getDiscountCode());
    }

    /**
     * 计算打折后的价格
     * @param price
     * @param discountCode
     * @return
     */
    private static double apply(double price, Code discountCode) {
        delay();
        return price * (100 - discountCode.percentage)/100;
    }

    /**
     * 延迟操作
     */
    public static void delay(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

```java
/**
 * 字符串的解析操作
 */
public class Quote {

    private final String shopName;
    private final double price;
    private final Discount.Code discountCode;

    public Quote(String shopName, double price, Discount.Code code){
        this.shopName = shopName;
        this.price = price;
        this.discountCode = code;
    }

    public static Quote parse(String s){
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code code = Discount.Code.valueOf(split[2]);
        return new Quote(shopName, price, code);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscountCode() {
        return discountCode;
    }
}
```

商店获取价格后计算折扣后的价格
```java
List<Shop> shops = new ArrayList<>();
shops.add(new Shop("BestShop"));
shops.add(new Shop("LetsSaveBig"));
shops.add(new Shop("MyFavoriteShop"));
shops.add(new Shop("BuyItAll"));

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
```
测试执行时间
```java
long startTime = System.nanoTime();
System.out.println(findPriceWithDiscount("myPhone275"));
long  retrievalTime = (System.nanoTime() -startTime)/1_000_000;
System.out.println("Done in : "+retrievalTime+" msecs");
```

差不多8秒多一点,顺序查询商店的价格耗费 4 秒,四个商店申请折扣又耗费 4 秒  
上面第一个map操作取的每个shop对象中商品的原始价格  
第二个map操作对上一步返回的字符串进行转换  
第三个map操作计算折扣后的价格

使用异步操作
```java
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
```

`thenCompose`方法允许你对两个异步操作进行流水线，第一个操作完成时，将其结果作为参数传递给第二个操作换句话说，你可以创建两个CompletableFuture对象，对第一个CompletableFuture对象调用thenCompose，并向其传递一个函数。当第一个CompletableFuture执行完毕后，它的结果将作为该函数的参数，这个函数的返回值是以第一个CompletableFuture的返回做输入计算出的第二个CompletableFuture对象
你需要将两个完全不相干的CompletableFuture对象的结果整合起来，而且你也不希望等到第一个任务完全结束才开始第二个任务。
这种情况下，你应该使用`thenCombine`方法，它接受名为BiFunction的第二个参数，这个参数定义了当两个CompletableFuture对象完成计算后，结果如何合并的操作.