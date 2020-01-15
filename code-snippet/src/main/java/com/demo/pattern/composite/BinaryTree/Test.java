package com.demo.pattern.composite.BinaryTree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        logger.info("树的高度为 : {}" + binaryTree.getHeight());
        logger.info("树的节点数量为 : {}" + binaryTree.getSize());
        binaryTree.preOrder(binaryTree.root);
        binaryTree.midOrder(binaryTree.root);
        binaryTree.proOrder(binaryTree.root);
        binaryTree.nonRecOrder(binaryTree.root);
    }
}
