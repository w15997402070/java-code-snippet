package com.demo.executor.ctrdone;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

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
