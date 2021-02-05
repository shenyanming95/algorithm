package com.sym.structure.graph.strategy;

import com.sym.structure.graph.IGraph;

import java.util.List;

/**
 * 图的最短路径策略
 *
 * @author shenyanming
 * Created on 2021/2/5 10:24
 */
public interface IShortestPathStrategy<V, E> {

    /**
     * 单源最短路径
     * @param graph 指定图
     * @param v 指定顶点
     * @return 该节点到其它顶点的最短路径
     */
    List<IGraph.EdgeInfo<V, E>> shortestPath(IGraph<V, E> graph, V v);
}
