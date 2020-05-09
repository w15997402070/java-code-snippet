package com.demo.java8.lambda;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorTest {
    public static void main(String[] args) {
       BinaryOperator add =  new BinaryOperator<Integer>(){
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer+integer2;
            }
        };
       System.out.println(add.apply(5,6));

        BinaryOperator<Integer> comparator = (Integer param1,Integer param2) -> {
            return param2 > param1 ? param2:param1;
        };

        System.out.println(comparator.apply(8,2));

        Comparator<Integer> comparator1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 > o2 ? 1:-1;
            }
        };

        BinaryOperator<Integer> maxBy = BinaryOperator.maxBy(comparator1);
        System.out.println(maxBy.apply(2,3));
    }
}
