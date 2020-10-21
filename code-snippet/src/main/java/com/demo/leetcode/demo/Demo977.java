package com.demo.leetcode.demo;

import java.util.Arrays;

/**
 * @author wang
 * @since 2020/10/18
 */
public class Demo977 {
    public static void main(String[] args) {

        sortedSquares(new int [] {-4, -1, 0, 3, 10});
    }

    public static int[] sortedSquares(int[] args) {
        int [] resultArr = new int [args.length];
        for (int i = 0; i < args.length ; i++) {
            resultArr[i] = args[i] * args[i];
        }

        Arrays.sort(resultArr);
        return resultArr;
    }
}
