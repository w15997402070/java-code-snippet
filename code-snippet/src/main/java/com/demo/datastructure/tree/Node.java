package com.demo.datastructure.tree;

/**
 * Created by Administrator on 2020/5/26.
 */
public class Node {

    private String data;
    private Node lchild;
    private Node rchild;

    Node(String data){
        this.data = data;
    }

    Node(String data, Node lchild, Node rchild) {
         super();
         this.data = data;
         this.lchild = lchild;
         this.rchild = rchild;

    }

    public String getData() {
        return data;
    }

    public Node getLchild() {
        return lchild;
    }

    public Node getRchild() {
        return rchild;
    }
}
