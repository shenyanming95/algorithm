package com.sym.structure.map.hash;

import com.sym.structure.map.IMap;

/**
 * 通过数组+红黑树实现的哈希表. 通过hashcode()求得key在数组中的位置, 若发生hash冲突,
 * 通过红黑树来关联.但由于红黑树要求其节点可以比较, 所以比较大的难题在于怎么将通用的Key换
 * 算可比较的形式.
 *
 * put()若发生hash冲突：
 * - 比较equals(), 是同一个对象则覆盖; 若不是, 则需要查找;
 * - 是否实现{@link Comparable}, 通过比较结果决定往红黑树左边还是右边查找;
 * - 如果Compare结果还为0, 只能扫描左右子树, 通过equals()判断是否存在;
 * - 若存在则覆盖, 若不存在则put.
 *
 * get()查找逻辑:
 * - 比较hashcode();
 * - 比较equals(), 相等的说明找到了, 方法直接返回;
 * - 是否实现{@link Comparable}, 通过比较结果决定往左还是往右;
 * - 如果Compare结果还为0, 扫描右子树;
 * - 左子树扫描不到, 扫描左子树;
 * - 若能扫描到则返回, 还是扫描不到返回null.
 *
 * 在扩容时, 节点的索引有且仅有两种情况：
 * - 保持不变;
 * - index = index + 旧容量
 *
 * @author shenyanming
 * @date 2020/7/19 12:36.
 */
public class HashMap<K, V> implements IMap<K, V> {

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void remove(K key) {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean containKey(K key) {
        return false;
    }

    @Override
    public boolean containValue(V value) {
        return false;
    }
}
