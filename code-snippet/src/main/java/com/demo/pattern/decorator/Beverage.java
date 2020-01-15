package com.demo.pattern.decorator;

public abstract class Beverage {

    String description = "unKnown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
