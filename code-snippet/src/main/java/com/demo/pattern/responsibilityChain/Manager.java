package com.demo.pattern.responsibilityChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理者
 */
public class Manager extends Handler {

    private static Logger logger = LoggerFactory.getLogger(Manager.class);

    @Override
    public void discount(double discount) {
        if (discount <= 0.3) {
            logger.info("{},Manager处理了请求 : {}", this.getClass().getName(), discount);
        } else {
            nextHandle.discount(discount);
        }
    }
}
