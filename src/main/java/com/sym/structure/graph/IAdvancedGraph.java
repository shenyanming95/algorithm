package com.sym.structure.graph;

import java.util.List;
import java.util.function.Consumer;

/**
 * 图接口, 在图基本操作基础上增加：
 * 图的遍历方式, 图的最小生成树, 图的最短路径
 *
 * @author shenyanming
 * Created on 2021/2/5 10:41
 */
public interface IAdvancedGraph<V, E> extends IGraph<V, E> {

    /**
     * 广度优先搜索, Breadth First Search.
     * 先查找离起始顶点最近的, 然后是次近的, 依次往外搜索;
     *
     * @param v 指定顶点
     * @param consumer 遍历时的处理逻辑
     */
    void bfs(V v, Consumer<VertexInfo<V>> consumer);

    /**
     * 深度优先搜索, Depth First Search.
     * 沿着树的深度遍历树的节点, 尽可能深得搜索树的分支,
     * 当节点v的所在边都己被探寻过, 搜索将回溯到发现节点v的那条边的起始节点.
     *
     * @param v 指定顶点
     * @param consumer 遍历时的处理逻辑
     */
    void dfs(V v, Consumer<VertexInfo<V>> consumer);

    /**
     * 计算图的最小生成树
     *
     * @return 最小生成树的边集合ø
     */
    List<EdgeInfo<V, E>> mst();

    /**
     * 计算图的最短路径
     *
     * @param vertex 指定顶点
     * @return 该顶点到不同顶点的最短路径
     */
    List<EdgeInfo<V, E>> shortestPath(V vertex);
}
