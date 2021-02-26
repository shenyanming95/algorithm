package com.sym.structure.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 图接口, 定义图的基本操作.
 * 图有两种表示方式, 其一邻接矩阵, 其二邻接表, 不过实现上不会
 * 严格使用这两种表示方式, 因为边也有权重, 需要存储边的信息.
 *
 * @param <V> 顶点值的类型
 * @param <E> 边权值的类型
 * @author shenyanming
 * @date 2020/11/1 21:14.
 */
public interface IGraph<V, E> {

    /**
     * 添加顶点
     *
     * @param v 顶点值
     * @return true-添加成功
     */
    boolean addVertex(V v);

    /**
     * 添加没有权值的边
     *
     * @param from 边的起点
     * @param to   边的终点
     * @return true-添加成功
     */
    boolean addEdge(V from, V to);

    /**
     * 添加带有权值的边
     *
     * @param from   边的起点
     * @param to     边的终点
     * @param weight 权值
     * @return true-添加成功
     */
    boolean addEdge(V from, V to, E weight);

    /**
     * 删除顶点
     *
     * @param t 顶点值
     * @return true-删除成功
     */
    boolean removeVertex(V t);

    /**
     * 删除边
     *
     * @param from 边的起点
     * @param to   边的终点
     * @return true-删除成功
     */
    boolean removeEdge(V from, V to);

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

    /**
     * 对外暴露的描述顶点的信息
     *
     * @param <V> 顶点值的类型
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class VertexInfo<V> {
        V value;

        @Override
        public String toString() {
            return "{" + value + "}";
        }

        public static <V> VertexInfo<V> of(V value) {
            return new VertexInfo<>(value);
        }
    }

    /**
     * 对外暴露的描述边的信息
     *
     * @param <V> 顶点值的类型
     * @param <E> 边的权重值的类型
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class EdgeInfo<V, E> {
        V from;
        V to;
        E weight;

        @Override
        public String toString() {
            return "{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }
}
