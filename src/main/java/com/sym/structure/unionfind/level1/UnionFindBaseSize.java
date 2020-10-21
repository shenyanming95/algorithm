package com.sym.structure.unionfind.level1;

import com.sym.structure.unionfind.impl.QuickUnion;

/**
 * 并查集一级优化, 基于size的优化.
 * 让元素数量少的集合合并到元素数量多的集合, 但是基于集合数量的优化, 并不能解决树的高度问题,在find操作
 * 时还是存在链表现象. 因此, 大部分情况下, 还是选择使用基于Rank的优化措施{@link UnionFindBaseRank}
 *
 * @author shenyanming
 * @date 2020/10/19 22:50.
 */
public class UnionFindBaseSize extends QuickUnion {

    private int[] sizes;

    public UnionFindBaseSize(int capacity) {
        super(capacity);
        sizes = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            // 初始化时, 每个元素所在的集合, 其数量肯定为1, 因为只有他自己
            sizes[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        // 找出两个集合的根节点索引
        int r1 = find(v1);
        int r2 = find(v2);
        if (r1 == r2) {
            return;
        }
        // 根据size大小来判断, size小的合并到size大的
        if (sizes[r1] < sizes[r2]) {
            // r1集合数量小, 所以它合并到r2集合
            array[r1] = r2;
            sizes[r2] += sizes[r1];
        } else {
            // r1和r2集合size一样, 或r1集合数量大于r2
            array[r2] = r1;
            sizes[r1] += sizes[r2];
        }
    }
}
