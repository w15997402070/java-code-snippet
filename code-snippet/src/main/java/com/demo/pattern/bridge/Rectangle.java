package com.demo.pattern.bridge;

public class Rectangle extends Shape {
    @Override
    public void draw() {
        color.bepaint("长方形");
    }
}
