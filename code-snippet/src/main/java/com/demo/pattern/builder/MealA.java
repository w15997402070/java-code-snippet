package com.demo.pattern.builder;

/**
 * 具体的建造者 套餐A
 */
public class MealA extends Builder {
    @Override
    public void buildFood() {
        meal.setFood("薯条");
    }

    @Override
    public void buildDrink() {
        meal.setDrink("可乐");
    }
}
