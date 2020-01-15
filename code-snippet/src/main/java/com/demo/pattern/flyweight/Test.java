package com.demo.pattern.flyweight;

public class Test {

    public static void main(String[] args) {

        Flyweight flyweightx = FlyweightFactory.getFlyweight("x");
        flyweightx.operate("操作x");
        Flyweight flyweighty = FlyweightFactory.getFlyweight("y");
        flyweighty.operate("操作y");
        Flyweight flyweightx1 = FlyweightFactory.getFlyweight("x");
        flyweightx1.operate("操作x1");
    }
}
