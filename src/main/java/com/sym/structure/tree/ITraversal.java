package com.sym.structure.tree;

/**
 * 树的遍历接口, 树这种数据结构, 有4种遍历方式：
 * 1.前序遍历：先访问根节点, 再前序遍历左子树, 后前序遍历右子树;
 * 2.中序遍历：先中序遍历左子树, 再访问根节点, 后中序遍历右子树;
 * 3.后序遍历：先后序遍历左子树, 再后序遍历右子树, 后访问根节点;
 * 4.层序遍历：按照从上往下, 从左往右的顺序遍历
 *
 * @author shenyanming
 * @date 2020/6/16 21:37.
 */
public interface ITraversal<E> {
    /**
     * 前序遍历
     *
     * @param visitor 访问者
     */
    void preOrder(IVisitor<E> visitor);

    /**
     * 中序遍历
     *
     * @param visitor 访问者
     */
    void inOrder(IVisitor<E> visitor);

    /**
     * 后序遍历
     *
     * @param visitor 访问者
     */
    void postOrder(IVisitor<E> visitor);

    /**
     * 层序遍历
     *
     * @param visitor 访问者
     */
    void levelOrder(IVisitor<E> visitor);


    /**
     * 通过【访问者模式】设计树{@link com.sym.structure.tree.ITree}的遍历方式
     *
     * @author shenyanming
     * @date 2020/6/16 21:34.
     */
    @FunctionalInterface
    interface IVisitor<E> {
        /**
         * 访问一个元素
         *
         * @param element 具体元素
         */
        void visit(E element);
    }
}
