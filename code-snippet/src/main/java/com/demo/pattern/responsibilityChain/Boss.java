package com.demo.pattern.responsibilityChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 老板
 */
public class Boss extends Handler {

    private static Logger logger = LoggerFactory.getLogger(Boss.class);

    @Override
    public void discount(double discount) {
        if (discount <= 0.5) {
            logger.info("{},Boss处理了请求 : {}", this.getClass().getName(), discount);
        } else {
            logger.info("{},boss拒绝了请求 : {}", this.getClass().getName(), discount);
        }
    }
}
