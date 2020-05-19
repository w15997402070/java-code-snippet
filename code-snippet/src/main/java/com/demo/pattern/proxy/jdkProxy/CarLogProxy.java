package com.demo.pattern.proxy.jdkProxy;

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
