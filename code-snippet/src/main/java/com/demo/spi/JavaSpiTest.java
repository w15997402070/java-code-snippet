package com.demo.spi;

import java.util.ServiceLoader;

/**
 * @author wang
 * @since 2020/10/11
 */
public class JavaSpiTest {

    public static void main(String[] args) {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}
