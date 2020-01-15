package com.demo.pattern.flyweight;

public abstract class Flyweight {

    /**
     * 内部状态
     */
    public String intrinsic;

    /**
     * 外部状态
     */
    protected final String extrinsic;

    public abstract void operate(String extrinsic);

    public Flyweight(String extrinsic) {
        this.extrinsic = extrinsic;
    }

    public String getIntrinsic() {
        return intrinsic;
    }

    public void setIntrinsic(String intrinsic) {
        this.intrinsic = intrinsic;
    }
}
