package com.demo.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 归并排序
 * 把序列分成长度相同的两个子序列，当无法继续往下分时（也就是每个子序列中只有一个数据时），就对子序列进行归并
 * @author wang
 */
@Slf4j
public class MergeSort {

    public void mergeSort(int [] arr){
        sort(arr,0,arr.length);
        log.info(Arrays.toString(arr));
    }

    public void sort(int [] arr,int left,int right){
        //递归退出条件
        if (left >= right){
            return;
        }
        int center = (right+left)/2;
        sort(arr,0,center);
        sort(arr,center+1,right);
        merge(arr,left,center,right);

    }

    /**
     * 将两个数组进行归并
     * @param arr 已经局部有序的数组
     * @param left 左数组的第一个元素的索引
     * @param center 左数组的最后一个元素索引
     *               center+1 是右边数组的第一个元素索引
     * @param right 右边数组最后一个元素的索引
     */
    public void merge(int [] arr,int left,int center,int right){
        int [] tmpArr = new int [arr.length];
        //右数组第一个元素索引
        int mid = center+1;
        //记录临时数组的索引
        int third = left;
        int tmp = left;
        while (left <= center && mid <= right){
            //两个数组中最小的放回原数组
            if (arr[left] <= arr[mid]){
                tmpArr[third] = arr[left];
                left++;
            }else {
                tmpArr[third] = arr[mid];
                mid++;
            }
            third++;
        }
        while (mid <= right){
            tmpArr[third] = arr[mid];
            third++;
            mid++;
        }
        while (left <= center){
            tmpArr[third] = arr[left];
            third++;
            left++;
        }
        //将临时数组中的内容拷贝回原数组
        //原left-right 范围内的内容被复制回原数组
        while (tmp <= right){
            arr[tmp] = tmpArr[tmp++];
        }
    }

    public static void main(String[] args) {
        int [] arr = {5,9,3,1,2,8,4,7,6};
        new MergeSort().mergeSort(arr);
    }
}
