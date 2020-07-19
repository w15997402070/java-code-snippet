package com.demo.executor.ctrdone;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
