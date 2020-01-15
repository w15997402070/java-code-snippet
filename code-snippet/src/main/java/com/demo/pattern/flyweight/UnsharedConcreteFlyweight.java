package com.demo.pattern.flyweight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnsharedConcreteFlyweight extends Flyweight {

    private static Logger logger = LoggerFactory.getLogger(UnsharedConcreteFlyweight.class);

    public UnsharedConcreteFlyweight(String extrinsic) {
        super(extrinsic);
    }

    @Override
    public void operate(String extrinsic) {
        logger.info("不共享的具体的Flyweight : {}", extrinsic);
    }
}
