package com.sym.structure.queue.priority;


import com.sym.structure.queue.IQueue;
import lombok.Data;

import java.util.Comparator;

/**
 * 优先级队列,队首弹出元素,不仅仅限制在队尾添加元素,
 * 现在需要按照优先数的大小来排列,谁先出
 *
 * @author ym.shen
 */
public class PriorityQueue<E> implements IQueue<E> {

    /**
     * 队首结点
     */
    private Node<E> front;

    /**
     * 队尾结点
     */
    private Node<E> rear;

    /**
     * 比较器
     */
    private Comparator<E> comparator;

    public PriorityQueue(){

    }

    public PriorityQueue(Comparator<E> comparator){
        this.comparator = comparator;
    }

    /**
     * 优先级队列需要使用的结点
     */
    @Data
    private static class Node<E> {
        //实际数据
        private E data;

        //下一个结点引用
        private Node<E> next;

        public Node(E data) {
            this.data = data;
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
    public int size() {
        int count = 0;
        Node<E> p = front;
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
    public E peek() {
        if (front == null){
            return null;
        }
        return front.getData();
    }

    /**
     * 往队列添加新元素,这是优先级队列的关键方法,需要比较新元素与已存在元素的优先值
     */
    @Override
    public void offer(E e) {
        Node<E> newNode = new Node<>(e);
        //如果队列为空,队首和队尾都指向新结点
        if (front == null) {
            front = rear = newNode;
        } else {
            // 用来遍历队列所有的元素
            Node<E> current = front;
            // 保存新元素插入位置的前一个结点
            Node<E> prev = front;

            while (current != null && doCompare(e, current.data)) {
                prev = current;
                current = current.getNext();
            }
            if (current == null) {
                // 说明此时的q是队尾结点,且新结点的优先值大于队列内的所有结点
                prev.setNext(newNode);
                rear = newNode;
            } else if (current == front) {
                // 说明此时的P是队首结点,即新结点的优先值小于队列内的所有结点
                newNode.setNext(current);
                front = newNode;
            } else {
                // 说明此时处在中间
                newNode.setNext(current);
                prev.setNext(newNode);
            }
        }
    }

    /**
     * 比较e1和e2大小
     * @param e1 元素1
     * @param e2 元素2
     * @return 大于返回true, 其它返回false
     */
    @SuppressWarnings("unchecked")
    private boolean doCompare(E e1, E e2){
        if(null != comparator){
            return comparator.compare(e1, e2) > 0;
        }
        return ((Comparable)e1).compareTo(e2) > 0;
    }

    /**
     * 弹出队首元素
     */
    @Override
    public E poll() {
        if (front == null){
            return null;
        }
        Node<E> p = front;
        front = p.getNext();
        return p.getData();
    }

    @Override
    public String toString() {
        if (front == null){
            return "[]";
        } else {
            StringBuilder s = new StringBuilder("[");
            Node<E> p = front;
            while (p != null) {
                s.append(p.getData()).append(", ");
                p = p.getNext();
            }
            // 去掉逗号
            if(s.length() > 1){
                s.delete(s.length() - 2, s.length());
            }
            return s.append("]").toString();
        }
    }
}
