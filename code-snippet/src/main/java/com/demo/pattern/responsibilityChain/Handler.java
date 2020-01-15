package com.demo.pattern.responsibilityChain;

/**
 * 抽象处理器
 */
public abstract class Handler {

    public Handler nextHandle;

    public void setNextHandle(Handler nextHandle) {
        this.nextHandle = nextHandle;
    }

    public abstract void discount(double discount);
}
