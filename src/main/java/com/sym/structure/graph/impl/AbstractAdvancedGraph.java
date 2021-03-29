package com.sym.structure.graph.impl;

import com.sym.structure.graph.IAdvancedGraph;
import com.sym.structure.graph.strategy.IMstStrategy;
import com.sym.structure.graph.strategy.IShortestPathStrategy;

import java.util.List;
import java.util.Objects;

/**
 * 图高级运用的抽象父类, 实现：
 * 图的遍历方式、图的最小生成树、图的最短路径
 *
 * @author shenyanming
 * Created on 2021/2/5 10:35
 */
public abstract class AbstractAdvancedGraph<V, E> extends AbstractBaseGraph<V, E>
        implements IAdvancedGraph<V, E> {

    /**
     * 最小生成树实现策略
     */
    private IMstStrategy<V, E> mstStrategy;

    /**
     * 最短路径实现策略
     */
    private IShortestPathStrategy<V, E> shortestPathStrategy;

    /**
     * 边权值处理器
     */
    private IWeightHandler<E> weightHandler;

    /**
     * 构造方法
     *
     * @param mstStrategy          最小生成树实现策略
     * @param shortestPathStrategy 最短路径实现策略
     */
    protected AbstractAdvancedGraph(IMstStrategy<V, E> mstStrategy, IShortestPathStrategy<V, E> shortestPathStrategy,
                                    IWeightHandler<E> weightHandler) {
        this.mstStrategy = Objects.requireNonNull(mstStrategy);
        this.shortestPathStrategy = Objects.requireNonNull(shortestPathStrategy);
        this.weightHandler = Objects.requireNonNull(weightHandler);
    }

    @Override
    public List<EdgeInfo<V, E>> mst() {
        return mstStrategy.mst(this);
    }

    @Override
    public List<IShortestPathStrategy.PathInfo<V, E>> shortestPath(V vertex) {
        return shortestPathStrategy.shortestPath(this, vertex);
    }

    /**
     * 边权值比较
     */
    protected int compareWithEdge(E e1, E e2) {
        return Objects.isNull(e1) || Objects.isNull(e2) ? 0 : weightHandler.compare(e1, e2);
    }

    /**
     * 边权值计算
     */
    protected E addWithEdge(E e1, E e2) {
        return weightHandler.add(e1, e2);
    }

    /**
     * 边权值初始值
     */
    protected E initEdgeWeight(){
        return weightHandler.initialValue();
    }
}
