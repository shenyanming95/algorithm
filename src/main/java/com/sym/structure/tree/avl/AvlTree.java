package com.sym.structure.tree.avl;

import com.sym.structure.tree.bst.BinarySearchTree;
import com.sym.structure.tree.traversal.Visitor;

/**
 * AVL树的实现, 理论上可以继承二叉搜索树{@link BinarySearchTree}实现,
 * 但是我们在这里重新实现一版, 就不去继承二叉搜索树实现了!
 *
 * @author shenyanming
 * @date 2020/6/16 22:25.
 */
public class AvlTree<E> implements IAvlTree<E> {

    /**
     * AVL树的节点实体
     *
     * @param <E> 类型
     */
    private static class AvlNode<E> {
        E element;
        AvlNode<E> left;
        AvlNode<E> right;
        AvlNode<E> parent;

        public AvlNode(E e, AvlNode<E> p) {
            this.element = e;
            this.parent = p;
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

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(element.toString());
            if (parent == null) {
                sb.append("(null)");
            } else {
                sb.append("(").append(parent.element.toString()).append(")");
            }
            return sb.toString();
        }
    }

    /**
     * 根节点
     */
    private AvlNode<E> root;

    @Override
    public int height() {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

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
    public void preorder(Visitor<E> visitor) {

    }

    @Override
    public void inorder(Visitor<E> visitor) {

    }

    @Override
    public void postorder(Visitor<E> visitor) {

    }

    @Override
    public void levelorder(Visitor<E> visitor) {

    }

    /* 借助外部工具类, 打印二叉树的结构图 - start*/
    @Override
    public Object root() {
        return root;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object left(Object node) {
        return ((AvlNode<E>) node).left;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object right(Object node) {
        return ((AvlNode<E>) node).right;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object string(Object node) {
        return ((AvlNode<E>) node).toString();
    }
    /* 借助外部工具类, 打印二叉树的结构图 - end*/
}
