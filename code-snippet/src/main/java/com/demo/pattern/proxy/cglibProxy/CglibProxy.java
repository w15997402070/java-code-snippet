package com.demo.pattern.proxy.cglibProxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;


public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        //���ô����������
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     * ��������Ŀ����ķ����ĵ���
     * obj Ŀ�����ʵ��
     * m Ŀ�귽���ķ������
     * args �����Ĳ���
     * proxy �������ʵ��
     */
    @Override
    public Object intercept(Object obj, Method m, Object[] args, MethodProxy proxy) throws Throwable {
        Long startTime = System.currentTimeMillis();
        System.out.println("�𳵿�ʼ��ʻ...");
        //��������ø���ķ���
        proxy.invokeSuper(obj, args);
        Long endTime = System.currentTimeMillis();
        System.out.println("�𳵽�����ʻ...������ʻʱ��: " + (endTime - startTime) + "����");
        return null;
    }

}
