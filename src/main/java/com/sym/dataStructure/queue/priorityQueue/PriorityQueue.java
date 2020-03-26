package com.sym.dataStructure.queue.priorityQueue;


import com.sym.dataStructure.queue.ISymQueue;

/**
 * 优先级队列,队首弹出元素,不仅仅限制在队尾添加元素
 * 
 * 现在需要按照优先数的大小来排列,谁先出
 * 
 */
public class PriorityQueue implements ISymQueue {

	private PriorityNode front;// 队首结点

	private PriorityNode rear;// 队尾结点

	/* 将队列清空 */
	@Override
	public void clear() {
		front = rear = null;
	}

	/* 判断队列是否为空 */
	@Override
	public boolean isEmpty() {
		return front == null;
	}

	/* 返回队列的长度 */
	@Override
	public int length() {
		int count = 0;
		PriorityNode p = front;
		while (p != null) {
			p = p.getNext();
			++count;
		}
		return count;
	}

	/* 读取队首元素 */
	@Override
	public Object peek() {
		if (front == null)
			return null;
		return front.getData();
	}

	/* 往队列添加新元素,这是优先级队列的关键方法,需要比较新元素与已存在元素的优先值 */
	@Override
	public void offer(Object o) {
		if (o instanceof PriorityNode) {

			PriorityNode newNode = (PriorityNode) o;
            //如果队列为空,队首和队尾都指向新结点
			if (front == null)
				front = rear = newNode;
			else {
				PriorityNode p = front;// 用来遍历队列所有的元素

				PriorityNode q = front;// 保存新元素插入位置的前一个结点

				while (p != null && newNode.getPriority() > p.getPriority()) {
					q = p;
					p = p.getNext();
				}
				if (p == null) {
					// 说明此时的q是队尾结点,且新结点的优先值大于队列内的所有结点
					q.setNext(newNode);
					newNode = rear;
				} else if (p == front) {
					// 说明此时的P是队首结点,即新结点的优先值小于队列内的所有结点
					newNode.setNext(p);
					front = newNode;
				} else {
					// 说明此时处在中间
					newNode.setNext(p);
					q.setNext(newNode);
				}
			}

		}
	}

	/* 弹出队首元素 */
	@Override
	public Object poll() {
		if (front == null)
			return null;
		PriorityNode p = front;
		front = p.getNext();
		return p.getData();
	}

	/* 遍历优先级队列 */
	@Override
	public void display() {
		if (front == null)
			System.out.println("队列为空");
		else {
			String s = "";
			PriorityNode p = front;
			while (p != null) {
				s += "[data=" + p.getData() + ",priority=" + p.getPriority() + "],";
				p = p.getNext();
			}
			String result = s.substring(0, s.length() - 1);// 去掉逗号
			System.out.println(result);
		}
	}
}
