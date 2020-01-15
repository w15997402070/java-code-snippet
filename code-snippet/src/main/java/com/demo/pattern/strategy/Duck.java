package com.demo.pattern.strategy;

public abstract class Duck {

    public FlyBehavior flyBehavior;
    public QuackBehavior quackBehavior;

    /**
     * ���е�Ѽ�Ӷ����
     */
    @Deprecated
    public void quack() {

    }

    public void performQuack() {
        quackBehavior.quack();
    }

    public void performFly() {
        flyBehavior.fly();
    }

    /**
     * Ѽ�Ӷ�����Ӿ
     */
    public abstract void swim();

    /**
     * Ѽ�ӵ����
     */
    public abstract void display();


    public FlyBehavior getFlyBehavior() {
        return flyBehavior;
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public QuackBehavior getQuackBehavior() {
        return quackBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

}
