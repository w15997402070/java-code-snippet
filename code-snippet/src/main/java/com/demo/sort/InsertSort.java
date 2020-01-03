package com.demo.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 插入排序
 * 插入排序的思路就是从右侧的未排序区域内取出一个数据，然后将它插入到已排序区域内合适的位置上
 * 在插入排序中，需要将取出的数据与其左边的数字进行比较。
 * 就跟前面讲的步骤一样，如果左边的数字更小，就不需要继续比较，本轮操作到此结束，自然也不需要交换数字的位置。
 * 然而，如果取出的数字比左边已归位的数字都要小，就必须不停地比较大小，交换数字，直到它到达整个序列的最左边为止。
 * 具体来说，就是第k轮需要比较k-1次。
 * 因此，在最糟糕的情况下，第2轮需要操作1次，第3轮操作2次……第n轮操作n-1次，所以时间复杂度和冒泡排序的一样，都为O（n2）。
 * 和前面讲的排序算法一样，输入数据按从大到小的顺序排列时就是最糟糕的情况。
 * @author wang
 */
@Slf4j
public class InsertSort {

    public void insertSort(int [] arr){

        for (int i = 1; i < arr.length ; i++) {
             int tmp = arr[i];
             //用一个临时值存储待比较元素的下标
             int m = i;
            for (int j = i-1; j >= 0 ; j--) {
                int b = arr[j];
                if (tmp < b){
                    arr[j] = tmp;
                    arr[m] = b;
                    m = j;
                }
            }
        }
        log.info(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        int [] arr = {5,9,3,1,2,8,4,7,6};
        new InsertSort().insertSort(arr);
    }
}
