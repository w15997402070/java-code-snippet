package com.demo.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @author wang
 * @since 2020/10/11
 */
public class DubboSpiTest {
    public static void main(String[] args) {

        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }
}
