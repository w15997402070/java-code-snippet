package com.demo.heap;

import sun.reflect.generics.tree.Tree;

import java.util.TreeMap;

/**
 * 数组实现最大堆
 * @author wang
 */
public class MaxHeap {

    public int [] data;
    public int size;
    public int capacity;

    public MaxHeap(int maxSize){
        //将0的位置空出,所以数组容量要加1
        data = new int [maxSize+1];
        this.size = 0;
        this.capacity = maxSize;
    }

    public int size(){
        return size;
    }
    public int insert(int e){
        //堆满了
        if (size == capacity){
            System.out.println("堆已满");
            return -1;
        }
        data[size+1] = e;
        size++;
        shiftUp(size);
        return size;
    }

    /**
     * 调整树结构,将插入的元素和父元素比较
     * @param size 数据量
     */
    public void shiftUp(int size){
        //父节点的索引
        int p = size/2;
        //比较当前元素和父元素
        while (size > 1 && data[size] < data[p]){
            int tmp = data[p];
            data[p] = data[size];
            data[size] = tmp;
            size = p;
            p = p/2;
        }
    }

    /**
     * 递归实现
     * 调整树结构,将插入的元素和父元素比较
     * @param size
     */
    public void shiftUpByRecursion(int size){
        int p = size/2;
        if (p == 0){
            return;
        }
        if (data[size] > data[p]){
            int tmp = data[p];
            data[p] = data[size];
            data[size] = tmp;
            shiftUpByRecursion(p);
        }
    }

    public int deleteMax(){
        if(size == 0){
            System.out.println("堆已经是空的了！");
            return -1 ;
        }
        int d = data[1];
        //将最后的元素放到第一个位置上
        data[1] = data[size];
        size--;
        shiftDown(1);
        return d;
    }

    /**
     * 元素删除之后调整堆结构
     * @param i
     */
    public void shiftDown(int i){
        //数组不能越界,所以节点的子节点的索引应该 2*i < size
        while (2*i <= size){
           int j = 2*i;
           //比较两个孩子节点,指向孩子节点中较大的那个
           if (j+1 <= size && data[2*i] > data[j+1]){
               j = 2*i+1;
           }
           if (data[i] < data[j]){
               break;
           }
           //交换两个位置的数据
           int tmp = data[i];
           data[i] = data[j];
           data[j] = tmp;
           i = j;
        }
    }
    /**
     * 递归实现
     * 元素删除之后调整堆结构
     * @param i
     */
    public void shiftDownByRecursion(int i){
        if (2*i > size){
            return;
        }
        int j = 2*i;
        if ( j+1 <= size && data[2*i] < data[j+1]){
            j = 2*i+1;
        }
        if (data[i] > data[j]){
            return;
        }
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
        i = j;
        shiftDownByRecursion(i);
    }
}
