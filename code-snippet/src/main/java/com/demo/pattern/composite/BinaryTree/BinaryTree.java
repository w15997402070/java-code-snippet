package com.demo.pattern.composite.BinaryTree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class BinaryTree {

    private static Logger logger = LoggerFactory.getLogger(BinaryTree.class);
    Node root;

    /**
     * A
     * B		  C
     *     D	E		  F
     */
    public BinaryTree() {
        root = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");
        nodeB.setLeftChildTree(nodeD);
        nodeB.setRightChildTree(nodeE);
        nodeC.setRightChildTree(nodeF);
        root.setLeftChildTree(nodeB);
        root.setRightChildTree(nodeC);
    }

    public int getHeight() {
        return getHeight(root);
    }

    public int getHeight(Node root) {

        if (root == null) {
            return 0;
        } else {
            int left = getHeight(root.getLeftChildTree());
            int right = getHeight(root.getRightChildTree());
            return left > right ? left + 1 : right + 1;
        }
    }

    public int getSize() {
        return getSize(root);
    }

    public int getSize(Node root) {
        if (root == null) {
            return 0;
        } else {
            int left = getSize(root.getLeftChildTree());
            int right = getSize(root.getRightChildTree());
            return left + right + 1;
        }
    }

    /**
     * 前序遍历
     */
    public void preOrder(Node root) {
        if (root == null) {
            return;
        } else {
            logger.info("preOrder : {}", root.getData());

            preOrder(root.getLeftChildTree());
            preOrder(root.getRightChildTree());
        }
    }

    /**
     * 中序遍历
     *
     * @param root
     */
    public void midOrder(Node root) {
        if (root == null) {
            return;
        } else {
            midOrder(root.getLeftChildTree());
            logger.info("midOrder : {}", root.getData());
            midOrder(root.getRightChildTree());
        }
    }

    public void proOrder(Node root) {

        if (root == null) {
            return;
        } else {
            preOrder(root.getLeftChildTree());
            preOrder(root.getRightChildTree());
            logger.info("proOrder : {}", root.getData());
        }
    }


    /**
     * 前序遍历，非迭代,利用栈，要遍历一个节点，就先把它压入，再弹出，输出数据，把此节点的右节点压入，再把左节点压入
     *
     * @param node
     */
    public void nonRecOrder(Node node) {
        if (node == null) {
            return;
        } else {
            Stack<Node> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                Node pop = stack.pop();
                logger.info("nonRecOrder : {}", pop.getData());
                if (pop.getLeftChildTree() != null) {
                    stack.push(pop.getLeftChildTree());
                }
                if (pop.getRightChildTree() != null) {
                    stack.push(pop.getRightChildTree());
                }
            }
        }
    }
}
