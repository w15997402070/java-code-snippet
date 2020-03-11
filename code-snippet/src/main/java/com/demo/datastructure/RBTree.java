package com.demo.datastructure;

import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 红黑树java实现
 * 1.红黑树是每个节点都带有颜色属性的二叉查找树，颜色为红色或黑色。在二叉查找树强制一般要求以外，对于任何有效的红黑树我们增加了如下的额外要求：
 * <p>
 * 2.节点是红色或黑色。
 * 3.根是黑色。
 * 4.所有叶子都是黑色（叶子是NIL节点）。
 * 5.每个红色节点必须有两个黑色的子节点。（从每个叶子到根的所有路径上不能有两个连续的红色节点。）
 * 6.从任一节点到其每个叶子的所有简单路径都包含相同数目的黑色节点。
 *
 * @author wang
 */
public class RBTree<T extends Comparable<T>> {

    private static class RBTreeNode<T extends Comparable<T>> {
        public T value;
        public RBTreeNode<T> parent;
        public boolean isRed;
        public RBTreeNode<T> left;
        public RBTreeNode<T> right;

        public RBTreeNode() {

        }

        public RBTreeNode(T value) {
            this.value = value;
        }

        public RBTreeNode(T value, boolean isRed) {
            this.value = value;
            this.isRed = isRed;
        }

        /**
         * 判断是否是叶子节点
         *
         * @return
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }


        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public RBTreeNode<T> getParent() {
            return parent;
        }

        public void setParent(RBTreeNode<T> parent) {
            this.parent = parent;
        }

        public boolean isRed() {
            return isRed;
        }

        public void setRed(boolean red) {
            isRed = red;
        }

        public RBTreeNode<T> getLeft() {
            return left;
        }

        public void setLeft(RBTreeNode<T> left) {
            this.left = left;
        }

        public RBTreeNode<T> getRight() {
            return right;
        }

        public void setRight(RBTreeNode<T> right) {
            this.right = right;
        }

        void makeRed() {
            isRed = true;
        }

        void makeBlack() {
            isRed = false;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private final RBTreeNode<T> root;

    private AtomicLong size = new AtomicLong(0);

    public RBTree() {
        this.root = new RBTreeNode<>();
    }

    /**
     * 获取祖父节点(父节点的父节点)
     *
     * @param node
     * @return
     */
    public RBTreeNode getGrandparent(RBTreeNode<T> node) {
        return node.parent.parent;
    }

    /**
     * 获取叔父节点
     *
     * @param node
     * @return
     */
    public RBTreeNode getUncle(RBTreeNode<T> node) {
        if (node.parent.value.equals(getGrandparent(node).left.value)) {
            return getGrandparent(node).right;
        } else {
            return getGrandparent(node).left;
        }
    }

    /**
     * 情形1 :  新节点位于树的根上,没有父节点,只需要变一下颜色为黑色就行
     *
     * @param node
     */
    public void insert_case1(RBTreeNode<T> node) {
        if (node.parent == null) {
            node.isRed = false;
        } else {
            insert_case2(node);
        }

    }

    /**
     * 情形2 : 新节点的父节点是黑色,树仍然有效不要变动
     *
     * @param node
     */
    public void insert_case2(RBTreeNode<T> node) {
        //父节点是黑色
        if (!node.parent.isRed) {
            return;
        } else {
            insert_case3(node);
        }
    }

    /**
     * 情形3 : 如果父节点P和叔父节点U二者都是红色，（此时新插入节点N做为P的左子节点或右子节点都属于情形3，
     * 这里右图仅显示N做为P左子的情形）则我们可以将它们两个重绘为黑色并重绘祖父节点G为红色（用来保持性质5）。
     * 现在我们的新节点N有了一个黑色的父节点P。因为通过父节点P或叔父节点U的任何路径都必定通过祖父节点G，在这些路径上的黑节点数目没有改变。但是，红色的祖父节点G可能是根节点，这就违反了性质2，也有可能祖父节点G的父节点是红色的，这就违反了性质4。为了解决这个问题，我们在祖父节点G上递归地进行情形1的整个过程。（把G当成是新加入的节点进行各种情形的检查）
     *
     * @param node
     */
    private void insert_case3(RBTreeNode<T> node) {
        if (getUncle(node) != null && getUncle(node).isRed) {
            node.parent.isRed = false;
            getUncle(node).isRed = false;
            getGrandparent(node).isRed = true;
            insert_case1(node);
        } else {
            insert_case4(node);
        }
    }

