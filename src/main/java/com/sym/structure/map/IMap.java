package com.sym.structure.map;

/**
 * Map, 映射, 也称为字典, 属于一种key-value存储的数据结构.
 *
 * @author shenyanming
 * @date 2020/7/14 21:47.
 */
public interface IMap<K, V> {

    /**
     * 新增元素
     *
     * @param key   键
     * @param value 值
     * @return 若是新增返回null, 若是修改返回旧值
     */
    V put(K key, V value);

    /**
     * 根据key获取对应的值
     *
     * @param key 键
     * @return key对应的value
     */
    V get(K key);

    /**
     * 移除key, 及其对应的value
     *
     * @param key 键
     */
    void remove(K key);

    /**
     * 返回Map存放的元素数量
     *
     * @return 元素数量
     */
    int size();

    /**
     * 判断是否为空映射
     *
     * @return true-空
     */
    boolean isEmpty();

    /**
     * 清空映射Map
     */
    void clear();

    /**
     * 判断当前映射Map是否存在key
     *
     * @param key 键
     * @return true-存在
     */
    boolean containKey(K key);

    /**
     * 判断当前映射Map是否存在value
     *
     * @param value 值
     * @return true-存在
     */
    boolean containValue(V value);
}
