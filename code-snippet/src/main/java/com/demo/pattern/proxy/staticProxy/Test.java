package com.demo.pattern.proxy.staticProxy;

public class Test {

    public static void main(String[] args) {
        Moveable carMoveable = new Car();
        CarProxy carProxy = new CarProxy(carMoveable);
        carProxy.move();
    }
}
