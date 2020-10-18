package com.sym.structure.unionfind;

/**
 * 并查集
 *
 * @author shenyanming
 * @date 2020/10/18 11:46.
 */

public interface IUnionFind {

    /**
     * 查找元素v所在的集合
     *
     * @param v 待确定集合的元素
     * @return 集合根节点
     */
    int find(int v);

    /**
     * 合并v1和v2所在的两个集合
     *
     * @param v1 v1所在集合
     * @param v2 v2所在集合
     */
    void union(int v1, int v2);

    /**
     * 判断v1和v2是否同属于一个集合
     *
     * @param v1 元素v1
     * @param v2 元素v2
     * @return true-处于同一个集合
     */
    boolean isSame(int v1, int v2);
}
