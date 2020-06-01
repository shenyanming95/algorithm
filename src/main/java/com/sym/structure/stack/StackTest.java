package com.sym.structure.stack;

import com.sym.structure.stack.array.ArrayStack;
import com.sym.structure.stack.linked.LinkedStack;
import org.junit.Test;

/**
 * 测试顺序栈和链栈
 *
 * @author ym.shen
 * Created on 2020/5/8 15:18
 */
public class StackTest {

    /**
     * 测试顺序栈
     */
    @Test
    public void arrayStackTest() {
        IStack<Integer> stack = new ArrayStack<>();
        System.out.println("==============测试顺序栈===============");
        System.out.println("new一个顺序栈，是否为空？" + stack.isEmpty());
        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }
        System.out.println("往栈中压入5个元素，栈大小为：" + stack.length());
        System.out.print("遍历栈中的元素：");
        stack.display();
        System.out.println("弹出栈顶元素：" + stack.pop());
        System.out.print("遍历栈中的元素：");
        stack.display();
    }

    /**
     * 测试链栈
     */
    @Test
    public void linkedStackTest() {
        IStack<Integer> stack = new LinkedStack<>();
        System.out.println("==============测试链栈===============");
        System.out.println("new一个链栈，是否为空？" + stack.isEmpty());
        for (int i = 0; i < 7; i++) {
            stack.push(i);
        }
        System.out.println("往栈中压入7个元素，栈大小为：" + stack.length());
        System.out.print("遍历栈中的元素：");
        stack.display();
        System.out.println("弹出栈顶元素：" + stack.pop());
        System.out.print("遍历栈中的元素：");
        stack.display();
    }
}
