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
    }
}
