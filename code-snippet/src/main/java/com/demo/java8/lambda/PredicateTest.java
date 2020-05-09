package com.demo.java8.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PredicateTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("orange");
        list.add("red");
        list.add("a");

       Predicate<String> p = (String s) -> {
            return  s.length() > 2;
        };
        Predicate<String> pAnd = (String s) -> {
            return s.length() < 6;
        };
        Predicate<String> pOr = (String s) -> {
            return s.length() == 1;
        };
        list.stream().filter(p.and(pAnd).or(pOr)).forEach(s -> System.out.println(s));
    }
}
