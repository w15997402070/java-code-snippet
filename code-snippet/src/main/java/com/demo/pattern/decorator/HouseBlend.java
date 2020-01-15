package com.demo.pattern.decorator;

public class HouseBlend extends Beverage {

    @Override
    public double cost() {
        return 0.89D;
    }

    public HouseBlend() {
        description = "House Blend coffee";
    }
}
