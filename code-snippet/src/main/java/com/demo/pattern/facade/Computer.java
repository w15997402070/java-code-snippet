package com.demo.pattern.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Computer {

    private static Logger logger = LoggerFactory.getLogger(Computer.class);
    public CPU cpu;
    public Disk disk;
    public Memory memory;

    public Computer() {
        this.cpu = new CPU();
        this.disk = new Disk();
        this.memory = new Memory();
    }

    public void start() {
        logger.info("Computer begin start");
        cpu.start();
        disk.start();
        memory.start();
        logger.info("Computer start end!");
    }

    public void shutdown() {
        logger.info("Computer begin shutdown!");
        cpu.shutdown();
        disk.shutdown();
        memory.shutdown();
        logger.info("Computer shutdown end!");
    }
}
