package com.demo.datastructure.tree;

import net.sf.jsqlparser.expression.Expression;

/**
 * Created by Administrator on 2020/5/26.
 */
public class ExpressionNode {

    private Expression data;
    private ExpressionNode left;
    private ExpressionNode right;

    public ExpressionNode(Expression data){
        this.data = data;
    }

    public ExpressionNode(ExpressionNode left, ExpressionNode expressionNode, Expression data){
        this.left = left;
        this.right = right;
        this.data = data;
    }

    public Expression getData() {
        return data;
    }

    public void setData(Expression data) {
        this.data = data;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }


}