    private void insert_case4(RBTreeNode<T> node) {
    }


    public void rightRotate(RBTreeNode root) {
        //根的左节点的右节点变成根的左节点
        RBTreeNode leftRight = root.left.right;
        root.left = leftRight;
        //根本身在旋转后会变成新的根的右子节点
        RBTreeNode left = root.left;
        left.right = root;
        root = left;
    }

    public T addNode(T value) {
        RBTreeNode<T> t = new RBTreeNode<T>(value);
        return addNode(t);
    }

    private T addNode(RBTreeNode<T> node) {
        node.setLeft(null);
        node.setRight(null);
        node.setRed(true);
        if (root.getLeft() == null) {
            root.setLeft(node);
            node.setRed(false);
            size.incrementAndGet();
        } else {
            RBTreeNode<T> x = findParentNode(node);
        }
        return node.value;
    }

    private RBTreeNode<T> findParentNode(RBTreeNode<T> x) {
        RBTreeNode<T> dataRoot = getRoot();
        RBTreeNode<T> child = dataRoot;
        while (child != null) {
            int cmp = child.getValue().compareTo(x.getValue());
            if (cmp == 0) {
                return child;
            }
            if (cmp > 0) {
                dataRoot = child;
                child = child.getLeft();
            } else if (cmp < 0) {
                dataRoot = child;
                child = child.getRight();
            }
        }
        return dataRoot;

    }

    /**
     * get the root node
     *
     * @return
     */
    private RBTreeNode<T> getRoot() {
        return root.getLeft();
    }

    /**
     * number of tree number
     *
     * @return
     */
    public long getSize() {
        return size.get();
    }

    /**
     * 右旋转
     * 代码来自 美团技术博客  <a href="https://zhuanlan.zhihu.com/p/24367771"></a>
     *
     * @param node 待旋转树的根节点 (参考维基百科<a href="https://zh.wikipedia.org/wiki/%E6%A0%91%E6%97%8B%E8%BD%AC">树旋转</a>)
     */
    private void rotateRight(RBTreeNode<T> node) {
        //根节点的左节点
        RBTreeNode<T> left = node.getLeft();

        RBTreeNode<T> parent = node.getParent();
        //根的左节点的右节点变成根的左节点
        node.setLeft(left.getRight());
        setParent(left.getRight(), node);
        //根本身在旋转后会变成新的根的右子节点
        left.setRight(node);
        setParent(node, left);
        if (parent == null) {
            root.setLeft(left);
            setParent(left, null);
        } else {
            if (parent.getLeft() == node) {
                parent.setLeft(left);
            } else {
                parent.setRight(left);
            }
            setParent(left, parent);
        }
    }

    /**
     * 左旋转
     * 代码来自 美团技术博客  <a href="https://zhuanlan.zhihu.com/p/24367771"></a>
     *
     * @param node 待旋转树的根节点 (参考维基百科<a href="https://zh.wikipedia.org/wiki/%E6%A0%91%E6%97%8B%E8%BD%AC">树旋转</a>)
     */
    private void rotateLeft(RBTreeNode<T> node) {
        //根节点的右节点
        RBTreeNode<T> right = node.getRight();
        RBTreeNode<T> parent = node.getParent();
        //根的右节点的左节点赋值给根的右节点
        node.setRight(right.getLeft());
        setParent(right.getLeft(), node);
        //根节点赋值给右节点的左节点
        right.setLeft(node);
        //证明是树的根节点
        if (parent == null) {
            //右节点上升为新的根节点
            root.setLeft(right);//这一行???
            setParent(right, null);
        } else {
            if (parent.getLeft() == node) {
                parent.setLeft(right);
            } else {
                parent.setRight(right);
            }
            setParent(right, parent);
        }
    }

    private void setParent(RBTreeNode<T> node, RBTreeNode<T> parent) {
        if (node != null) {
            node.setParent(parent);
            if (parent == root) {
                node.setParent(null);
            }
        }
    }

}