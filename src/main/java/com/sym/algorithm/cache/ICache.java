package com.sym.algorithm.cache;

/**
 * 缓存接口
 *
 * @author shenyanming
 * Created on 2020/5/19 11:04
 */
public interface ICache<K, V> {

    /**
     * 新增或修改缓存信息
     *
     * @param key   键
     * @param value 值
     */
    void set(K key, V value);

    /**
     * 获取缓存值
     *
     * @param key 键
     * @return 值
     */
    V get(K key);

    /**
     * 移除缓存值
     *
     * @param key 键
     * @return 旧值, 若key不存在, 返回null
     */
    V remove(K key);
}
