package com.sym.dataStructure.queue.linkQueue;

import lombok.Data;

/**
 * 链队列跟链栈一样,同样也需要一个结点类
 * @author shenym
 */
@Data
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
}
