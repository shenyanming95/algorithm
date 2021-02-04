package com.sym.structure.graph.base;

import com.sym.structure.graph.IGraph;

import java.util.*;

/**
 * 邻接表实现的图
 *
 * @author shenyanming
 * @date 2020/11/8 15:27.
 */

public class LinkedListGraph<V, E> implements IGraph<V, E> {

    // -------------------------------- fields

    /**
     * 每一个{@code V}都需要用一个{@link Vertex}存储, 用HashMap将它们关联起来.
     */
    private Map<V, Vertex<V, E>> vertices = newMap();

    /**
     * 存储当前图的所有边
     */
    private Set<Edge<V, E>> edges = newSet();

    // -------------------------------- static methods

    /**
     * 初始化set集合
     *
     * @param <T> 任意泛型
     * @return HashSet
     */
    static <T> Set<T> newSet() {
        return new HashSet<>();
    }

    /**
     * 初始化map映射
     *
     * @param <K> 任意泛型
     * @param <V> 任意泛型
     * @return HashMap
     */
    static <K, V> Map<K, V> newMap() {
        return new HashMap<>();
    }

    /**
     * 初始化一个顶点
     *
     * @param v 顶点值
     * @return Vertex
     */
    static <V, E> Vertex<V, E> newVertex(V v) {
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
    static <V, E> Edge<V, E> newEdge(Vertex<V, E> from, Vertex<V, E> to, E weight) {
        Edge<V, E> edge = new Edge<>();
        edge.from = from;
        edge.to = to;
        edge.weight = weight;
        return edge;
    }

    // -------------------------------- methods

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
        return Objects.nonNull(vertices.remove(v));
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
    }

}
