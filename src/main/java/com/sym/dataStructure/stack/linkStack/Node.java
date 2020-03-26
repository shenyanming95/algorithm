package com.sym.dataStructure.stack.linkStack;

/**
 * 链栈可以用链表实现，同样也需要一个结点类
 * @author Administrator
 */
public class Node {
	private Object data;// 该结点包含的数据项
	private Node next;// 该结点指向下一个结点的引用

	public Node() {
		this(null, null);
	}

	public Node(Object obj) {
		this(obj, null);
	}

	public Node(Object obj, Node node) {
		this.data = obj;
		this.next = node;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
}
