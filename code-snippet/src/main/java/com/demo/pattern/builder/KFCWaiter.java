package com.demo.pattern.builder;

/**
 * Director(指挥者)
 */
public class KFCWaiter {

    private Builder builder;

    public KFCWaiter(Builder builder) {
        this.builder = builder;
    }

    public Meal construct() {
        builder.buildFood();
        builder.buildDrink();
        Meal meal = builder.getMeal();
        return meal;
    }
}
