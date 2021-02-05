package com.sym;

import com.sym.structure.graph.IAdvancedGraph;
import com.sym.structure.graph.IGraph;
import com.sym.structure.graph.impl.list.LinkedListGraph;
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
        IGraph<Integer, Integer> graph = new LinkedListGraph<>();
        graph.addEdge(10, 20, 1);
        graph.addEdge(10, 30, 2);
        graph.addEdge(40, 50, 4);
        System.out.println(graph);
    }

    @Test
    public void advancedTest(){
        IAdvancedGraph<String, Integer> graph = new LinkedListGraph<>();
    }
}
