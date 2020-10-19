package com.sym.structure.unionfind.ordinary;

import com.sym.structure.unionfind.AbstractUnionFind;

/**
 * quick find的并查集, 它的union操作, 比如说集合A和集合B, 是将
 * 集合B中的每个元素都直接并接到集合A的根节点上, 这样可以保证在执行
 * find操作时效率很高, 但就是union操作需要遍历整个数组, 效率较慢.
 * 所以一般也不会采用这个实现方式, 转而使用
 *
 * @author shenyanming
 * @date 2020/10/19 21:43.
 * @see QuickUnion
 */

public class QuickFind extends AbstractUnionFind {

    public QuickFind(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        check(v);
        return array[v];
    }

    @Override
    public void union(int unionTo, int unionFrom) {
        // 找出目标集合的根节点
        int to = find(unionTo);
        int from = find(unionFrom);
        // 遍历所有元素, 将所有属于 unionFrom 集合的元素, 都嫁接到 unionTo 集合中
        for (int i = 0; i < array.length; i++) {
            if (array[i] == from) {
                array[i] = to;
            }
        }
    }
}
