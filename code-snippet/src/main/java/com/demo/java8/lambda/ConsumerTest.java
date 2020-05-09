package com.demo.java8.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ConsumerTest {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("test1","1");
        map.put("test2","2");
        map.put("test3","3");
        map.put("test4","4");
//        map.forEach((k,v) -> {
//            System.out.println("key : "+k+" --- value : "+v);
//        });

        map.forEach(((BiConsumer<String, String>) (k, v) -> {
            System.out.println("key : " + k + " --- value : " + v);
        }).andThen(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                System.out.println("value : "+s2+" --- key : "+s);
            }
        }));
    }

    public void testBiConsumer(){

    }
}
