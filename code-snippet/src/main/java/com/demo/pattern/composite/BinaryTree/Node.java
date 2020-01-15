package com.demo.pattern.composite.BinaryTree;

public class Node<T> {

    private Integer index;
    private Node leftChildTree;
    private Node rightChildTree;
    private T data;

    public Node(T data) {
        this.data = data;
        leftChildTree = null;
        rightChildTree = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Node getLeftChildTree() {
        return leftChildTree;
    }

    public void setLeftChildTree(Node leftChildTree) {
        this.leftChildTree = leftChildTree;
    }

    public Node getRightChildTree() {
        return rightChildTree;
    }

    public void setRightChildTree(Node rightChildTree) {
        this.rightChildTree = rightChildTree;
    }
}
