package com.demo.pattern.responsibilityChain;

import java.util.Random;

/**
 * 测试类
 */
public class Test {

    public static void main(String[] args) {

        Customer customer = new Customer();
        customer.setHandler(HandleFactory.createHanlder());
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            customer.discountReq(random.nextDouble());
        }
    }
}
