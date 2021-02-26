package com.sym.structure.graph.impl.list;

import com.sym.structure.graph.IGraph;
import com.sym.structure.graph.impl.AbstractAdvancedGraph;
import com.sym.structure.graph.strategy.IMstStrategy;
import com.sym.structure.graph.strategy.IShortestPathStrategy;
import com.sym.structure.heap.IHeap;
import com.sym.structure.heap.impl.BinaryHeap;
import com.sym.structure.queue.IQueue;
import com.sym.structure.queue.linked.LinkedQueue;
import com.sym.structure.stack.IStack;
import com.sym.structure.stack.linked.LinkedStack;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 邻接表实现的图(有向图)
 *
 * @author shenyanming
 * @date 2020/11/8 15:27.
 */
public class LinkedListGraph<V, E> extends AbstractAdvancedGraph<V, E> {

    public LinkedListGraph() {
        this(new Prim<>(), new Dijkstra<>(), null);
    }

    public LinkedListGraph(IMstStrategy<V, E> mst, IShortestPathStrategy<V, E> sp, Comparator<E> comparator) {
        super(mst, sp, comparator);
    }

    /**
     * 图的顶点集, 不管是在创建边还是在创建顶点, 都需要确定当前顶点是否已经创建,
     * 因此需要用Map来维护顶点集.
     */
    private Map<V, Vertex<V, E>> vertices = newMap();

    /**
     * 图的边集, 用于统计边的数量, 同时也可以用来确定边是否已经创建.
     */
    private Set<Edge<V, E>> edges = newSet();

    @Override
    public boolean addVertex(V v) {
        if (vertices.containsKey(v)) {
            // 已经存在当前顶点, 直接返回false
            return false;
        }
        // 不存在当前顶点, 为其生成一个新顶点
        vertices.put(v, newVertex(v));
        return true;
    }

    @Override
    public boolean addEdge(V from, V to) {
        return this.addEdge(from, to, null);
    }

    @Override
    public boolean addEdge(V from, V to, E weight) {
        // 首先需要判断起点和终点是否已经存在, 若不存在则需要创建
        Vertex<V, E> fromVertex = vertices.computeIfAbsent(from, LinkedListGraph::newVertex);
        Vertex<V, E> toVertex = vertices.computeIfAbsent(to, LinkedListGraph::newVertex);
        // 通过 起点+终点 确定一条边, 所以每次都生成一条新边, 然后直接覆盖旧边
        Edge<V, E> newEdge = newEdge(fromVertex, toVertex, weight);
        // 如果删除成功, 说明边确实存在, 那么起点出度边集合和终点入度边集合都需要删除,
        // 因为JDK提供的HashSet#add()并不会直接覆盖旧值, 因此不能单单只调用add()方法
        if (edges.remove(newEdge)) {
            fromVertex.outEdges.remove(newEdge);
            toVertex.inEdges.remove(newEdge);
        }
        // 然后添加新的边
        edges.add(newEdge);
        fromVertex.outEdges.add(newEdge);
        toVertex.inEdges.add(newEdge);
        return true;
    }

    @Override
    public boolean removeVertex(V v) {
        Vertex<V, E> vertex = vertices.remove(v);
        if (Objects.isNull(vertex)) {
            return false;
        }
        // 删除该顶点的入度边
        vertex.inEdges.forEach(edge -> {
            edge.to.outEdges.remove(edge);
            edges.remove(edge);
        });
        // 删除该顶点的出度边
        vertex.outEdges.forEach(edge -> {
            edge.to.inEdges.remove(edge);
            edges.remove(edge);
        });
        return cleanVertex(vertex);
    }

