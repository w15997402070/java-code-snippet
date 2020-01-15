package com.demo.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 选择排序
 * 从待排序的数据中寻找最小值，将其与序列最左边的数字进行交换
 *
 * @author wang
 */
@Slf4j
public class SelectSort {

    /**
     * int [] arr = {5,9,3,1,2,8,4,7,6};
     * 第一次遍历以 0 个数据为最小值,依次拿后面的元素比较
     * 如果比最小值小,就先两个位置交换数据
     * 然后把赋值给最小值
     *
     * @param arr
     */
    public void sort(int[] arr) {
        int min;
        for (int i = 0; i < arr.length; i++) {
            min = arr[i];
            for (int j = i; j < arr.length; j++) {
                int compare = arr[j];
                if (compare < min) {
                    //交换数据
                    arr[j] = min;
                    //最小数据赋值给最小值所在的数组的位置
                    arr[i] = compare;
                    //再赋值给最小值
                    min = arr[i];
                }
            }
        }
        log.info(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        int[] arr = {5, 9, 3, 1, 2, 8, 4, 7, 6};
        new SelectSort().sort(arr);
    }


}
