package com.sym.structure.unionfind.optimization;

import com.sym.structure.unionfind.AbstractUnionFind;

/**
 * 并查集, 基于Size的优化, 元素数量少的集合合并到元素数量多的集合
 *
 * @author shenyanming
 * @date 2020/10/19 22:50.
 */

public class UnionFindBaseSize extends AbstractUnionFind {

    private int[] sizes;

    protected UnionFindBaseSize(int capacity) {
        super(capacity);
        sizes = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            // 初始化时, 每个元素所在的集合, 其数量肯定为1,
            // 因为只有他自己
            sizes[i] = 1;
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
            // r1和r2集合size一样, 或则r1集合数量大于r2
            array[r2] = r1;
            sizes[r1] += sizes[r2];
        }
    }
}
