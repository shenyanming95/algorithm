package com.sym.structure.unionfind.optimization;

import com.sym.structure.unionfind.AbstractUnionFind;

/**
 * 并查集, 基于Rank的优化, 树高度小的集合(矮的树)合并到树高度大的集合(高的树)
 *
 * @author shenyanming
 * @date 2020/10/20 22:14.
 */

public class UnionFindBaseRank extends AbstractUnionFind {

    private int[] ranks;

    protected UnionFindBaseRank(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            // 初始化时, 每个集合的高度都为1
            ranks[i] = 1;
        }
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
        int r1 = find(v1);
        int r2 = find(v2);
        if (r1 == r2) {
            return;
        }
        // 让高度小的树嫁接到高度大的树
        if (ranks[r1] < ranks[r2]) {
            // r1集合的树高度小于r2集合, r1并到r2, 并且r1和r2的树高度都不会改变
            array[r1] = r2;
        } else if (ranks[r1] > ranks[r2]) {
            // r1集合的树高度大于r2集合, r1并到r1, 并且r1和r2的树高度都不会改变
            array[r2] = r1;
        } else {
            // r1和r2如果高度一样, 那谁并谁都一样, 被合并的集合, 其树高度肯定+1
            array[r1] = r2;
            ranks[r2] += 1;
        }
    }
}
