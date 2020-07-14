package com.demo.executor.returnfirst;

import java.util.concurrent.Callable;

/**
 * 9．创建一个名为ValidatorTask的类，并发使用UserValidation类来执行校验过程，并实现泛型为String的Callable接口：
 * @author wang
 */
public class ValidatorTask implements Callable<String> {
//10．声明一个类型为UserValidator名为validator的私有变量：
    private final UserValidator validator;
//11．声明两个私有String型变量，它们分别名为name和password：
    private final String name;
    private final String password;
//12．实现类的构造方法并初始化全部变量：
    public ValidatorTask(UserValidator validator, String name, String password){
        this.validator = validator;
        this.name = name;
        this.password = password;
    }
//13．实现call()方法并返回一个String型对象：
    @Override
    public String call() throws Exception {
//14．如果name未通过UserValidator对象的校验，则在控制台打印信息记录并抛出Exception：
        if (!validator.validate(name, password)){
            System.out.printf("%s : The user has not been found\n", validator.getName());
            throw new Exception("Error validating user");
        }
//15．在控制台打印信息通过校验的用户信息，并返回UserValidator实例对象的name值：
        System.out.printf("%s: The user has been found\n", validator.getName());
        return validator.getName();
    }
}
