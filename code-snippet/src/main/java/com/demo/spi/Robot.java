package com.demo.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author wang
 * @since 2020/10/11
 */
@SPI
public interface Robot {

    /**
     * sayHello
     */
    void sayHello();
}
