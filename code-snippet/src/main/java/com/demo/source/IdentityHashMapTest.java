package com.demo.source;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class IdentityHashMapTest {

    public static void main(String[] args) {
        Map<String,String> hashMap = new HashMap<String,String>();
        hashMap.put(new String("test"),"test1");
        hashMap.put(new String("test"),"test2");
        hashMap.forEach((k,v) -> {
            System.out.println("key : "+k+" | "+"value : "+v);
        });
        IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>();

        identityHashMap.put(new String("test"),"test1");
        identityHashMap.put(new String("test"),"test2");
        identityHashMap.put(null,"test3");
        identityHashMap.forEach((k,v) -> {
            System.out.println("key : "+k+" | "+"value : "+v);
        });
    }
}
