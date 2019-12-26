package com.demo.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 冒泡排序
 * 从左边开始比较相邻的元素,大的往后移
 * 第 1 轮需要比较 n-1 次
 * 第 2 轮比较 n-2 次
 * 第 n-1 轮比较 1 次
 *
 * 所以 需要比较
 *  (n-1) + (n-2) + (n-3) + ... + 1
 * = ((n-1)+1) * n/2
 * = n*n/2
 *
 * 比较次数为固定值
 *
 * 交换数字的次数和输入数据的排列顺序有关。
 * 假设出现某种极端情况，如输入数据正好以从小到大的顺序排列，那么便不需要任何交换操作；
 * 反过来，输入数据要是以从大到小的顺序排列，那么每次比较数字后便都要进行交换。
 * 因此，冒泡排序的时间复杂度为O（n2）
 * @author wang
 */
@Slf4j
public class BubbleSort {

    public void sort(int [] arr){
        for (int i = 0; i < arr.length ; i++) {
            for (int j = 0; j < arr.length-1-i ; j++) {
                int previous = arr[j];
                int next = arr[j+1];
                if (previous > next){
                    arr[j+1] = previous;
                    arr[j] = next;
                }
            }
        }
        log.info(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        int [] arr = {5,9,3,1,2,8,4,7,6};
        new BubbleSort().sort(arr);
    }
}
