package com.demo.reflect;

import java.lang.reflect.Method;
import java.util.Arrays;

public class BridgeMethodTest {

    public static void main(String[] args) {
//        P p = new S();
//        p.test(new Object());

        Class<S> sClass = S.class;
        Method[] methods = sClass.getMethods();
        for (Method method : methods){
            System.out.println(method.getName()+ " : "+ Arrays.toString(method.getParameterTypes())+"---"+method.isBridge());
        }
    }

}


class P<T>{
    public T test(T t){
        return t;
    }
}

class S extends P<String>{
    @Override
    public String test(String s) {
        return s;
    }
}