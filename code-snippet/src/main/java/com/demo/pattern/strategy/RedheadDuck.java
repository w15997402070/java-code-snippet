package com.demo.pattern.strategy;

public class RedheadDuck extends Duck {

    @Override
    public void display() {
        System.out.println("����һֻ��ͷѼ");
    }

    @Override
    public void swim() {
        System.out.println("��ͷѼ����Ӿ");
    }

}
