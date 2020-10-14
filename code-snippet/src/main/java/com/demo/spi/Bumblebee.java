package com.demo.spi;

/**
 * @author wang
 * @since 2020/10/11
 */
public class Bumblebee implements Robot {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}
