package com.sym.structure.unionfind.level1;

import com.sym.structure.unionfind.impl.QuickUnion;
import com.sym.structure.unionfind.level2.UnionFindWithPathCompression;
import com.sym.structure.unionfind.level2.UnionFindWithPathHalving;
import com.sym.structure.unionfind.level2.UnionFindWithPathSplitting;

/**
 * 并查集一级优化, 基于rank的优化.
 * 让树高度小的集合(矮的树)合并到树高度大的集合(高的树), 这样可以有效防止
 * 链表现象的发生, 并查集的二级优化大部分也是基于rank展开的.
 *
 * @author shenyanming
 * @date 2020/10/20 22:14.
 * @see UnionFindWithPathCompression
 * @see UnionFindWithPathSplitting
 * @see UnionFindWithPathHalving
 */
public class UnionFindBaseRank extends QuickUnion {

    private int[] ranks;

    public UnionFindBaseRank(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            // 初始化时, 每个集合的高度都为1
            ranks[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        // 找出两个集合的根节点
        int r1 = find(v1);
        int r2 = find(v2);
        if (r1 == r2) {
            return;
        }
        if (ranks[r1] < ranks[r2]) {
            // r1集合的树高度小于r2集合, r1并到r2, 并且r1和r2的树高度都不会改变
            array[r1] = r2;
        } else if (ranks[r1] > ranks[r2]) {
            // r1集合的树高度大于r2集合, r1并到r1, 并且r1和r2的树高度都不会改变
            array[r2] = r1;
        } else {
            // r1和r2如果高度一样, 那谁并谁都一样, 被合并的集合其树高度肯定+1
            array[r1] = r2;
            ranks[r2] += 1;
        }
    }
}
