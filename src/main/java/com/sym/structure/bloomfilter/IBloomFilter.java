package com.sym.structure.bloomfilter;

import java.util.Collection;

/**
 * 布隆过滤器.
 * 是一个空间效率高的概率型数据结构, 本质是一个二进制向量和若干随机哈希函数,
 * 用于定位某个元素一定不存在和可能存在的情况. 布隆过滤器误判率受到3个元素影响：
 * 1.二进位个数m
 * 2.哈希函数个数k
 * 3.数据规模n
 * <p>
 * 因此使用布隆过滤器的时候, 需要明确2个点：数据规模n、允许的误判率p, 通过这两个值
 * 推导出需要的二进制位的个数m和哈希函数的个数k：
 * m = - ((n*lnp) / (ln2)2)
 * k = - log2p
 * (其中ln指的是log2)
 *
 * @author shenyanming
 * Created on 2021/1/16 9:34.
 */

public interface IBloomFilter<T> {

    /**
     * 添加元素到布隆过滤器中
     *
     * @param t 元素
     */
    void put(T t);

    /**
     * 批量添加元素到布隆过滤器
     *
     * @param collection 集合
     */
    void put(Collection<T> collection);

    /**
     * 判断一个元素是否存在
     *
     * @param t 元素
     * @return true-可能存在, false-一定不存在
     */
    boolean contains(T t);
}
