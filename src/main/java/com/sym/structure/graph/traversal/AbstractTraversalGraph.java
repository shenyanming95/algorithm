package com.sym.structure.graph.traversal;

import com.sym.structure.graph.IGraph;

/**
 * 图的遍历方式：
 * - 广度优先搜索遍历: 先查找离起始顶点最近的, 然后是次近的, 依次往外搜索;
 * - 深度优先搜索遍历: 沿着树的深度遍历树的节点, 尽可能深得搜索树的分支, 当节点v的所在边都己被探寻过, 搜索将回溯到发现节点v的那条边的起始节点.
 *
 * @author shenyanming
 * @date 2020/11/8 15:12.
 */
public abstract class AbstractTraversalGraph<T, V> implements IGraph<T, V> {

    /**
     * 广度优先搜索, Breadth First Search
     */
    public abstract void BFS();

    /**
     * 深度优先搜索, Depth First Search
     */
    public abstract void DFS();
}
