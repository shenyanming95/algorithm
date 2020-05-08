package com.sym.datastructure.queue.array;

import com.sym.datastructure.queue.IQueue;

/**
 * 顺序队列,底层还是以数组来储存元素,但是为了防止假溢出,逻辑上处理成循环队列（可以通过取模实现）
 * front 表示队首,指向队列的首元素
 * rear  表示队尾,指向队列尾元素的下一个储存位置
 *
 * 顺序循环队列有一个问题：就是队列满和队列空的判断条件是一样的，所以需要额外处理：
 * 1.队列保留一个空位不保存数据,这样front和rear就不会在同一位置上;
 * 2.增加一个字段,用来标注队列是否为空;
 * 这个类采用第1种方式.
 *
 * @author ym.shen
 * @see  CircleArrayQueueV2
 */
public class CircleArrayQueue implements IQueue {

    /**
     * 底层数组,保存队列的元素
     */
    private Object[] elements;

    /**
     * 队首指针
     */
    private int front;

    /**
     * 队尾指针
     */
    private int rear;

    /**
     * 数组最大值
     */
    private int maxLength;


    public CircleArrayQueue(int capacity) {
        elements = new Object[capacity + 1];
        front = rear = 0;
        maxLength = elements.length;
    }

    @Override
    public void clear() {
        //逻辑上置空
        front = rear = 0;
    }

    @Override
    public boolean isEmpty() {
        return front == rear;
    }

    public boolean isFull() {
        return front == (rear + 1) % maxLength;
    }

    @Override
    public int length() {
        // 取队首和队尾差值的绝对值
        return (rear - front + elements.length) % elements.length;
    }

    @Override
    public Object peek() {
        if (isEmpty()) {
            return null;
        } else {
            return elements[front];
        }
    }

    @Override
    public void offer(Object o) {
        if (isFull()) {
            throw new IllegalArgumentException("queue is full");
        }
        elements[rear] = o;
        rear = (rear + 1) % elements.length;
    }

    @Override
    public Object poll() {
        if (isEmpty()) {
            throw new IllegalArgumentException("queue is null");
        }
        Object t = elements[front];
        front = (front + 1) % elements.length;
        return t;
    }

    @Override
    public void display() {
        if (isEmpty()) {
            System.out.println("[]");
            return;
        }
        System.out.print("[");
        int index = front;
        for (int i = 0, len = (rear - front + maxLength) % maxLength; i < len; i++) {
            System.out.print(elements[index] + (i == len - 1 ? "" : ","));
            index = (index + 1) % maxLength;
        }
        System.out.println("]");
    }
}
