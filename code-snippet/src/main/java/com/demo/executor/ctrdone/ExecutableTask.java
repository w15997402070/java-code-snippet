package com.demo.executor.ctrdone;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

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
