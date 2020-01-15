package com.demo.pattern.bridge;

public class Circle extends Shape {
    @Override
    public void draw() {
        color.bepaint("圆形");
    }
}
