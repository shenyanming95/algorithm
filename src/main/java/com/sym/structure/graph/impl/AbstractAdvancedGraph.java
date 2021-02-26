package com.sym.structure.graph.impl;

import com.sym.structure.graph.IAdvancedGraph;
import com.sym.structure.graph.strategy.IMstStrategy;
import com.sym.structure.graph.strategy.IShortestPathStrategy;

import java.util.Comparator;
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
     * 边权重的比较器, 用于求最小生成树和最短路径
     */
    protected Comparator<E> edgeWeightComparator;

    /**
     * 构造方法
     *
     * @param mstStrategy          最小生成树实现策略
     * @param shortestPathStrategy 最短路径实现策略
     */
    protected AbstractAdvancedGraph(IMstStrategy<V, E> mstStrategy, IShortestPathStrategy<V, E> shortestPathStrategy,
                                    Comparator<E> comparator) {
        this.mstStrategy = Objects.requireNonNull(mstStrategy);
        this.shortestPathStrategy = Objects.requireNonNull(shortestPathStrategy);
        this.edgeWeightComparator = comparator;
    }

    @Override
    public List<EdgeInfo<V, E>> mst() {
        return mstStrategy.mst(this);
    }

    @Override
    public List<EdgeInfo<V, E>> shortestPath(V vertex) {
        return shortestPathStrategy.shortestPath(this, vertex);
    }

    /**
     * 边权重的比较
     */
    @SuppressWarnings("unchecked")
    protected int edgeCompare(E e1, E e2) {
        if (Objects.isNull(e1) || Objects.isNull(e2)) {
            return 0;
        }
        return Objects.nonNull(edgeWeightComparator) ? edgeWeightComparator.compare(e1, e2)
                : ((Comparable<E>) e1).compareTo(e2);

    }
}
