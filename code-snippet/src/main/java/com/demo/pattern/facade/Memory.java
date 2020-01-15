package com.demo.pattern.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Memory {

    private static Logger logger = LoggerFactory.getLogger(Memory.class);

    public void start() {
        logger.info("Memory start ...");
    }

    public void shutdown() {
        logger.info("Memory shutdown ...");
    }
}
