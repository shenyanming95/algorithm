package com.sym.dataStructure.stack.linkStack;
/**
 * 自定义链栈,链栈的性质跟链表差不多，链栈也需要一个结点类
 * @author Administrator
 *
 */
public class MyLinkedStack {
	
	//栈顶指针
	private Node top;
	
	//无参构造方法，链栈入栈和出栈都只在栈顶实现，因此它不像链表需要一个头结点
	public MyLinkedStack(){
		
	}
	
	/*
	 * 入栈,链栈的入栈跟顺序栈的入栈不一样，顺序栈的栈顶指针指向栈顶元素的下一个位置
	 * 而链栈的栈顶指针直接指向栈顶元素
	 */
	public void push(Object o){
		//新结点，往栈底压，top指针指向它
		Node p = new Node(o);
		p.setNext(top);
		top = p;
	}
	
	/*
	 * 出栈，弹出top指向的栈顶元素，然后将top指向下一个元素
	 */
	public Object pop(){
		if(isEmpty())
			return null;
		Node p = top;
		top = top.getNext();
		return p.getData();
	}
	
	/*
	 * 判断栈是否为空
	 */
	public boolean isEmpty(){
		return top == null;
	}
	
	/*
	 * 链栈的长度
	 */
	public int length(){
		int j = 0;
		Node p = top;
		while(p!=null){
			p = p.getNext();
			j++;
		}
		return j;		
	}
	
	/*
	 *遍历全栈 
	 */
	public void display(){
		Node p = top;
		while(p!=null){
			System.out.print(p.getData()+" ");
			p = p.getNext();
		}
		System.out.println();
	}

}
