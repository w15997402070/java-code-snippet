package com.demo.pattern.proxy.staticProxy;

public class CarProxy implements Moveable {

    private Moveable car;

    public CarProxy(Moveable car) {
        this.car = car;
    }

    @Override
    public void move() {
        Long startTime = System.currentTimeMillis();
        System.out.println("������ʼ��ʻ...");
        car.move();
        Long endTime = System.currentTimeMillis();
        System.out.println("����������ʻ...������ʻʱ��: " + (endTime - startTime) + "����");

    }


}
