package com.sym.structure.unionfind;

/**
 * 并查集。
 * 并查集, 其核心就是通过一个元素来定义一个集合, 然后借助这个元素,
 * 可以判断其它元素是否与集合有关, 这就是find; 还可以借助这个元素,
 * 合并集合, 这就是union.
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
     * @param v1 元素v1
     * @param v2 元素v2
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
