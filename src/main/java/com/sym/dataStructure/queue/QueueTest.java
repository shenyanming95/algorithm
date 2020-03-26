package com.sym.dataStructure.queue;

import com.sym.dataStructure.queue.ArrayQueue.CircleArrayQueue;
import com.sym.dataStructure.queue.linkQueue.LinkedQueue;
import com.sym.dataStructure.queue.priorityQueue.PriorityNode;
import com.sym.dataStructure.queue.priorityQueue.PriorityQueue;
import org.junit.Test;

import java.util.Random;

/**
 * 测试循环顺序队列、链队列、优先级队列
 */
public class QueueTest {

    /**
     * 测试顺序队列,{@link CircleArrayQueue}
     */
    @Test
    public void testLinearSqQueue() {
        CircleArrayQueue queue = new CircleArrayQueue(5);
        initQueue(5,queue);
        System.out.println("队列的长度为：" + queue.length());
        queue.display();
        System.out.println("读取队首元素：" + queue.peek());
        System.out.println("弹出队首元素：" + queue.poll());
        System.out.println("此时队列长度为：" + queue.length());
        System.out.println("加入新元素");
        queue.offer("新元素");
        queue.display();
    }


    /**
     * 链队列
     */
    @Test
    public void testLinkedQueue() {
        LinkedQueue queue = new LinkedQueue();
        for (int i = 0; i < 5; i++) {
            try {
                queue.offer("元素" + (i + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("队列的长度为：" + queue.length());
        System.out.print("遍历队列：");
        queue.display();
        System.out.println("读取队首元素：" + queue.peek());
        System.out.println("弹出队首元素：" + queue.poll());
        System.out.println("此时队列长度为：" + queue.length());
        System.out.println("加入新元素");
        try {
            queue.offer("新元素");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("遍历队列：");
        queue.display();
    }

    /**
     * 测试优先级队列
     */
    @Test
    public void testPriorityQueue() {
        PriorityQueue queue = new PriorityQueue();
        System.out.println("加入5个新元素...");
        for (int i = 0; i < 5; i++) {
            try {
                PriorityNode node = new PriorityNode("元素" + (i + 1), new Random().nextInt(50));
                queue.offer(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("此时队列长度=" + queue.length());
        System.out.print("遍历队列：");
        queue.display();
        System.out.println("读取队首元素：" + queue.peek());
        System.out.println("弹出队首元素：" + queue.poll());
        System.out.println("此时队列长度=" + queue.length());
        System.out.print("遍历队列：");
        queue.display();
        System.out.println("加入新元素...");
        try {
            PriorityNode newNode = new PriorityNode("我是新元素", new Random().nextInt(100));
            queue.offer(newNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("遍历队列：");
        queue.display();
    }


    /**
     * 初始化队列
     * @param capacity
     * @param queue
     */
    private void initQueue(int capacity, ISymQueue queue) {
        assert queue != null;
        for (int i = 0; i < capacity; i++) {
            queue.offer("元素" + (i + 1));
        }
    }

}
