## 运行多个任务并处理第一个返回结果

一.创建`UserValidator`类

```java
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
```

二. 创建`ValidatorTask`类

```java
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
```

三.创建测试类`TestMain`

```java
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
```

本案例包含两个随机返回的布尔型的`UserValidato`r对象。每个`UserValidator`对象都由一个实现了Callable接口的`ValidatorTask`对象所调用。如果`UserValidator`类中的`validate()`方法返回false，则`ValidatorTask`类会抛出一个异常；否则返回true。若是提交了两个返回true或是抛出异常的任务，则它们会有如下4种可能。

* 两个任务都返回true。然后`invokeAny()`方法会返回第一个执行完成任务的名称。
*  第一个任务返回true，而第二个任务抛出异常。然后，`invokeAny()`方法会返回第一个任务的名称。
* 第一个任务抛出异常，而第二个任务返回true。然后，`invokeAny()`方法会返回第二个任务的名称。
* 两个任务都抛出异常。在这种情况下，`invokeAny()`方法抛出一个`Execution-Exception`异常。

`ThreadPoolExecutor`提供了另一个版本的`invokeAny()`方法。

* `invokeAny(Collection<? extends Callable> tasks, longtimeout, TimeUnit unit)`：该方法执行全部任务并返回第一个正常运行结束且未抛出异常和未超过设定时间的任务执行结果。TimeUnit类对象是一个枚举类，它包括DAYS、HOURS、MICROSECONDS、MILLISECONDS、MINUTES、NANOSECONDS、和SECONDS。