package com.demo.pattern.adapter;

public class ClassAdapter extends Adapter implements Target {
    @Override
    public void request() {
        specificRequest();
    }
}
