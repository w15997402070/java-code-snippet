package com.demo.pattern.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Disk {

    private static Logger logger = LoggerFactory.getLogger(Disk.class);

    public void start() {
        logger.info("Disk start ...");
    }

    public void shutdown() {
        logger.info("Disk shutdown ...");
    }
}
