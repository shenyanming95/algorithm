package com.sym.structure.graph.strategy.impl;

import com.sym.structure.graph.IGraph;
import com.sym.structure.graph.strategy.IMstStrategy;

import java.util.List;

/**
 * Prim算法
 *
 * @author shenyanming
 * Created on 2021/2/5 11:05
 */
public class Prim<V, E> implements IMstStrategy<V, E> {

    @Override
    public List<IGraph.EdgeInfo<V, E>> mst(IGraph<V, E> graph) {
        return null;
    }

}
