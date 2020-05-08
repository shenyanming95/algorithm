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
 * 这个类采用第2种方式.
 *
 * @author ym.shen
 * @see  CircleArrayQueue
 */
public class CircleArrayQueueV2 implements IQueue {

    private Object[] elements;
    private int concurrentSize;
    private int front;
    private int rear;
    private int maxLength;

    public CircleArrayQueueV2() {
        this(10);
    }

    public CircleArrayQueueV2(int capacity) {
        maxLength = capacity;
        elements = new Object[maxLength];
    }


    @Override
    public void clear() {
        front = rear = concurrentSize = 0;
    }

    @Override
    public boolean isEmpty() {
        return concurrentSize == 0;
    }

    public boolean isFull() {
        return front == rear && concurrentSize > 0;
    }

    @Override
    public int length() {
        return maxLength;
    }

    @Override
    public Object peek() {
        if (front == rear && concurrentSize == 0) {
            throw new IllegalArgumentException("queue is null");
        }
        return elements[front];
    }

    @Override
    public void offer(Object o) {
        //首先判断队列是否满了
        if (isFull()) {
            throw new IllegalArgumentException("queue is full");
        }
        // 将新元素放在队尾指标rear指向的位置
        elements[rear] = o;
        // 然后将队尾指标加1取模（这样才能保证队列是循环队列）
        rear = (rear + 1) % maxLength;
        // 当前元素指标累加1
        concurrentSize++;
    }

    @Override
    public Object poll() {
        // 首先判断队列是否为空
        if (isEmpty()) {
            throw new IllegalArgumentException("queue is null");
        }
        // 取出队首指标指向的数组位置的元素
        Object o = elements[front];
        // 队首指标加1取模
        front = (front + 1) % maxLength;
        // 当前元素指标减1
        concurrentSize--;
        return o;
    }

    @Override
    public void display() {
        if (isEmpty()) {
            System.out.println("[]");
            return;
        }
        System.out.print("[");
        int index = front;
        for (int i = 0; i < concurrentSize; i++) {
            System.out.print(elements[index] + (i == concurrentSize - 1 ? "" : ","));
            index = (index + 1) % maxLength;
        }
        System.out.println("]");
    }
}
