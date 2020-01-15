package com.demo.pattern.bridge;

public class Test {

    public static void main(String[] args) {
        Color white = new White();
        Shape square = new Square();
        square.setColor(white);
        square.draw();
    }
}
