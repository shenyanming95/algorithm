package com.sym.structure.tree;

/**
 * 树接口
 *
 * @author ym.shen
 * Created on 2020/5/8 14:48
 */
public interface ITree<E>{

    /**
     * 节点的度
     */
    Integer DEGREE_ZERO = 0;
    Integer DEGREE_ONE = 1;
    Integer DEGREE_TWO = 2;

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
