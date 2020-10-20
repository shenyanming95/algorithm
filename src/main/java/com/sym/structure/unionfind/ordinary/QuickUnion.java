package com.sym.structure.unionfind.ordinary;

import com.sym.structure.unionfind.AbstractUnionFind;

/**
 * quick union的并查集, 它的union操作, 比如说集合A和集合B, 是将
 * 集合B中的每个元素都直接并到集合A中, 也不需要去寻找集合A的根节点,
 * 这样可以保证在执行union操作时效率很高, 但是find操作时就需要一层
 * 一层向上寻找至根节点. 一般会采用这个方式的并查集.
 *
 * @author shenyanming
 * @date 2020/10/19 21:52.
 */

public class QuickUnion extends AbstractUnionFind {

    public QuickUnion(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        check(v);
        int result = v;
        while (array[result] != result) {
            result = array[result];
        }
        return result;
    }

    @Override
    public void union(int v1, int v2) {
        // 找出两个集合的根节点
        int to = find(v1);
        int from = find(v2);
        if (to == from) {
            return;
        }
        // 直接将from嫁接到to, 默认v2合并到v1
        array[from] = to;
    }
}
