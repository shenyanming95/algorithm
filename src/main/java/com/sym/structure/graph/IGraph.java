package com.sym.structure.graph;

/**
 * 图的基本接口
 *
 * @author shenyanming
 * @date 2020/11/1 21:14.
 */

public interface IGraph<T, V> {

    /**
     * 添加顶点
     *
     * @param t 顶点值
     * @return true-添加成功
     */
    boolean addVertex(T t);

    /**
     * 添加没有权值的边
     *
     * @param from 边的起点
     * @param to   边的终点
     * @return true-添加成功
     */
    boolean addEdge(T from, T to);

    /**
     * 添加带有权值的边
     *
     * @param from   边的起点
     * @param to     边的终点
     * @param weight 权值
     * @return true-添加成功
     */
    boolean addEdge(T from, T to, V weight);

    /**
     * 删除顶点
     *
     * @param t 顶点值
     * @return true-删除成功
     */
    boolean removeVertex(T t);

    /**
     * 删除边
     *
     * @param from 边的起点
     * @param to   边的终点
     * @return true-删除成功
     */
    boolean removeEdge(T from, T to);

    /**
     * 节点数
     *
     * @return 总节点数据量
     */
    int verticesSize();

    /**
     * 边的数量
     *
     * @return 总的边数量
     */
    int edgeSize();
}
