package com.demo.pattern.adapter;


public class Test {
    public static void main(String[] args) {
        Target target = new ClassAdapter();
        target.request();
    }
}
