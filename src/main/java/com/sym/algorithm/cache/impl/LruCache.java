package com.sym.algorithm.cache.impl;

import com.sym.algorithm.cache.ICache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 基于{@link java.util.LinkedHashMap>的 LRU 缓存
 * (LRU, 即Least Recently Used, 最近最少使用)
 *
 * @author shenyanming
 * Created on 2020/5/20 17:37
 */
public class LruCache<K,V> implements ICache<K,V> {

    private LinkedHashMap<K,V> map;

    public LruCache(int capacity){
        map = new LryLinkedHashMap<>(capacity);
    }

    @Override
    public void set(K key, V value) {
        Objects.requireNonNull(key, "键不能为空");
        Objects.requireNonNull(value, "值不能为空");
        map.put(key, value);
    }

    @Override
    public V get(K key) {
        Objects.requireNonNull(key, "键不能为空");
        return map.get(key);
    }

    @Override
    public V remove(K key) {
        Objects.requireNonNull(key, "键不能为空");
        return map.remove(key);
    }

    @Override
    public String toString() {
        return map.toString();
    }

    static class LryLinkedHashMap<K,V> extends LinkedHashMap<K,V>{
        /**
         * 最大容量
         */
        private int capacity;

        public LryLinkedHashMap(int capacity){
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        /**
         * 当容量达到 capacity 时, 移除头节点, 所以必须每次get()后将该节点移动到链表的末尾,
         * 这样链表的头节点才是最近最少使用的, 当容量不足时就可以删除它
         * @param eldest 最近最少使用的节点
         * @return boolean
         */
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }
}
