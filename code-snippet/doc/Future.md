
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

简单示例
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