    @Override
    public boolean removeEdge(V from, V to) {
        Vertex<V, E> fromVertex;
        Vertex<V, E> toVertex;
        if (Objects.isNull(fromVertex = vertices.get(from)) || Objects.isNull(toVertex = vertices.get(to))) {
            // 只要一个顶点为空, 这条边肯定不存在当前图中
            return false;
        }
        // 用于寻址以便删除边
        Edge<V, E> edge = newEdge(fromVertex, toVertex, null);
        // 仅当边存在的时候, 才会去删除起点出度边和终点入度边.
        if (edges.remove(edge)) {
            fromVertex.outEdges.remove(edge);
            toVertex.inEdges.remove(edge);
            return true;
        }
        // edges删除失败返回false, 说明这条边确实不存在于当前图中
        return false;
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public void bfs(V v, Consumer<VertexInfo<V>> consumer) {
        // 广度优先搜索, Breadth First Search, 需要从指定顶点开始
        Vertex<V, E> vertex = vertices.get(v);
        if (Objects.isNull(vertex) || Objects.isNull(consumer)) {
            return;
        }
        // 通过一个队列来存储下一个需要遍历的顶点
        IQueue<Vertex<V, E>> queue = new LinkedQueue<>();
        queue.offer(vertex);
        // 通过一个集合来存储已经遍历过的顶点
        Set<Vertex<V, E>> visitedSet = newSet();
        // 遍历的终止条件, 就是队列不为空
        while (!queue.isEmpty()) {
            Vertex<V, E> ve;
            // 仅当未被访问过顶点, 才需要访问, 并且将它出度边的对端顶点加入到集合中
            if (!visitedSet.contains((ve = queue.poll()))) {
                // 执行访问逻辑
                consumer.accept(VertexInfo.of(ve.value));
                // 将其加入到集合中表示已经访问过了
                visitedSet.add(ve);
                // 将该顶点出度边的对端顶点加入到队列中, 以便下次循环访问
                ve.outEdges.forEach(edge -> queue.offer(edge.to));
            }
        }
    }

    @Override
    public void dfs(V v, Consumer<VertexInfo<V>> consumer) {
        // 深度优先搜索, Deep First Search, 需要从指定顶点开始
        Vertex<V, E> vertex = vertices.get(v);
        if (Objects.isNull(vertex) || Objects.isNull(consumer)) {
            return;
        }
        // 用来存储已经访问到的顶点
        Set<Vertex<V, E>> visitedSet = newSet();
        // 递归实现
        dfs(vertex, visitedSet, consumer);
        // 非递归实现
        dfs2(vertex, visitedSet, consumer);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 先打印没有任何边的顶点
        vertices.values().stream().filter(v -> v.outEdges.isEmpty()).forEach(v -> sb.append(v).append("\n"));
        // 再打印所有边
        edges.forEach(edge -> sb.append(edge.toString()).append(";\n"));
        return sb.toString();
    }

    /**
     * 图的DFS搜索算法, 递归实现~
     *
     * @param vertex     起点
     * @param visitedSet 标识已经访问过的顶点
     * @param consumer   访问者模式
     */
    private void dfs(Vertex<V, E> vertex, Set<Vertex<V, E>> visitedSet, Consumer<VertexInfo<V>> consumer) {
        // 访问当前顶点, 并将其加入标识集合中
        consumer.accept(VertexInfo.of(vertex.value));
        visitedSet.add(vertex);
        // 获取它的出度边, 递归访问
        vertex.outEdges.forEach(edge -> {
            Vertex<V, E> toVertex = edge.to;
            if (!visitedSet.contains(toVertex)) {
                dfs(toVertex, visitedSet, consumer);
            }
        });
    }

    /**
     * 图的DFS搜索算法, 非递归实现~
     *
     * @param vertex     起点
     * @param visitedSet 标识已经访问过的顶点
     * @param consumer   访问者模式
     */
    private void dfs2(Vertex<V, E> vertex, Set<Vertex<V, E>> visitedSet, Consumer<VertexInfo<V>> consumer) {
        // 一切的递归都可以转化成非递归, 而其核心组件就是栈, BFS非递归实现同理也要用到栈
        IStack<Vertex<V, E>> stack = new LinkedStack<>();
        stack.push(vertex);
        // 循环的条件就是栈未空
        while (!stack.isEmpty()) {
            // 弹出栈顶元素访问, 然后将其加入到标识集合中
            Vertex<V, E> v = stack.pop();
            consumer.accept(VertexInfo.of(v.value));
            visitedSet.add(v);
            // 同时将该顶点出度边的对端顶点加入到栈中, 注意要过滤掉已经访问过的顶点,
            // 也就是保证每次从栈中弹出的顶点, 都是未访问过的.
            v.outEdges.stream().filter(edge -> !visitedSet.contains(edge.to)).forEach(edge -> stack.push(edge.to));
        }
    }

    /**
     * 初始化一个顶点
     *
     * @param v 顶点值
     * @return Vertex
     */
    private static <V, E> Vertex<V, E> newVertex(V v) {
        Vertex<V, E> vertex = new Vertex<>();
        vertex.value = v;
        return vertex;
    }

    /**
     * 初始化一条边
     *
     * @param from   起点
     * @param to     终点
     * @param weight 权重
     * @return Edge
     */
    private static <V, E> Edge<V, E> newEdge(Vertex<V, E> from, Vertex<V, E> to, E weight) {
        Edge<V, E> edge = new Edge<>();
        edge.from = from;
        edge.to = to;
        edge.weight = weight;
        return edge;
    }

    /**
     * 清空一个顶点
     *
     * @param vertex 顶点
     */
    private static <V, E> boolean cleanVertex(Vertex<V, E> vertex) {
        vertex.inEdges.clear();
        vertex.outEdges.clear();
        vertex.value = null;
        return true;
    }

    /**
     * 邻接表顶点
     *
     * @param <V> 顶点值
     * @param <E> 边权重
     */
    private static class Vertex<V, E> {
        /**
         * 存储用户的数据
         */
        V value;

        /**
         * 当前顶点的入度边集合
         */
        Set<Edge<V, E>> inEdges = newSet();

        /**
         * 当前顶点的出度边集合
         */
        Set<Edge<V, E>> outEdges = newSet();

        @Override
        public int hashCode() {
            return Objects.isNull(value) ? 0 : value.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            // 内部类不需要类型转换
            return Objects.equals(value, ((Vertex<?, ?>) o).value);
        }

        @Override
        public String toString() {
            return "[" + value + "]";
        }
    }

    /**
     * 邻接表的边
     *
     * @param <V> 顶点值
     * @param <E> 边权值
     */
    private static class Edge<V, E> {

        /**
         * 边的权重
         */
        E weight;

        /**
         * 当前边的起点
         */
        Vertex<V, E> from;

        /**
         * 当前边的终点
         */
        Vertex<V, E> to;

        @Override
        public int hashCode() {
            // 起点和终点确定一条边
            return Objects.hash(from, to);
        }

        @Override
        public boolean equals(Object obj) {
            // 内部类不需要类型转换
            Edge<?, ?> e = (Edge<?, ?>) obj;
            // 起点和终点确定一条边
            return Objects.equals(from, e.from) && Objects.equals(to, e.to);
        }

        @Override
        public String toString() {
            return from.toString() + " → " + to.toString() + ", w=" + weight;
        }
    }

    /**
     * Prim算法, 利用切分定理来求得最小生成树
     *
     * @param <V> 顶点值
     * @param <E> 边权值
     */
    private static class Prim<V, E> implements IMstStrategy<V, E> {

        @Override
        public List<EdgeInfo<V, E>> mst(IGraph<V, E> param) {
            // 最小生成树是针对连通图计算的, 连通图都是无向的(强连通图才是有向的)
            if (!(param instanceof UndirectedListGraph)) {
                return Collections.emptyList();
            }
            LinkedListGraph<V, E> graph = (LinkedListGraph<V, E>) param;
            if (graph.vertices.isEmpty()) {
                return Collections.emptyList();
            }
            // 任意选取一个顶点开始
            Vertex<V, E> begin = graph.vertices.values().stream().findAny().orElse(null);
            // 返回值
            List<EdgeInfo<V, E>> retList = newList();
            // 已经访问过的顶点
            Set<Vertex<V, E>> visitedVertexSet = newSet();
            visitedVertexSet.add(begin);
            // 通过堆来求得最小边
            IHeap<Edge<V, E>> minHeap = new BinaryHeap<>(begin.outEdges, IHeap.Type.MIN, (o1, o2) -> graph.edgeCompare(o1.weight, o2.weight));
            // 生成树的边数等于原图顶点数减一, 因此这边循环的终止条件就是retList等于顶点数减一
            int vertices = graph.vertices.size() - 1;
            while (!minHeap.isEmpty() || retList.size() < vertices) {
                // 移除堆顶元素, 相当于获取权重最小的边
                Edge<V, E> minEdge = minHeap.remove();
                if (visitedVertexSet.contains(minEdge.to)) {
                    // 有可能当前这条最小边的对端顶点已经被访问过了, 就不能再次选取它.
                    continue;
                }
                // 获取到一条相对最小边, 将其加入到返回值中
                retList.add(new EdgeInfo<>(minEdge.from.value, minEdge.to.value, minEdge.weight));
                // 表示它的对端顶点已经被访问过了
                visitedVertexSet.add(minEdge.to);
                // 将被选取到的顶点的出度边也加入到最小堆中, 再下一轮中选择相对最小的边
                minHeap.addAll(minEdge.to.outEdges.stream().filter(e -> !visitedVertexSet.contains(e.to)).collect(Collectors.toSet()));
            }
            return retList;
        }
    }

    /**
     * Kruskal算法
     *
     * @param <V> 顶点值
     * @param <E> 边权值
     */
    private static class Kruskal<V, E> implements IMstStrategy<V, E> {

        @Override
        public List<IGraph.EdgeInfo<V, E>> mst(IGraph<V, E> graph) {
            return null;
        }

    }

    /**
     * Dijkstra算法
     */
    private static class Dijkstra<V, E> implements IShortestPathStrategy<V, E> {

        @Override
        public List<IGraph.EdgeInfo<V, E>> shortestPath(IGraph<V, E> graph, V v) {
            return null;
        }
    }

    /**
     * Bellman-Ford算法
     */
    private static class BellmanFord<V, E> implements IShortestPathStrategy<V, E> {

        @Override
        public List<IGraph.EdgeInfo<V, E>> shortestPath(IGraph<V, E> graph, V v) {
            return null;
        }
    }

}
