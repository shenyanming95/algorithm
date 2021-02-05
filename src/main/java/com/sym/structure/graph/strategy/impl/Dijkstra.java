package com.sym.structure.graph.strategy.impl;

import com.sym.structure.graph.IGraph;
import com.sym.structure.graph.strategy.IShortestPathStrategy;

import java.util.List;

/**
 * Dijkstra算法
 *
 * @author shenyanming
 * Created on 2021/2/5 11:15
 */
public class Dijkstra<V, E> implements IShortestPathStrategy<V, E> {

    @Override
    public List<IGraph.EdgeInfo<V, E>> shortestPath(IGraph<V, E> graph, V v) {
        return null;
    }
}
