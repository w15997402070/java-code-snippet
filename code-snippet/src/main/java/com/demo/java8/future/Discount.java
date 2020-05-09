package com.demo.java8.future;

/**
 * Created by Administrator on 2020/5/8.
 *
 */
public class Discount {
    /**
     * 折扣代码
     */
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage){
            this.percentage = percentage;
        }
    }

    /**
     * 每个商店打折后的价格
     * @param quote
     * @return
     */
    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is "+Discount.apply(quote.getPrice(),quote.getDiscountCode());
    }

    /**
     * 计算打折后的价格
     * @param price
     * @param discountCode
     * @return
     */
    private static double apply(double price, Code discountCode) {
        delay();
        return price * (100 - discountCode.percentage)/100;
    }

    /**
     * 延迟操作
     */
    public static void delay(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
