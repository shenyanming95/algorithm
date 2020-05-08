package com.sym.datastructure.stack;

/**
 * 栈接口
 *
 * @author ym.shen
 * Created on 2020/5/8 14:52
 */
public interface IStack<T> {

    /**
     * 将元素压入栈
     * @param t 元素
     */
    void push(T t);

    /**
     * 推出栈顶元素
     * @return 当前栈顶元素
     */
    T pop();

    /**
     * 判断当前栈是否为空
     * @return 空栈返回true
     */
    boolean isEmpty();

    /**
     * 当期栈的元素数量
     * @return 栈长度
     */
    int length();

    /**
     * 打印栈信息
     */
    void display();
}
