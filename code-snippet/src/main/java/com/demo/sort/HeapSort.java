package com.demo.sort;

import com.demo.heap.MaxHeap;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 堆排序
 * 1. 先将所有的元素存入堆中
 * 2. 从降序排列的堆中取出数据时会从最大的数据开始取，所以将取出的数据反序输出，排序就完成了。
 * 堆排序一开始需要将n个数据存进堆里，所需时间为O（nlogn）。
 * 排序过程中，堆从空堆的状态开始，逐渐被数据填满。
 * 由于堆的高度小于log2n，所以插入1个数据所需要的时间为O（logn）。
 * 每轮取出最大的数据并重构堆所需要的时间为O（logn）。
 * 由于总共有n轮，所以重构后排序的时间也是O（nlogn）。
 * 因此，整体来看堆排序的时间复杂度为O（nlogn）。
 * 这样来看，堆排序的运行时间比之前讲到的冒泡排序、选择排序、插入排序的时间O（n2）都要短，
 * 但由于要使用堆这个相对复杂的数据结构，所以实现起来也较为困难。
 * @author wang
 */
@Slf4j
public class HeapSort {

    public static void main(String[] args) {
        int [] arr = {5,9,3,1,2,8,4,7,6};
        new HeapSort().heapSort(arr);
    }

    public void heapSort(int [] arr){
        MaxHeap maxHeap = new MaxHeap(arr.length);
        for (int i = 0; i < arr.length; i++) {
            maxHeap.insert(arr[i]);
        }
        log.info(Arrays.toString(maxHeap.data));
        int [] sortArr = new int [arr.length];

        for (int i = 0; i < arr.length ; i++) {
            sortArr[i] = maxHeap.deleteMax();
        }
        log.info(Arrays.toString(sortArr));
    }
}
