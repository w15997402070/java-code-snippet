package com.demo.source;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class SingletonListDemo {

    public static void main(String[] args) {
        List<String> singletonList = Collections.singletonList("SingletonList");
        log.info("结果 : {}",singletonList);
    }
}
