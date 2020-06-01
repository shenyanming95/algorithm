package com.sym.structure.queue.array;

import com.sym.structure.queue.IQueue;

/**
 * 基于数组实现的队列
 *
 * @author shenyanming
 * @date 2020/5/24 21:26.
 */

public interface IArrayQueue<E> extends IQueue<E> {

    /**
     * 判断队列是否已满
     *
     * @return true-队列已满
     */
    boolean isFull();

}
