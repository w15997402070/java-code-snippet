package com.demo.pattern.proxy.cglibProxy;


public class CgilbTest {

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        Train train = (Train) cglibProxy.getProxy(Train.class);
        train.move();
    }
}
