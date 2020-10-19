package com.sym.structure.unionfind;

/**
 * 并查集抽象父类
 *
 * @author shenyanming
 * @date 2020/10/19 21:06.
 */
public abstract class AbstractUnionFind implements IUnionFind {

    protected int[] array;

    protected AbstractUnionFind(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be more than 0");
        }
        array = new int[capacity];
        // 初始化时, 每个元素都是一个集合, 且自身就是集合的根节点
        for (int i = 0; i < capacity; i++) {
            array[i] = i;
        }
    }

    @Override
    public boolean isSame(int v1, int v2) {
        check(v1, v2);
        return find(v1) == find(v2);
    }

    protected void check(int... params) {
        for (int param : params) {
            if (param >= array.length || param < 0) {
                throw new IllegalArgumentException("invalid param with value [" + param + "]");
            }
        }
    }
}
