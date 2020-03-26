package com.sym.dataStructure.stack.arrayStack;
/**
 * 自定义顺序栈
 * 栈跟线性表大致一样，只不过它有了限制条件，只能在一头操作数据，称为栈顶，另一头称为栈底
 * 栈是先进后出(first in last out，FILO)，栈也可以分为顺序栈和链栈
 * @author Administrator
 */
public class MyArrayStack {
	
	//顺序栈底层的储存结构，数组
	private Object[] elementData;
	
	//栈顶指针，可以指向栈顶元素，也可以指向栈顶元素的下一个位置，一般是以后者为准
	private int top;
	
	//无参构造方法，默认10个储存空间
	public MyArrayStack(){
		elementData = new Object[10];
		top = 0;
	}
	//有参构造方法，指定储存空间
	public MyArrayStack(int i){
		elementData = new Object[i];
		top = 0;
	}
	/*
	 * 入栈，将元素压入栈顶，栈顶指针+1
	 */
	public void push(Object o){
		//指针top的值比数组下标多1
		if( top== 10)
			throw new RuntimeException("栈已满，元素无法入栈");
		elementData[top++] = o;
	}
	
	/*
	 * 出栈，将元素推出栈顶，栈顶指针-1
	 */
	public Object pop(){
		//栈为空，则无法将元素出栈
		if( top== 0)
			throw new RuntimeException("栈为空，无元素出栈");
		return elementData[--top];
	}
	
	/*
	 * 判断栈是否为空
	 */
	public boolean isEmpty(){
		return top==0 ;
	}
	
	/*
	 * 判断栈的长度
	 */
	public int length(){
		return top;
	}
	
	/*
	 * 输出栈中所有的元素,注意出栈顺序：从栈顶到栈底
	 */
	public void display(){
		for (int i = top-1; i >= 0 ; i--) {
			System.out.print(elementData[i].toString()+" ");
		}
		System.out.println();
	}

}
