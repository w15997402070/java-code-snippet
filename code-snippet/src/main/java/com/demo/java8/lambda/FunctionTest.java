package com.demo.java8.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("orange");
        list.add("red");
        list.add("a");

//        List<Integer> lengthList = getElementLength(list,(String s) -> s.length());

        List<Integer> lengthList = getElementLength(list, new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                System.out.println("Function : "+s);
                return s.length();
            }
        }.compose(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        System.out.println("compose : "+s);
                        return s+"==";
                    }

        }).andThen(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                System.out.println("andThen : "+integer);
                return integer+10;
            }
        }));
        lengthList.forEach( i -> {
            System.out.println(i);
        });
    }

    public static List<Integer> getElementLength(List<String> list, Function<String,Integer> function){
        List<Integer> lengthList = new ArrayList<>();
        list.forEach(s -> {
            lengthList.add(function.apply(s));
        });
        return lengthList;
    }

//    public static void lengthPlus(Function<Integer,Integer> plus,Integer length){
//        plus.apply(length);
//    }

    @Test
    public void testBiFunction(){
        BiFunction<String, String, String> function1 = (s1, s2) -> {
            String s3 = s1 + s2;
            return s3;
        };
        System.out.println(function1.apply("aa", "bb"));

        BiFunction<Integer, Integer, Integer> function2 = (a, b) -> a + b;
        System.out.println(function2.apply(100, 200));
    }

    @Test
    public void testBiFunctionAndThen(){
        BiFunction<Integer, Integer, Integer> function1 = (a, b) -> a + b;
        Function<Integer, Integer> function2 = (n) -> n*n;

        //Using andThen()
        System.out.println(function1.andThen(function2).apply(5, 3));
        System.out.println(function1.andThen(function2).apply(12, 2));
    }
}
