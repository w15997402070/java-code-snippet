package com.demo.pattern.proxy.staticProxy;

import java.util.Random;

public class Car implements Moveable {

    @Override
    public void move() {
        try {
            //ʵ�ֿ���
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
