package com.sym.structure.graph.strategy;

import com.sym.structure.graph.IGraph;

import java.util.List;

/**
 * 图的最小生成树策略
 *
 * @author shenyanming
 * Created on 2021/2/5 10:14
 */
public interface IMstStrategy<V, E> {

    /**
     * 计算图的最小生成树
     *
     * @return 最小生成树的边
     */
    List<IGraph.EdgeInfo<V, E>> mst(IGraph<V, E> graph);
}
