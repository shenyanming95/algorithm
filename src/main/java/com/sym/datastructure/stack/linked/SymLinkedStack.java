package com.sym.datastructure.stack.linked;

import com.sym.datastructure.stack.IStack;
import lombok.Data;

/**
 * @author ym.shen
 * Created on 2020/5/8 15:05
 */
public class SymLinkedStack<T> implements IStack<T> {

    /**
     * 栈顶指针
     */
    private Node top;

    /**
     * 无参构造方法，链栈入栈和出栈都只在栈顶实现，因此它不像链表需要一个头结点
     */
    public SymLinkedStack() {
    }

    /**
     * 链栈可以用链表实现，同样也需要一个结点类
     */
    @Data
    static class Node {
        /**
         * 该结点包含的数据项
         */
        private Object data;

        /**
         * 该结点指向下一个结点的引用
         */
        private Node next;

        public Node() {
            this(null, null);
        }

        public Node(Object data) {
            this(data, null);
        }

        public Node(Object data, Node node) {
            this.data = data;
            this.next = node;
        }
    }


    /**
     * 入栈,链栈的入栈跟顺序栈的入栈不一样，顺序栈的栈顶指针指向栈顶元素的下一个位置
     * 而链栈的栈顶指针直接指向栈顶元素
     *
     * @param t 元素
     */
    @Override
    public void push(T t) {
        // 新结点，往栈底压，top指针指向它
        Node p = new Node(t);
        p.setNext(top);
        top = p;
    }

    /**
     * 出栈，弹出top指向的栈顶元素，然后将top指向下一个元素
     *
     * @return 栈顶元素
     */
    @Override
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        Node p = top;
        top = top.getNext();
        return (T) p.getData();
    }

    /**
     * 判断栈是否为空
     *
     * @return 空栈返回true
     */
    @Override
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * 链栈的长度
     *
     * @return 栈大小
     */
    @Override
    public int length() {
        int j = 0;
        Node p = top;
        while (p != null) {
            p = p.getNext();
            j++;
        }
        return j;
    }

    @Override
    public void display() {
        Node p = top;
        while (p != null) {
            System.out.print(p.getData() + " ");
            p = p.getNext();
        }
        System.out.println();
    }
}
