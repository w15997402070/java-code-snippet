package com.demo.executor.returnfirst;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 1．创建一个名为UserValidator的类，并实现用户校验的逻辑：
 * @author wang
 */
public class UserValidator {
//2．声明一个名为name的私有String变量——用来存储用户校验系统的名称：
    private final String name;
//3．实现该类的构造方法并初始化类中变量：
    public UserValidator(String name){
        this.name = name;
    }
//4．实现validate()方法。该方法接收两个String类型的参数，它们分别为需要校验的名称和密码：
    public boolean validate(String name, String password){
//5．创建一个名为random的Random对象：
        Random random = new Random();
//6．随机等待一段时间后，模拟用户校验过程
        try {
            long duration = ThreadLocalRandom.current().nextLong(5);
            System.out.printf("Validator %s: Validating a user during %d seconds\n", this.name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            return false;
        }
//7．返回一个随机的布尔值。如果用户校验通过，则validate()方法返回true，否则返回false：
        return random.nextBoolean();
    }
//8．实现getName()方法。该方法返回变量name的值：
    public String getName(){
        return this.name;
    }
}
