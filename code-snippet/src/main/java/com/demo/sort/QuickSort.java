package com.demo.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author wang
 */
@Slf4j
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {5, 9, 3, 1, 2, 8, 4, 7, 6};
        sort(arr);
        log.info(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int low, int high) {
        if (high <= low) {
            return;
        }
        //切分
        int j = partition(arr, low, high);
        sort(arr, low, j - 1);
        sort(arr, j + 1, high);
    }

    /**
     * @param arr
     * @param low
     * @param high
     * @return
     */
    private static int partition(int[] arr, int low, int high) {
        int i = low;
        int j = high + 1;
        int cmp = arr[low];
        while (true) {
            //1. 从数组的左端开始向右扫描直到找到一个大于切分元素(cmp)的元素
            while (arr[++i] < cmp) {
                if (i == high) {
                    break;
                }
            }
            //2. 从数组的右端开始向左扫描直到找到一个小于等于它的元素
            while (cmp < arr[--j]) {
                if (j == low) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }
            //3. 交换他们的位置
            exch(arr, i, j);

        }
        //将cmp = arr[j] 放到正确的位置
        exch(arr, low, j);
        log.info(Arrays.toString(arr));
        return j;
    }

    /**
     * 交换位置
     *
     * @param arr
     * @param i
     * @param j
     */
    private static void exch(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
        Collections.sort();
    }
}
