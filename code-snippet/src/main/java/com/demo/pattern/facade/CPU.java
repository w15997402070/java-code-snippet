package com.demo.pattern.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CPU {

    private static Logger logger = LoggerFactory.getLogger(CPU.class);

    public void start() {
        logger.info("CPU start ...");
    }

    public void shutdown() {
        logger.info("CPU shutdown ...");
    }
}
