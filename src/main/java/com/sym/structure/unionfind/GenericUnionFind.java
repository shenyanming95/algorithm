package com.sym.structure.unionfind;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 并查集大部分都是基于数组实现, 而且元素大部分都要求为整数. 这里使用
 * 链表来实现, 并且实现泛型化, 通过{@link Object#equals(Object)}
 * 来比较元素是否相等.
 *
 * @param <T> 元素类型
 * @author shenyanming
 * Created on 2020/11/10 14:02
 */
public class GenericUnionFind<T> {

    private Map<T, Entry<T>> dataMap;

    public GenericUnionFind(Collection<T> collection) {
        Objects.requireNonNull(collection, "collection is null");
        dataMap = new HashMap<>(collection.size());
        collection.forEach(e -> dataMap.put(e, new Entry<>(e)));
    }

    /**
     * 查找元素所在集合的根节点
     *
     * @param element 元素
     * @return 集合根节点
     */
    public T find(T element) {
        Entry<T> entry = findEntry(element);
        return Objects.isNull(entry) ? null : entry.data;
    }

    /**
     * 合并 e1 和 e2 所在的两个集合
     *
     * @param e1 元素1
     * @param e2 元素2
     */
    @SuppressWarnings("all")
    public void union(T e1, T e2) {
        dataMap.computeIfAbsent(e1, (e) -> new Entry<>(e1));
        dataMap.computeIfAbsent(e2, (e) -> new Entry<>(e2));
        // 找出两个集合的根节点
        Entry<T> root1 = findEntry(e1);
        Entry<T> root2 = findEntry(e2);
        // 同一个集合就没必要合并
        if (Objects.equals(root1, root2)) {
            return;
        }
        // 高度低的树并到高度高的树
        if (root1.rank < root2.rank) {
            root1.parent = root2;
        } else if (root1.rank > root2.rank) {
            root2.parent = root1;
        } else {
            root1.parent = root2;
            root2.rank++;
        }
    }

    /**
     * 判断 e1 和 e2 是否同处于一个集合
     *
     * @param e1 元素1
     * @param e2 元素2
     * @return true-同一个集合
     */
    public boolean isSame(T e1, T e2) {
        return Objects.equals(find(e1), find(e2));
    }

    private Entry<T> findEntry(T e) {
        Entry<T> entry = dataMap.get(e);
        if (Objects.isNull(entry)) {
            return null;
        }
        while (!Objects.equals(entry, entry.parent)) {
            // 路径分裂：让每个节点都指向它的祖父节点
            // Entry<T> p = entry.parent;
            // entry.parent = entry.parent.parent;
            // entry = p;

            // 路径减半：每个一个节点指向它的祖父节点
            entry.parent = entry.parent.parent;
            entry = entry.parent;
        }
        return entry;
    }

    /**
     * 并查集元素类
     */
    private static class Entry<T> {
        /**
         * 实际存储元素
         */
        T data;

        /**
         * 父节点引用, 初始化时自己就是自己的父节点
         */
        Entry<T> parent = this;

        /**
         * 存储截止到该元素为止的并查集查询链的高度, 默认为1
         */
        int rank = 1;

        public Entry(T data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry<?> entry = (Entry<?>) o;
            return Objects.equals(data, entry.data);
        }
    }
}
