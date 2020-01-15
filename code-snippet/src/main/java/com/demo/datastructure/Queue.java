package com.demo.datastructure;

import java.util.Iterator;

public class Queue<Item> {
    /**
     * 指向最早添加的节点的链接
     */
    private Node first;
    /**
     * 指向最近添加的节点的链接
     */
    private Node last;
    /**
     * 队列中的元素数量
     */
    private int N;

    private class Node<Item> {
        private Node next;
        private Item item;
    }

    private boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    /**
     * 向队尾添加元素
     *
     * @param item
     */
    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        N++;
    }

    /**
     * 从表头删除元素
     *
     * @return
     */
    public Item dequeue() {
        Item item = (Item) first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        N--;
        return item;
    }

//    public Iterator<Item> iterator(){
//        return new Li
//    }
}
