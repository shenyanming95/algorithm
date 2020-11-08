package com.sym.structure.graph.base;

import com.sym.structure.graph.IGraph;

/**
 * 邻接表实现的图
 *
 * @author shenyanming
 * @date 2020/11/8 15:27.
 */

public class LinkedListGraph<T, V> implements IGraph<T, V> {
    @Override
    public boolean addVertex(T t) {
        return false;
    }

    @Override
    public boolean addEdge(T from, T to) {
        return false;
    }

    @Override
    public boolean addEdge(T from, T to, V weight) {
        return false;
    }

    @Override
    public boolean removeVertex(T t) {
        return false;
    }

    @Override
    public boolean removeEdge(T from, T to) {
        return false;
    }

    @Override
    public int verticesSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }
}
