package com.demo.pattern.flyweight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {

    private static Logger logger = LoggerFactory.getLogger(FlyweightFactory.class);
    private static Map<String, Flyweight> pool = new HashMap<>();

    public static Flyweight getFlyweight(String extrinsic) {
        Flyweight flyweight = null;
        if (pool.containsKey(extrinsic)) {
            flyweight = pool.get(extrinsic);
            logger.info("已有 {} Flyweight,直接从池中获取", extrinsic);
        } else {
            flyweight = new ConcreteFlyweight(extrinsic);
            pool.put(extrinsic, flyweight);
            logger.info("创建 {} Flyweight,并加入到池中", extrinsic);
        }
        return flyweight;
    }
}
