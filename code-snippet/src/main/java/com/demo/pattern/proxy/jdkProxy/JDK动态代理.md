# JDK动态代理

创建接口
```java
public interface Moveable {
    void move();
}
```

实现类Car

```java
import java.util.Random;

public class Car implements Moveable {

    @Override
    public void move() {
        try {
            //ʵ�ֿ���
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

```

jdk代理类
需要实现`java.lang.reflect.InvocationHandler`

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2020/5/19.
 */
public class CarLogProxy implements InvocationHandler {

    private Object moveable;

    public CarLogProxy(Object moveable){
        this.moveable = moveable;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始打印日志");
        method.invoke(moveable,args);
        System.out.println("打印日志结束");
        return null;
    }
}
```

创建代理实例并调用方法

```java
public class JdkProxyTest {

    public static void main(String[] args) {
        logProxy();
    }

    private static void logProxy(){
        Car car = new Car();
        Class<? extends Car> carClass = car.getClass();
        Moveable instance = (Moveable)Proxy.newProxyInstance(carClass.getClassLoader(),
                carClass.getInterfaces(), //获取Car类实现的接口
                new CarLogProxy(car));
        instance.move();
    }
}
```

