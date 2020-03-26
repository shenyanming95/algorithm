package com.sym.dataStructure.queue.linkQueue;

import com.sym.dataStructure.queue.ISymQueue;

/**
 * 链式队列
 * @author Administrator
 *
 */
public class LinkedQueue implements ISymQueue {
	
	private Node front;//队首结点
	
	private Node rear;//队尾结点(直接指向,区别于顺序队列指向队尾的下一个储存位置)
	
	/*初始化链队列*/
	public LinkedQueue(){
		front = rear = null;
	}
	

	/*清空链队列*/
	@Override
	public void clear() {
		front = rear = null;
	}

	/*判断队列是否为空*/
	@Override
	public boolean isEmpty() {	
		return front == null;
	}

	/*队列长度*/
	@Override
	public int length() {
		Node p = front;//取到队列首结点
		int count = 0;
		while( p!= null){
			p = p.getNext();
			++count;
		}
		return count;
	}

	/*读取队首元素*/
	@Override
	public Object peek() {
		if( front== null)
			return null;
		return  front.getData();
	}

	/*往队尾添加新元素*/
	@Override
	public void offer(Object o) {
		Node n = new Node(o);
		//链队列就没有队列满的情况,但有空队列的判断
		if( front == null)
			front = rear = n;
		else{
			rear.setNext(n);
			rear = n;
		}	
	}

	/*队首取元素*/
	@Override
	public Object poll() {
		//队列如果为空
		if( front == null )
			return null;
		//队列不为空
		Object o = front.getData();
		front = front.getNext();
		//如果此时取得是队列最后一个元素,即front和rear都指向同一个结点
		if( front == null)
			rear = null;
		return o;
	}

	/*遍历整个队列*/
	@Override
	public void display() {
		Node p = front;
		while( p != null ){
			System.out.print(p.getData()+" ");
			p = p.getNext();
		}	
		System.out.println();//换行
	}
}
