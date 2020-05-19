package com.demo.pattern.proxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler {

    private Object Target;

    public TimeHandler(Object target) {
        super();
        Target = target;
    }


    /**
     * ����:
     * Object ���������
     * method ������ķ���
     * args �����Ĳ���
     * <p>
     * ����ֵ :
     * Object �����ķ���ֵ
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Long startTime = System.currentTimeMillis();
        System.out.println("代理开始");
        method.invoke(Target);
        Long endTime = System.currentTimeMillis();
        System.out.println("代理结束 , 执行时间 : " + (endTime - startTime) + " 毫秒");
        return null;
    }

}
