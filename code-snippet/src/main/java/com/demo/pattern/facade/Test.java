package com.demo.pattern.facade;

public class Test {

    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.start();
        computer.shutdown();
    }
}
