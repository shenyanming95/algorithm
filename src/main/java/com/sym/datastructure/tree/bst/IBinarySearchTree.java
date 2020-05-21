package com.sym.datastructure.tree.bst;

/**
 * 二叉搜索树, Binary Search Tree, 简称BST. 是二叉树的一种, 也称为二叉查找树、二叉排序树.
 * 二叉搜索树的接口定义
 *
 * @author shenyanming
 * @date 2020/5/21 21:19.
 */

public interface IBinarySearchTree<E> {

    /**
     * 元素的数量
     *
     * @return 数量
     */
    int size();

    /**
     * 是否为空
     *
     * @return true-空树
     */
    boolean isEmpty();

    /**
     * 清空所有元素
     */
    void clear();

    /**
     * 新增元素
     *
     * @param e 元素
     */
    void add(E e);

    /**
     * 删除元素
     *
     * @param e 待删除元素
     */
    void remove(E e);

    /**
     * 查询元素
     *
     * @param e 元素
     * @return true-存在
     */
    boolean contains(E e);
}
