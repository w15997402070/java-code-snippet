package com.example.web.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SPITestMain {

    public static void main (String [] args){
        ServiceLoader<SPITestInterface> load = ServiceLoader.load(SPITestInterface.class);
        Iterator<SPITestInterface> iterator = load.iterator();
        while (iterator.hasNext()){
            SPITestInterface next = iterator.next();
            next.testSpi();
        }
    }
}
