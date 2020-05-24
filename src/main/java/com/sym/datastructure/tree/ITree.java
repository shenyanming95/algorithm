package com.sym.datastructure.tree;

import com.sym.util.printer.BinaryTreeInfo;

/**
 * Binary Tree, 二叉树
 *
 * @author ym.shen
 * Created on 2020/5/8 14:48
 */
public interface ITree<E> extends BinaryTreeInfo {

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

    /**
     * 树的遍历方式
     *
     * @return {@link TreeTraversal}
     */
    TreeTraversal traversal();

    /**
     * 树的遍历接口, 树这种数据结构, 有4种遍历方式：
     * 1.前序遍历：先访问根节点, 再前序遍历左子树, 后前序遍历右子树;
     * 2.中序遍历：先中序遍历左子树, 再访问根节点, 后中序遍历右子树;
     * 3.后序遍历：先后序遍历左子树, 再后序遍历右子树, 后访问根节点;
     * 4.层序遍历：按照从上往下, 从左往右的顺序遍历
     */
    interface TreeTraversal {
        /**
         * 返回树的根节点
         */
        TreeTraversal root();

        /**
         * 返回树的左节点
         */
        TreeTraversal left();

        /**
         * 返回树的右节点
         */
        TreeTraversal right();
    }

}
