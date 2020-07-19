## 在执行器内控制任务的完成

一. 创建执行任务的Task类

```java
/**
 * Created on 2020/7/19
 * 1．创建一个实现Callable接口且泛型为String的名为ExecutableTask的类：
 * @author wang
 */
public class ExecutableTask implements Callable<String> {

//2. 声明一个名为name的私有String型变量，用来存储任务名称。
    private final String name;
    public String getName(){
        return this.name;
    }
//3．实现该类的构造方法，并初始化任务名称：
    public ExecutableTask(String name){
        this.name = name;
    }
//4．实现call()方法，让任务休眠一段随机时间并返回任务名称等信息：
    @Override
    public String call() throws Exception {
        long duration = ThreadLocalRandom.current().nextLong(5);
        System.out.printf("%s : Waiting %d seconds for results.\n", this.name, duration);
        TimeUnit.SECONDS.sleep(duration);
        return "Hello, world, I'm "+name;
    }
}
```



二. 创建一个接受结果的任务类

```java
/**
 * Created on 2020/7/19
 * 5．实现一个名为ResultTask的类。该类继承FutureTask类，泛型类型为String：
 * @author wang
 */
public class ResultTask extends FutureTask<String> {

 //6．声明一个名为name的私有String型变量，它用来存储任务名称：
    private final String name;

//7．实现该类的构造方法，该方法接收一个Callable实例对象作为参数。调用该类父类构造方法并使用接收的任务属性来初始化name变量：
    public ResultTask(ExecutableTask callable) {
        super(callable);
        this.name = callable.getName();
    }
//8．重写done()方法。检查isCancelled()方法的值并根据返回值在控制台打印返回信息：
    @Override
    protected void done() {
        if (isCancelled()){
            System.out.printf("%s: Has been canceled\n", name);
        }else {
            System.out.printf("%s: Has finished\n", name);
        }
    }
}
```



三. 创建测试类

```java
/**
 * Created on 2020/7/19
 * 9. 创建包含main()方法的测试类：
 * @author wang
 */
public class TestMain {
    public static void main(String[] args) {
 //10．使用Executors类中的newCachedThreadPool创建ExecutorService实例对象：
        ExecutorService executor = Executors.newCachedThreadPool();
 //11．创建一个数组用来存储5个ResultTask对象：
        ResultTask[] resultTask = new ResultTask [5];
//12．初始化ResultTask对象。对数组中的每个元素，首先创建ExecutableTask对象，并使用该对象创建ResultTask对象；随后使用submit()方法提交ResultTask对象到执行器：
        for (int i = 0; i < 5 ; i++) {
            ExecutableTask executableTask = new ExecutableTask("Task" + i);
            resultTask[i] = new ResultTask(executableTask);
            executor.submit(resultTask[i]);
        }
 //13．让主线程休眠1s：
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//14．取消提交给执行器的全部任务：
        for (int i = 0; i < resultTask.length ; i++) {
            resultTask[i].cancel(true);
        }
//15．用ResultTask对象的get()方法获取还未取消的对象信息，并在控制台打印出来：
        for (int i = 0; i < resultTask.length ; i++) {
                try {
                    if (!resultTask[i].isCancelled()) {
                        System.out.printf("%s\n", resultTask[i].get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
        }
//16．调用shutdown()方法关闭执行器：
        executor.shutdown();
    }
}
```

四. 运行结果分析

```java
Task1 : Waiting 3 seconds for results.
Task2 : Waiting 0 seconds for results.
Task3 : Waiting 0 seconds for results.
Task4 : Waiting 3 seconds for results.
Task0 : Waiting 3 seconds for results.
Task3: Has finished
Task2: Has finished
Task0: Has been canceled
Task1: Has been canceled
Task4: Has been canceled
Hello, world, I'm Task2
Hello, world, I'm Task3
```

可以看到task2,task3在任务取消之前执行完成了,其他的任务被取消了

当任务结束运行时，管理任务的FutureTask对象会调用done()方法。在本案例中，用实现了Callable接口的ExecutableTask类和一个FutureTask的子类来管理ExecutableTask实例对象的执行。当FutureTask类确定了任务的返回值，并将任务状态改为isDone后，它会间歇性地调用done()方法。虽然这时不能修改任务的返回值或是状态，但是可以关闭任务使用的资源，打印日志信息或是发送通知。FutureTask类也可以通过只调用一次其重写的Runnable或Callable接口的run()方法来确保一个指定的任务只运行一次（当运行结果可见时可以获取结果）。