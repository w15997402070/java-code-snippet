package com.demo.executor.returnfirst;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建包含main()方法的TestMain类：
 *
 * @author wang
 */
public class TestMain {
    public static void main(String[] args) {
//17．创建两个String型对象，分别命名为name和password，并使用测试值初始化它们
        String name = "test";
        String password = "test";
//18．创建两个UserValidator对象，分别命名为ldapValidator和dbValidator：
        UserValidator ldapValidator = new UserValidator("LDAP");
        UserValidator dbValidator = new UserValidator("DataBase");
//19．创建两个TaskValidator对象，分别命名为ldapTask和dbTask。分别用ldapValidator和dbValidator初始化它们：
        ValidatorTask ldapTask = new ValidatorTask(ldapValidator, name, password);
        ValidatorTask dbTask = new ValidatorTask(dbValidator, name, password);
//20．创建一个TaskValidator对象的列表，并把创建的两个对象添加进去：
        List<ValidatorTask> taskList = new ArrayList<>();
        taskList.add(ldapTask);
        taskList.add(dbTask);
//21．使用Executors的newCachedThreadPool创建一个新的ThreadPoolExecutor对象，并创建一个名为result的字符型变量
        ExecutorService executorService = Executors.newCachedThreadPool();
//22．调用执行器的invokeAny()方法。该方法接收taskList作为参数，并返回String型结果，并且将该方法的返回值打印到控制台：
        String result;
        try {
            result  = executorService.invokeAny(taskList);
            System.out.printf("Main : Result: %s\n", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//23．调用shutdown()方法终止执行器，并在控制台打印信息来表明程序已经终止：
        executorService.shutdown();
        System.out.println("Main: End of the Execution\n");
    }
}
