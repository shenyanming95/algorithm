package com.sym.structure.graph.strategy;

import com.sym.structure.graph.IGraph;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 图的最短路径策略
 *
 * @author shenyanming
 * Created on 2021/2/5 10:24
 */
public interface IShortestPathStrategy<V, E> {

    /**
     * 单源最短路径
     *
     * @param graph 指定图
     * @param v     指定顶点
     * @return 该节点到其它顶点的最短路径
     */
    default List<PathInfo<V, E>> shortestPath(IGraph<V, E> graph, V v) {
        return Collections.emptyList();
    }

    /**
     * 多源最短路径
     *
     * @param graph 指定图
     * @return 所有节点的最短路径
     */
    default Map<V, List<PathInfo<V, E>>> shortestPath(IGraph<V, E> graph) {
        return Collections.emptyMap();
    }

    /**
     * 用于记录最短路径
     *
     * @param <V>
     * @param <E>
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class PathInfo<V, E> {
        V to;
        E weight;
        Collection<V> paths;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            StringBuilder temp = new StringBuilder();
            if (Objects.nonNull(paths) && !paths.isEmpty()) {
                paths.forEach(v -> temp.append(v).append(" → "));
                temp.delete(temp.length() - 3, temp.length());
            }
            return sb.append("weight:{").append(weight).append("}, ")
                    .append("path:{").append(temp).append("}").toString();
        }
    }
}
