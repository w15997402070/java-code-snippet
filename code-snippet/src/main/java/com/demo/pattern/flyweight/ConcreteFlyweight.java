package com.demo.pattern.flyweight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcreteFlyweight extends Flyweight {

    private static Logger logger = LoggerFactory.getLogger(ConcreteFlyweight.class);

    public ConcreteFlyweight(String extrinsic) {
        super(extrinsic);
    }

    /**
     * 根据外部状态进行逻辑处理
     *
     * @param extrinsic
     */
    @Override
    public void operate(String extrinsic) {
        logger.info("具体的 Flyweight : {}", extrinsic);
    }
}
