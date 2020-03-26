package com.sym.dataStructure.queue.priorityQueue;
/**
 * 优先级队列需要使用的结点
 * @author Administrator
 *
 */
public class PriorityNode {
	
	private Object data;//实际数据
	
	private int priority;//优先值
	
	private PriorityNode next;//下一个结点引用

	/*有参构造器：必须给定优先值*/
	public PriorityNode(Object data, int priority) {
		this.data = data;
		this.priority = priority;
	}

	/*set、get*/
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public PriorityNode getNext() {
		return next;
	}

	public void setNext(PriorityNode next) {
		this.next = next;
	}
}
