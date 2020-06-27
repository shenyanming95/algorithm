package com.sym.structure.tree.rbt;

import com.sym.structure.tree.traversal.IVisitor;
import java.util.Comparator;
import java.util.Objects;

/**
 * 红黑树的实现, 它其实可以继承{@link com.sym.structure.tree.bst.BinarySearchTree}做延伸,
 * 这里为了实现一套完整的逻辑, 单独实现.红黑树也是一颗二叉搜索树, 在二叉搜索树的基础上加了5个限制：{@link IRedBlackTree},
 * 其中最特色的就是引入了节点颜色的概念.
 *
 * @author shenyanming
 * @date 2020/6/27 10:31.
 */
public class RedBlackTree<E> implements IRedBlackTree<E> {

    /**
     * 红黑树节点
     *
     * @param <E> 存储的元素类型
     */
    private static class RbtNode<E> {
        E element;
        RbtNode<E> parent;
        RbtNode<E> left;
        RbtNode<E> right;
        int color;

        RbtNode(E e, RbtNode<E> parent) {
            this(e, parent, BLACK);
        }

        RbtNode(E e, RbtNode<E> parent, int color) {
            this.element = e;
            this.parent = parent;
            this.color = color;
        }

        /**
         * 获取节点的度
         *
         * @return 0 or 1 or 2
         */
        public int degree() {
            int result = 0;
            if (left != null) {
                result++;
            }
            if (right != null) {
                result++;
            }
            return result;
        }

        /**
         * 判断当前节点是否为黑色
         *
         * @param node 节点
         * @return true-黑色
         */
        public boolean isBlack(RbtNode<E> node) {
            if (Objects.isNull(node)) {
                // 空节点默认都为黑色节点
                return true;
            }
            return node.color == BLACK;
        }

        /**
         * 判断当前节点是否为红色
         *
         * @param node 节点
         * @return true-红色
         */
        public boolean isRed(RbtNode<E> node) {
            if (Objects.isNull(node)) {
                // 空节点默认都为黑色节点
                return false;
            }
            return node.color == RED;
        }

        /**
         * 将当前节点标志为黑色
         *
         * @return 被标志的节点
         */
        public RbtNode<E> markBlack() {
            this.color = BLACK;
            return this;
        }

        /**
         * 将当前节点标志位红色
         *
         * @return 被标志的节点
         */
        public RbtNode<E> markRed() {
            this.color = RED;
            return this;
        }

        /**
         * 判断当前节点是否为其父节点的左子节点
         *
         * @return true-是左子节点
         */
        public boolean isParentLeftChild() {
            return parent != null && parent.left == this;
        }

        /**
         * 判断当前节点是否为其父节点的右子节点
         *
         * @return true-是右子节点
         */
        public boolean isParentRightChild() {
            return parent != null && parent.right == this;
        }

        /**
         * 获取当前节点的兄弟节点
         *
         * @return 可能为Null
         */
        public RbtNode<E> sibling() {
            if (isParentLeftChild()) {
                // 说明当前节点是否父节点的左子节点, 那么兄弟节点就是父节点的右子节点
                return parent.right;
            }
            if (isParentRightChild()) {
                // 说明当前节点是否父节点的右子节点, 那么兄弟节点就是父节点的左子节点
                return parent.left;
            }
            // 没有父节点, 也即根节点啦
            return null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // 节点自身的信息
            sb.append(element.toString())
                    .append("[")
                    .append(color == BLACK ? "black" : "red")
                    .append("]");
            // 父节点的信息
            if (parent == null) {
                sb.append("(null)");
            } else {
                sb.append("(").append(parent.element.toString()).append(")");
            }
            return sb.toString();
        }
    }

    /**
     * 这里统一红色节点用0表示, 黑色结点用1表示
     */
    private final static int RED = 0;
    private final static int BLACK = 1;

    /**
     * 根节点
     */
    private RbtNode<E> root;

    /**
     * 红黑树的元素数量
     */
    private int size;

    /**
     * 用于元素比较大小
     */
    private Comparator<E> comparator;

    /**
     * 无参构造器, 要求元素E自己必须实现{@link Comparable}
     */
    public RedBlackTree(){

    }

    /**
     * 指定比较器, 元素E可以不用实现{@link Comparable}, 通过指定比较器{@link Comparator}
     * @param comparator 比较器
     */
    public RedBlackTree(Comparator<E> comparator){
        this.comparator = comparator;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return null == root;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public void add(E e) {

    }

    @Override
    public void remove(E e) {

    }

    @Override
    public boolean contains(E e) {
        return false;
    }

    @Override
    public void preorder(IVisitor<E> visitor) {

    }

    @Override
    public void inorder(IVisitor<E> visitor) {

    }

    @Override
    public void postorder(IVisitor<E> visitor) {

    }

    @Override
    public void levelorder(IVisitor<E> visitor) {

    }

    /* 借助外部工具类, 打印二叉树的结构图 - start*/
    @Override
    public Object root() {
        return root;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object left(Object node) {
        return ((RbtNode<E>) node).left;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object right(Object node) {
        return ((RbtNode<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return node.toString();
    }
    /* 借助外部工具类, 打印二叉树的结构图 - end*/
}
