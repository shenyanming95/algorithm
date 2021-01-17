package com.sym.structure.list.skip;

import java.util.Comparator;
import java.util.Objects;

/**
 * 跳表.
 *
 * @param <K> 键, 必须保证可比较性
 * @param <V> 值
 * @author shenyanming
 * Created on 2021/1/16 20:17.
 */
public class SkipList<K, V> {


    /**
     * 跳表节点
     */
    private static class Node<K, V> {

        K key;

        V value;

        /**
         * 这是跳表节点跟普通链表节点最不一样的地方, 因为它是有层次的概念,
         * 即可能存在多个next引用, 所以并不能只用一个引用来指向下一个节点,
         * 而是应该使用数组.
         */
        Node<K, V>[] nextArray;

        /**
         * 用于初始化跳表头节点
         */
        Node() {
            this.nextArray = new Node[MAX_LEVEL];
        }
    }

    /**
     * 跳表必须指定最高层数, 一方面是效率, 并不是说层数越高, 效率越快, 反而增加向下查询的次数.
     * 另一方面, 跳表的首节点必须指定一个层数, 最好就是这个最高值
     */
    private static final int MAX_LEVEL = 32;

    /**
     * 当前跳表的有效层数
     */
    private int level;

    /**
     * 当前跳表的元素数量
     */
    private int size;

    /**
     * 跳表头节点, 不存储任何元素, 但必须指定最大层数
     */
    private Node<K, V> head;

    /**
     * 比较器
     */
    private Comparator<K> cp;

    public SkipList() {
        this(null);
    }

    public SkipList(Comparator<K> comparator) {
        this.cp = comparator;
        this.head = new Node<>();
    }

    /**
     * 查询元素是否存在于跳表
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        if (isEmpty()) {
            return null;
        }
        int curLevel = level - 1;
        Node<K, V> curNode = head;
        // 外层循环控制访问跳表的层数
        while (curLevel >= 0) {
            int cp = -1;
            // 内层循环控制每层访问的终点
            while (Objects.nonNull(curNode.nextArray[curLevel]) && (cp = compare(key, curNode.nextArray[curLevel].key)) > 0) {
                // 如果能进到这块代码, 说明这一层还有节点可以访问, 并且参数key是比当前节点的key还大,
                // 那么就转到这一层的下一个节点继续判断
                curNode = curNode.nextArray[curLevel];
            }
            // 跳出内层循环, 要么这一层的节点已经访问完, 或者当前节点的下一个节点的key小于等于参数key.
            // 当key相等的时候, 说明已经找到了, 除此之外的其它情况, 都要到下一层就寻找.
            if (cp == 0) {
                return curNode.nextArray[curLevel].value;
            }
            curLevel--;
        }
        return null;
    }

    /**
     * 添加参数
     *
     * @param key   键
     * @param value 值
     */
    public void put(K key, V value) {

    }

    /**
     * 判断跳表是否为空
     *
     * @return true-为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 比较两个元素
     *
     * @param k1 第一个元素
     * @param k2 第二个元素
     * @return 大于0说明t1>t2, 小于0说明t1<t2, 等于0说明相等
     */
    @SuppressWarnings("unchecked")
    private int compare(K k1, K k2) {
        return Objects.nonNull(cp) ? cp.compare(k1, k2) : ((Comparable) k1).compareTo(k2);
    }

}
