package com.sym.dataStructure.stack;

import com.sym.dataStructure.stack.linkStack.MyLinkedStack;
import com.sym.dataStructure.stack.arrayStack.MyArrayStack;
import org.junit.Test;

/**
 * 测试顺序栈和链栈
 * @author Administrator
 */
public class StackTest {

	/**
	 * 测试顺序栈
	 */
	@Test
	public void linearStack() {
		MyArrayStack stack = new MyArrayStack();
		System.out.println("==============测试顺序栈===============");
		System.out.println("new一个顺序栈，是否为空？"+stack.isEmpty());
		for (int i = 0; i < 5; i++) {
			stack.push(i);
		}
		System.out.println("往栈中压入5个元素，栈大小为："+stack.length());
		System.out.print("遍历栈中的元素：");
		stack.display();
		System.out.println("弹出栈顶元素："+stack.pop());
		System.out.print("遍历栈中的元素：");
		stack.display();
	}

	/**
	 * 测试链栈
	 */
	@Test
	public void linkedStack() {
		MyLinkedStack stack = new MyLinkedStack();
		System.out.println("==============测试链栈===============");
		System.out.println("new一个链栈，是否为空？"+stack.isEmpty());
		for (int i = 0; i < 5; i++) {
			stack.push(i);
		}
		System.out.println("往栈中压入5个元素，栈大小为："+stack.length());
		System.out.print("遍历栈中的元素：");
		stack.display();
		System.out.println("弹出栈顶元素："+stack.pop());
		System.out.print("遍历栈中的元素：");
		stack.display();
	}
}
