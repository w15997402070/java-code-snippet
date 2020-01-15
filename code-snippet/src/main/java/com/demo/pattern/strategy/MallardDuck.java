package com.demo.pattern.strategy;

public class MallardDuck extends Duck {


    public MallardDuck() {
        flyBehavior = new FlyWithSwings();
        quackBehavior = new Squeak();
    }

    @Override
    public void display() {
        System.out.println("����һֻ��ͷѼ");
    }

    @Override
    public void swim() {
        System.out.println("��ͷѼ����Ӿ");
    }
}
