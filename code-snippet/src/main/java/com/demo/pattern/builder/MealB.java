package com.demo.pattern.builder;

public class MealB extends Builder {
    @Override
    public void buildFood() {
        meal.setFood("汉堡");
    }

    @Override
    public void buildDrink() {
        meal.setDrink("果汁");
    }
}
