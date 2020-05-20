package com.sym.datastructure.queue.priority;


import com.sym.datastructure.queue.IQueue;
import lombok.Data;

/**
 * 优先级队列,队首弹出元素,不仅仅限制在队尾添加元素,
 * 现在需要按照优先数的大小来排列,谁先出
 *
 * @author ym.shen
 */
public class PriorityQueue implements IQueue {

    /**
     * 队首结点
     */
    private Node front;

    /**
     * 队尾结点
     */
    private Node rear;

    /**
     * 优先级队列需要使用的结点
     */
    @Data
    public static class Node {
        //实际数据
        private Object data;

        //优先值
        private int priority;

        //下一个结点引用
        private Node next;

        /**
         * 有参构造器：必须给定优先值
         */
        public Node(Object data, int priority) {
            this.data = data;
            this.priority = priority;
        }
    }

    /**
     * 将队列清空
     */
    @Override
    public void clear() {
        front = rear = null;
    }

    /**
     * 判断队列是否为空
     */
    @Override
    public boolean isEmpty() {
        return front == null;
    }

    /**
     * 返回队列的长度
     */
    @Override
    public int length() {
        int count = 0;
        Node p = front;
        while (p != null) {
            p = p.getNext();
            ++count;
        }
        return count;
    }

    /**
     * 读取队首元素
     */
    @Override
    public Object peek() {
        if (front == null){
            return null;
        }
        return front.getData();
    }

    /**
     * 往队列添加新元素,这是优先级队列的关键方法,需要比较新元素与已存在元素的优先值
     */
    @Override
    public void offer(Object o) {
        if (o instanceof Node) {
            Node newNode = (Node) o;
            //如果队列为空,队首和队尾都指向新结点
            if (front == null) {
                front = rear = newNode;
            } else {
                // 用来遍历队列所有的元素
                Node p = front;
                // 保存新元素插入位置的前一个结点
                Node q = front;
                while (p != null && newNode.getPriority() > p.getPriority()) {
                    q = p;
                    p = p.getNext();
                }
                if (p == null) {
                    // 说明此时的q是队尾结点,且新结点的优先值大于队列内的所有结点
                    q.setNext(newNode);
                    newNode = rear;
                } else if (p == front) {
                    // 说明此时的P是队首结点,即新结点的优先值小于队列内的所有结点
                    newNode.setNext(p);
                    front = newNode;
                } else {
                    // 说明此时处在中间
                    newNode.setNext(p);
                    q.setNext(newNode);
                }
            }

        }
    }

    /**
     * 弹出队首元素
     */
    @Override
    public Object poll() {
        if (front == null){
            return null;
        }
        Node p = front;
        front = p.getNext();
        return p.getData();
    }

    /**
     * 遍历优先级队列
     */
    @Override
    public void display() {
        if (front == null){
            System.out.println("队列为空");
        } else {
            StringBuilder s = new StringBuilder();
            Node p = front;
            while (p != null) {
                s.append("[data=").append(p.getData()).append(",priority=").append(p.getPriority()).append("],");
                p = p.getNext();
            }
            // 去掉逗号
            String result = s.substring(0, s.length() - 1);
            System.out.println(result);
        }
    }
}
