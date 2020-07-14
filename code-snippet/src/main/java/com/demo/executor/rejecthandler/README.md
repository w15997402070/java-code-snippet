## 创建一个线程执行器并实现其拒绝策略

一. 创建一个`Task`类实现`Runnable`接口

```java
//1．实现一个任务并提交执行。创建一个名为Task的类，并实现Runnable接口：
public class Task implements Runnable {
//2.声明一个名为initDate的Date类型的属性来存储任务的创建时间；并声明一个名为name的String类型的属性来存储任务的名称：
    private final Date initDate;
    private final String name;
//3．实现该类的构造方法,并初始化全部属性：
    public Task(String name){
        initDate = new Date();
        this.name = name;
    }
//4．实现run()方法
    @Override
    public void run() {
//5．打印initDate的值和执行当前任务时的真正时间：
        System.out.printf("%s: Task %s Created on: %s\n", Thread.currentThread().getName(), name, initDate);
        System.out.printf("%s: Task %s Started on: %s\n", Thread.currentThread().getName(), name, LocalDateTime.now().toString());
//6．让当前任务随机休眠一段时间：
        try {
            long duration = ThreadLocalRandom.current().nextLong(5);
            System.out.printf("%s: Task %s Doing a task during: %d seconds\n", Thread.currentThread().getName(), name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//7．在控制台打印任务的完成时间：
        System.out.printf("%s: Task %s Finished on: %s\n", Thread.currentThread().getName(), name, LocalDateTime.now().toString());
    }
}
```

二. 创建拒绝策略类

```java
//创建一个名为RejectedTaskController的类，并实现RejectedExecutionHandler接口。在类中实现接口的rejectedExecution()方法，然后打印出被拒绝的任务名称和执行器的名称与状态：
public class RejectedTaskController implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.printf("RejectedTaskController: The task %s has been rejected\n", r.toString());
        System.out.printf("RejectedTaskController: %s\n", executor.toString());
        System.out.printf("RejectedTaskController:  Terminating: %s\n", executor.isTerminating());
        System.out.printf("RejectedTaskController:  Terminated: %s\n", executor.isTerminated());
    }
}
```

三. 创建Server类

```java
//9．创建一个Server类，用一个executor实例对象来执行它收到的每一个任务：
public class Server {
//10．声明一个名为executor、类型为ThreadPoolExecutor的属性：
    private final ThreadPoolExecutor executor;
//11．实现这个类的构造方法，用Executors类来初始化ThreadPoolExecutor对象，并创建一个拒绝策略：
    public Server(){
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        RejectedTaskController rejectedTaskController = new RejectedTaskController();
        executor.setRejectedExecutionHandler(rejectedTaskController);
    }
//12．实现executeTask()方法来接收一个Task对象作为参数，并将其提交给一个执行器。首先，在控制台打印信息来表明一个新任务已经到达：
    public void executeTask(Task task){
        System.out.printf("Server: A new task has arrived\n");
//13．调用执行器的execute()方法来提交任务：
        executor.execute(task);
//14．在控制台打印执行器的信息来区分状态：
        //• getPoolSize()：该方法返回执行器线程池中当前的线程数。
        //• getActiveCount()：该方法返回执行器中正在执行任务的线程数。
        //• getTaskCount()：该方法返回等待执行的任务数。由于等待执行的任务数是动态变化的，因此该方法返回的只是一个近似值。
        //• getCompletedTaskCount()：该方法返回执行器已经完成的任务数。
        System.out.printf("Server: Pool Size: %d\n", executor.getPoolSize());
        System.out.printf("Server: Active Count: %d\n", executor.getActiveCount());
        System.out.printf("Server: Task Count: %d\n", executor.getTaskCount());
        System.out.printf("Server: Completed Tasks: %d\n", executor.getCompletedTaskCount());
    }
//15．实现endServer()方法。在这个方法中，调用执行器的shutdown()方法来终止执行器：
    public void endServer(){
        executor.shutdown();
    }
}
```

四. TestMain测试类

```java
//16．至此，我们可以开始实现应用程序的入口，创建包含main()方法的Main类。首先，创建100个任务并将其提交给Executor：
public class TestMain {

    public static void main(String[] args) {
        Server server = new Server();
        System.out.println("Main: Starting... ");
        for (int i = 0; i < 100 ; i++) {
            Task task = new Task("Task" + i);
            server.executeTask(task);
        }
//17．调用Server类中的endServer()方法来终止执行器：
        System.out.println("Main: Shutting down the Executor.");
        server.endServer();
//18．提交一个新的任务。由于这个任务会被拒绝，因此我们可以看到拒绝策略是如何生效的：
        System.out.println("Main: Sending another Task.");
        Task rejected_task = new Task("Rejected task");
        server.executeTask(rejected_task);
        System.out.println("Main: End!");
    }
}
```



其他APi

ThreadPoolExecutor类也提供了其他与终止线程执行器相关的方法，具体如下。

* shutdownNow()：该方法会立刻终止执行器，而非执行现存的任务。虽然该方法会返回一个现存任务的列表，已经执行的任务会在调用方法后继续执行，但是该方法会直接返回结果，而非等待它们运行完成。
*  isTerminated()：该方法用来判断是否执行器处于结束过程。如果调用了shutdown()或shutdownNow()方法，则该方法会返回true。
*  isShutdown()：如果调用了执行器的shutdown()方法，则该方法会返回true。
*  awaitTermination(long timeout, TimeUnit unit)：该方法会阻塞调用线程直到执行器关闭或是超时。TimeUnit类是一系列常量的枚举：DAYS、HOURS、MICROSECONDS、MILLISECONDS、MINUTES、NANOSECONDS和SECONDS。



<<Java 9 并发编程实战>>