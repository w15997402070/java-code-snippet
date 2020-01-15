package com.demo.pattern.responsibilityChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 销售员
 */
public class Sales extends Handler {

    private static Logger logger = LoggerFactory.getLogger(Sales.class);

    @Override
    public void discount(double discount) {
        if (discount <= 0.1) {
            logger.info("{},Sales处理了请求 : {}", this.getClass().getName(), discount);
        } else {
            nextHandle.discount(discount);
        }
    }
}
