package com.demo.pattern.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Adapter {

    private static Logger logger = LoggerFactory.getLogger(Adapter.class);

    public void specificRequest() {

        logger.info("适配器中代码被调用");
    }
}
