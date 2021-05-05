package com.sym.algorithm.example.sort;

/**
 * 排序接口
 *
 * @author shenyanming
 * Created on 2020/9/2 09:51
 */
public interface ISort<E extends Comparable<E>> {

    /**
     * 执行排序
     *
     * @return 排序后的数组
     */
    E[] sort();

}
