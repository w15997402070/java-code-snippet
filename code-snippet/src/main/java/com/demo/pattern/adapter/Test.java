package com.demo.pattern.adapter;

import org.springframework.beans.BeanUtils;

public class Test {
    public static void main(String[] args) {
        Target target = new ClassAdapter();
        target.request();
    }
}
