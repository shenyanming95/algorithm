package com.sym.structure.heap;

/**
 * 如果一棵树, 它的任意节点总是 ≥ 或者 ≤ 它的子节点, 那么称这种数据结构为：堆（Heap）
 * 任意节点的值都 ≥ 它的子节点, 称为最大堆;
 * 任意节点的值都 ≤ 它的子节点, 称为最小堆.
 * 堆跟二叉搜索树一样, 它也要求堆中的元素具有可比较性.
 *
 * @author shenyanming
 * @date 2020/7/26 21:54.
 */
public interface IHeap<E> {

    /**
     * 元素的数量
     *
     * @return 堆中存在的元素数量
     */
    int size();

    /**
     * 是否为空
     *
     * @return true-空堆, 反之为false
     */
    boolean isEmpty();

    /**
     * 清空堆
     */
    void clear();

    /**
     * 添加元素
     *
     * @param element 新元素
     */
    void add(E element);

    /**
     * 获取堆顶元素
     *
     * @return 堆顶元素
     */
    E get();

    /**
     * 删除堆顶元素
     *
     * @return 顶对元素
     */
    E remove();

    /**
     * 删除堆顶元素的同时, 插入新元素
     *
     * @param element 新元素
     * @return 堆顶元素
     */
    E replace(E element);
}
