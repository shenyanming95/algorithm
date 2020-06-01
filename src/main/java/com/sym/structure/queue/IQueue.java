package com.sym.structure.queue;

/**
 * 队列接口
 */
public interface IQueue<E> {

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
	int size();

	/**
	 * 获取队首元素,不会执行出队操作
	 * @return 队首元素
	 */
	E peek();

	/**
	 * 入队操作
	 * @param e 数据
	 */
	void offer(E e);

	/**
	 * 出队操作
	 * @return 队首元素
	 */
	E poll();

}
