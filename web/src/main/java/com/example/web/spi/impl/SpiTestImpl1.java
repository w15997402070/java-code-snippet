package com.example.web.spi.impl;


import com.example.web.spi.SPITestInterface;

public class SpiTestImpl1 implements SPITestInterface {
    @Override
    public void testSpi() {
        System.out.println("这是SpiTestImpl1");
    }
}
