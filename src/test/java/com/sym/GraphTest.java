package com.sym;

import com.sym.structure.graph.IAdvancedGraph;
import com.sym.structure.graph.IGraph;
import com.sym.structure.graph.impl.list.LinkedListGraph;
import com.sym.structure.graph.impl.list.UndirectedListGraph;
import com.sym.structure.string.impl.String;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 图的测试类
 *
 * @author shenyanming
 * @date 2020/11/8 15:29.
 */
@Slf4j
public class GraphTest {

    @Test
    public void baseTest() {
        IGraph<Integer, Integer> graph = new LinkedListGraph<>(WeightHandlers.INTEGER_HANDLER);
        graph.addEdge(10, 20, 1);
        graph.addEdge(10, 30, 2);
        graph.addEdge(40, 50, 4);
        graph.addEdge(20, 10, 4);
        graph.addEdge(40, 70, 4);
        System.out.println(graph.addVertex(100));
        System.out.println(graph.removeVertex(100));
        System.out.println(graph);
        System.out.println(graph.verticesSize());
        System.out.println(graph.edgeSize());
        System.out.println(graph.removeEdge(40, 50));
        System.out.println(graph);
    }

    @Test
    public void bfsTest() {
        IAdvancedGraph<String, Integer> graph = new LinkedListGraph<>(WeightHandlers.INTEGER_HANDLER);
        graph.addEdge(new String("V1"), new String("V0"), 9);
        graph.addEdge(new String("V1"), new String("V2"), 3);
        graph.addEdge(new String("V2"), new String("V0"), 2);
        graph.addEdge(new String("V2"), new String("V3"), 5);
        graph.addEdge(new String("V3"), new String("V4"), 1);
        graph.addEdge(new String("V0"), new String("V4"), 6);
        graph.bfs(new String("V1"), System.out::println);
    }

    @Test
    public void dfsTest() {
        IAdvancedGraph<Integer, Void> graph = new UndirectedListGraph<>(WeightHandlers.VOID_HANDLER);
        graph.addEdge(1, 0);
        graph.addEdge(1, 3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 4);
        graph.addEdge(1, 6);
        graph.addEdge(1, 5);
        graph.addEdge(3, 7);
        graph.dfs(1, System.out::println);
    }

    @Test
    public void primTest() {
        IAdvancedGraph<java.lang.String, Integer> graph = new UndirectedListGraph<>(WeightHandlers.INTEGER_HANDLER);
        graph.addEdge("A", "B", 17);
        graph.addEdge("A", "E", 16);
        graph.addEdge("A", "F", 1);
        graph.addEdge("E", "F", 33);
        graph.addEdge("B", "F", 11);
        graph.addEdge("E", "D", 4);
        graph.addEdge("F", "D", 14);
        graph.addEdge("B", "D", 5);
        graph.addEdge("B", "C", 6);
        graph.addEdge("D", "C", 10);
        graph.mst().forEach(System.out::println);
    }

    @Test
    public void kruskalTest() {
        IAdvancedGraph<java.lang.String, Integer> graph =
                new UndirectedListGraph<>(new LinkedListGraph.Kruskal<>(), new LinkedListGraph.Dijkstra<>(), WeightHandlers.INTEGER_HANDLER);
        graph.addEdge("A", "B", 17);
        graph.addEdge("A", "E", 16);
        graph.addEdge("A", "F", 1);
        graph.addEdge("E", "F", 33);
        graph.addEdge("B", "F", 11);
        graph.addEdge("E", "D", 4);
        graph.addEdge("F", "D", 14);
        graph.addEdge("B", "D", 5);
        graph.addEdge("B", "C", 6);
        graph.addEdge("D", "C", 10);
        graph.mst().forEach(System.out::println);
    }

    @Test
    public void dijkstraTest(){
        IAdvancedGraph<Character, Integer> graph = new LinkedListGraph<>(WeightHandlers.INTEGER_HANDLER);
        graph.addEdge('A', 'E', 100);
        graph.addEdge('A', 'D', 30);
        graph.addEdge('A', 'B', 10);
        graph.addEdge('B', 'C', 50);
        graph.addEdge('C', 'E', 10);
        graph.addEdge('D', 'C', 20);
        graph.addEdge('D', 'E', 60);
        graph.shortestPath('A').forEach(System.out::println);
    }

    @Test
    public void bellmanFordTest(){
        IAdvancedGraph<Character, Integer> graph = new LinkedListGraph<>(new LinkedListGraph.Prim<>(),
                new LinkedListGraph.BellmanFord<>(), WeightHandlers.INTEGER_HANDLER);
        graph.addEdge('A', 'E', 100);
        graph.addEdge('A', 'D', 30);
        graph.addEdge('A', 'B', 10);
        graph.addEdge('B', 'C', 50);
        graph.addEdge('C', 'E', 10);
        graph.addEdge('D', 'C', 20);
        graph.addEdge('D', 'E', 60);
        graph.shortestPath('A').forEach(System.out::println);
    }

    /**
     * 边权值比较的工具类
     */
    static class WeightHandlers {
        /**
         * 整数
         */
        public static IGraph.IWeightHandler<Integer> INTEGER_HANDLER = new IGraph.IWeightHandler<Integer>() {
            @Override
            public int compare(Integer e1, Integer e2) {
                return Integer.compare(e1, e2);
            }

            @Override
            public Integer add(Integer e1, Integer e2) {
                return Integer.sum(e1, e2);
            }
        };

        /**
         * 长整数
         */
        public static IGraph.IWeightHandler<Long> LONG_HANDLER = new IGraph.IWeightHandler<Long>() {
            @Override
            public int compare(Long e1, Long e2) {
                return Long.compare(e1, e2);
            }

            @Override
            public Long add(Long e1, Long e2) {
                return Long.sum(e1, e2);
            }
        };

        /**
         * 字符串
         */
        public static IGraph.IWeightHandler<java.lang.String> STRING_HANDLER = new IGraph.IWeightHandler<java.lang.String>() {
            @Override
            public int compare(java.lang.String e1, java.lang.String e2) {
                return e1.compareTo(e2);
            }

            @Override
            public java.lang.String add(java.lang.String e1, java.lang.String e2) {
                return e1.concat(e2);
            }
        };

        /**
         * 所有类型
         */
        public static IGraph.IWeightHandler<Void> VOID_HANDLER = new IGraph.IWeightHandler<Void>() {
            @Override
            public int compare(Void e1, Void e2) {
                return 0;
            }

            @Override
            public Void add(Void e1, Void e2) {
                return null;
            }
        };
    }
}
