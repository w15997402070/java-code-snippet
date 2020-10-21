package com.demo.leetcode.demo;

import com.demo.graph.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 75. 颜色分类.
 * @author wang
 * @since 2020/10/19
 */
public class Demo75 {

    public static void main(String[] args) {

        int[] ints = {2, 0, 2, 1, 1, 0};
        sortColors(ints);
    }

    public static void sortColors(int[] nums) {

        int[] ints = new int[] {0,0,0};
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num == 0) {
                ints[0] = ints[0] + 1;
            } else if (num == 1) {
                ints[1] = ints[1] + 1;
            } else if (num == 2) {
                ints[2] = ints[2] + 1;
            }
        }
        int k = 0;
        for (int i = 0; i < ints.length; i++) {
            int anInt3 = ints[i];
            for (int j = 0; j < anInt3 ; j++) {
                nums[k] = i;
                k++;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            int r = nums[i];
            if (i == 0) {
                System.out.print("[" + r + ",");
            }else if (i == nums.length - 1) {
                System.out.print(r + "]");
            } else {
                System.out.print(r + ",");
            }
        }
    }
}
