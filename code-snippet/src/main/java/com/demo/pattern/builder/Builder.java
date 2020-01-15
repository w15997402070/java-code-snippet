package com.demo.pattern.builder;

/**
 * 抽象建造者 builder
 */
public abstract class Builder {

    Meal meal = new Meal();

    public abstract void buildFood();

    public abstract void buildDrink();

    public Meal getMeal() {
        return meal;
    }

}
