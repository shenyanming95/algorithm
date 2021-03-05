package com.sym.structure.graph.impl.list;

import com.sym.structure.graph.strategy.IMstStrategy;
import com.sym.structure.graph.strategy.IShortestPathStrategy;

import java.util.Comparator;

/**
 * 无向图, 基于有向图{@link LinkedListGraph}实现.
 * 实现原理很简单, 即每次有向图添加边的时候, 都相互添加, 从而形成双向.
 *
 * @author shenyanming
 * Created on 2021/2/18 22:34.
 */

public class UndirectedListGraph<V, E> extends LinkedListGraph<V, E> {

    public UndirectedListGraph() {
        this(new Prim<>(), new Dijkstra<>(), null);
    }

    public UndirectedListGraph(IMstStrategy<V, E> mst, IShortestPathStrategy<V, E> sp) {
        this(mst, sp, null);
    }

    public UndirectedListGraph(IMstStrategy<V, E> mst, IShortestPathStrategy<V, E> sp, Comparator<E> comparator) {
        super(mst, sp, comparator);
    }

    @Override
    public boolean addEdge(V from, V to) {
        return super.addEdge(from, to) | super.addEdge(to, from);
    }

    @Override
    public boolean addEdge(V from, V to, E weight) {
        return super.addEdge(from, to, weight) | super.addEdge(to, from, weight);
    }

    @Override
    public boolean removeEdge(V from, V to) {
        return super.removeEdge(from, to) | super.removeEdge(to, from);
    }
}
