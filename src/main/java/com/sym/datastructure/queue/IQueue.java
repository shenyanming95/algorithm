package com.sym.datastructure.queue;

/**
 *
 * 队列接口
 */
public interface IQueue {

	/**
	 * 队列置空
	 */
	void clear();

	/**
	 * 判断队列是否为空
	 * @return true-表示空队列
	 */
	boolean isEmpty();

	/**
	 * 返回队列的长度
	 * @return 队列长度
	 */
	int length();

	/**
	 * 获取队首元素,不会执行出队操作
	 * @return 队首元素
	 */
	Object peek();

	/**
	 * 入队操作
	 * @param o 数据
	 */
	void offer(Object o);

	/**
	 * 出队操作
	 * @return 队首元素
	 */
	Object poll();

	/**
	 * 按队首→队尾顺序遍历队列
	 */
	void display();

}
