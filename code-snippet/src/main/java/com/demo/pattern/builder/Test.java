package com.demo.pattern.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {

        MealA mealA = new MealA();
        KFCWaiter waiter = new KFCWaiter(mealA);

        Meal m = waiter.construct();
        logger.info("套餐的组成部分,食物:{},饮料:{}", m.getFood(), m.getDrink());
    }
}
