package com.demo.pattern.decorator;

public class Espresso extends Beverage {

    @Override
    public double cost() {
        return 1.99D;
    }

    public Espresso() {
        description = "Espresso";
    }

}
