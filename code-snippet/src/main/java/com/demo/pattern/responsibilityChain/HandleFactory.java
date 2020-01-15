package com.demo.pattern.responsibilityChain;

/**
 * 创建处理对象工厂
 */
public class HandleFactory {

    public static Handler createHanlder() {
        Handler sales = new Sales();
        Handler manager = new Manager();
        Handler boss = new Boss();

        sales.setNextHandle(manager);
        manager.setNextHandle(boss);
        return sales;
    }
}
