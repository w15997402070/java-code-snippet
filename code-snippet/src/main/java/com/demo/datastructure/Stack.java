package com.demo.datastructure;

/**
 * 栈链表实现
 *
 * @author wang
 */
public class Stack<Item> {

    /**
     * 栈顶(最近添加的元素)
     */
    private Node first;
    /**
     * 元素数量
     */
    private int N;

    private class Node<Item> {
        Item item;
        Node next;
    }

    private boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    /**
     * 向栈顶添加元素
     *
     * @param item
     */
    public void push(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    public Item pop() {
        Item item = (Item) first.item;
        first = first.next;
        N--;
        return item;
    }
}
