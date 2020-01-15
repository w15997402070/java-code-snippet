package com.demo.pattern.responsibilityChain;

/**
 * 客户
 */
public class Customer {
    private Handler pHandler;

    public void setHandler(Handler pHandler) {
        this.pHandler = pHandler;
    }

    public void discountReq(double discount) {
        pHandler.discount(discount);
    }
}
