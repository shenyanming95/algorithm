package com.sym.structure.stack.array;

import com.sym.structure.stack.IStack;

/**
 * 自定义顺序栈.
 * 栈跟线性表大致一样, 只不过它有了限制条件, 只能在一头操作数据, 称为栈.
 * 另一头称为栈底,和栈是先进后出(first in last out，FILO)，栈也可以分为顺序栈链栈
 *
 * @author ym.shen
 * Created on 2020/5/8 14:59
 */
public class ArrayStack<T> implements IStack<T> {

    /**
     * 顺序栈底层的储存结构，数组
     */
    private Object[] elementData;

    /**
     * 栈顶指针，可以指向栈顶元素，也可以指向栈顶元素的下一个位置，一般是以后者为准
     */
    private int top;

    public ArrayStack() {
        elementData = new Object[10];
        top = 0;
    }

    public ArrayStack(int capacity) {
        elementData = new Object[capacity];
        top = 0;
    }

    @Override
    public void push(T t) {
        //指针top的值比数组下标多1
        if (top == elementData.length) {
            throw new IllegalArgumentException("栈已满，元素无法入栈");
        }
        elementData[top++] = t;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T pop() {
        //栈为空，则无法将元素出栈
        if (top == 0) {
            throw new IllegalArgumentException("栈为空，无元素出栈");
        }
        return (T) elementData[--top];
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public int length() {
        return top;
    }

    @Override
    public void display() {
        for (int i = top - 1; i >= 0; i--) {
            System.out.print(elementData[i].toString() + " ");
        }
        System.out.println();
    }
}
