package com.demo.pattern.proxy.jdkProxy;

import com.demo.pattern.proxy.staticProxy.Car;
import com.demo.pattern.proxy.staticProxy.Moveable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


/**
 * jdk��̬���������
 *
 * @author wang
 */
public class JdkProxyTest {

    public static void main(String[] args) {
        logProxy();
    }

    private static void timeHandlerProxy() {
        Car car = new Car();
        InvocationHandler invocationHandler = new TimeHandler(car);
        Class<?> class1 = car.getClass();
        /**
         * loader �������
         * interface ʵ�ֽӿ�
         * h InvocationHandler
         */
        //Proxy.newProxyInstance(loader, interfaces, h);
        Moveable moveable = (Moveable) Proxy.newProxyInstance(class1.getClassLoader(),
                class1.getInterfaces(), invocationHandler);
        moveable.move();

        Class<?>[] interfaces = class1.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            String typeName = anInterface.getTypeName();
            System.out.println(typeName);
        }
    }

    private static void logProxy(){
        Car car = new Car();
        Class<? extends Car> carClass = car.getClass();
        Moveable instance = (Moveable)Proxy.newProxyInstance(carClass.getClassLoader(),
                carClass.getInterfaces(),
                new CarLogProxy(car));
        instance.move();
    }
}
