package com.sym.structure.queue.linked;

import com.sym.structure.queue.IQueue;
import lombok.Data;

/**
 * 链式队列
 *
 * @author ym.shen
 */
public class LinkedQueue<E> implements IQueue<E> {

    /**
     * 队首结点
     */
    private Node<E> front;

    /**
     * 队尾结点(直接指向,区别于顺序队列指向队尾的下一个储存位置)
     */
    private Node<E> rear;

    /**
     * 链队列跟链栈一样,同样也需要一个结点类
     */
    @Data
    private static class Node<E> {
        // 该结点包含的数据项
        private E data;

        // 该结点指向下一个结点的引用
        private Node<E> next;

        public Node(E e) {
            this(e, null);
        }

        public Node(E e, Node<E> node) {
            this.data = e;
            this.next = node;
        }
    }

    public LinkedQueue() {
        front = rear = null;
    }

    /**
     * 清空链队列
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
     * 队列长度
     */
    @Override
    public int size() {
        //取到队列首结点
        Node<E> p = front;
        int count = 0;
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
     * 往队尾添加新元素
     */
    @Override
    public void offer(E e) {
        Node<E> n = new Node<>(e);
        //链队列就没有队列满的情况,但有空队列的判断
        if (front == null) {
            front = rear = n;
        } else {
            rear.setNext(n);
            rear = n;
        }
    }

    /**
     * 队首取元素
     */
    @Override
    public E poll() {
        //队列如果为空
        if (front == null) {
            return null;
        }
        //队列不为空
        E e = front.getData();
        front = front.getNext();
        //如果此时取得是队列最后一个元素,即front和rear都指向同一个结点
        if (front == null) {
            rear = null;
        }
        return e;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> p = front;
        while (p != null) {
            sb.append(p.getData()).append(" ");
            p = p.getNext();
        }
        if(sb.length() > 0){
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }
}
