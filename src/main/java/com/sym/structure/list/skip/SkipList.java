package com.sym.structure.list.skip;

import java.util.Comparator;
import java.util.Objects;

/**
 * 跳表.
 * 它一个特殊地方就是, 会将节点的next指针, 扩展成next[]数组, 表示每一层.
 * 也可以把它理解成索引, 每次查询的时候, 就会先走最高层来过滤掉不需要判断的
 * 节点, 当前层判断完后, 转到下一层进行判断, 直至最底层, 即next[0].所以,
 * 它有点二分搜索的思想~
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
    @SuppressWarnings("unchecked")
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

        /**
         * 用于初始化普通跳表节点
         *
         * @param key   键
         * @param value 值
         * @param level 层数
         */
        Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            this.nextArray = new Node[level];
        }
    }

    /**
     * 跳表必须指定最高层数, 一方面是效率, 并不是说层数越高, 效率越快, 反而增加向下查询的次数.
     * 另一方面, 跳表的首节点必须指定一个层数,就是这个最高值
     */
    private static final int MAX_LEVEL = 32;

    /**
     * 每次添加一个新跳表节点, 它的层数是通过随机数来定义的
     */
    private static final double RANDOM_RATE = 0.25;

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
     * 添加元素, 有两种情况:
     * 1).元素本来就存在, 直接覆盖;
     * 2).元素不存在, 需要创建节点, 并且重新关联该节点每一层的前驱、后继关系.
     *
     * @param key   键
     * @param value 值
     * @return 旧值, 若不存在为null
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key, "key is null");
        // 用来保存在寻找过程, 切换层时的当前节点
        Node<K, V>[] nodes = nodes(level);
        // 开始寻找新元素适合的位置
        Node<K, V> node = head;
        int cp = -1;
        for (int i = level - 1; i > -1; i--) {
            // 首先在i这一层, 一直寻找, 直到遇到第一个大于等于key的跳表节点
            while (Objects.nonNull(node.nextArray[i]) && (cp = compare(key, node.nextArray[i].key)) > 0) {
                node = node.nextArray[i];
            }
            // 如果cp为0, 说明已经找到指定节点了, 用新值替换旧值.
            if (cp == 0) {
                V old = node.nextArray[i].value;
                node.nextArray[i].value = value;
                return old;
            }
            // 如果代码走到这里, 说明在i这一层, 要么已经到达这一层的空节点, 要么已经找到第一个大于key的跳表节点,
            // 这两种情况都需要转到下一层搜索. 此时, 需要将这个节点记录起来, 因为后续可能需要处理它的关联关系.
            nodes[i] = node;
        }
        // 代码走到这里, 说明已经遍历完所有层, 且新元素确实不存在与当前跳表, 所以需要为其创建一个新的节点, 其中层数随机.
        int newLevel = randomLevel();
        Node<K, V> newNode = new Node<>(key, value, newLevel);
        // 处理关联关系
        for (int i = 0; i < newLevel; i++) {
            // 如果新创建节点的层数大于当前有效level, 则让它和头节点head关联起来
            if (i >= level) {
                head.nextArray[i] = newNode;
                continue;
            }
            // 取出原先遍历过程中记录的node, 将node的后继节点移植到newNode上, 然后newNode作为node新的后继节点
            newNode.nextArray[i] = nodes[i].nextArray[i];
            nodes[i].nextArray[i] = newNode;
        }
        // 修改跳表新的属性
        size++;
        level = Math.max(level, newLevel);
        return null;
    }

    /**
     * 删除元素, 有两种情况：
     * 1).元素本来就不存在, 啥都不用做;
     * 2).元素已存在, 需要将节点移除, 并且调整原先的前驱、后继关系.
     *
     * @param key 键
     * @return 旧值, 若不存在为null
     */
    public V remove(K key) {
        Objects.requireNonNull(key, "key is null");
        if (isEmpty()) {
            return null;
        }
        // 删除节点需要维护原先节点存在的前驱、后继关系, 因此同put()一样, 同样需要一个节点空数组
        Node<K, V>[] nodes = nodes(level);
        // 有可能节点本身就不在跳表中
        boolean isExist = false;
        // 开始遍历
        Node<K, V> node = head;
        int cp = -1;
        for (int i = level - 1; i > -1; i--) {
            // 循环条件：当前层的下一个节点不为null, 并且参数key大于下一个节点的key
            while (Objects.nonNull(node.nextArray[i]) && (cp = compare(key, node.nextArray[i].key)) > 0) {
                node = node.nextArray[i];
            }
            // 如果遍历过程中, 发现有等于0的情况, 那么说明待删除节点是存在的, 将标记置为true
            if (cp == 0 && !isExist) {
                isExist = true;
            }
            // 记录每一层的"拐弯点"
            nodes[i] = node;
        }
        // 外层for循环结束, 说明已经将所有层的跳表都遍历完了
        if (!isExist) {
            // 待删除节点不存在与当前跳表, 直接返回
            return null;
        }
        // 代码能走到这里, 说明待删除节点确实存在于当前跳表, 因此维护它们的前驱、后继关系.
        // 而且nodes[0]的下一个节点肯定是待删除节点, 因为跳表底层就是链表
        Node<K, V> deleteNode = nodes[0].nextArray[0];
        // 处理待删节点的每一层的前驱、后继关系
        for (int i = 0; i < deleteNode.nextArray.length; i++) {
            // 原先：node1 → deleteNode → node2,
            // 改为：node1 → node2, 直接将deleteNode独立出去.
            nodes[i].nextArray[i] = deleteNode.nextArray[i];
        }
        // 调整size和level
        size--;
        int newLevel = level;
        // 有效层数就是要让待删除节点的后继节点不再为null
        while (--newLevel >= 0 && head.nextArray[newLevel] == null) {
            level = newLevel;
        }
        return deleteNode.value;
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
     * 返回跳表元素大小
     *
     * @return 数量
     */
    public int size() {
        return size;
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

    /**
     * 通过随机数, 生成新跳表节点的层数
     *
     * @return 随机层数
     */
    private int randomLevel() {
        int level = 1;
        while (Math.random() < RANDOM_RATE && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    /**
     * 创建一个跳表节点数组
     *
     * @param length 长度
     * @return 节点数组
     */
    @SuppressWarnings("unchecked")
    private Node<K, V>[] nodes(int length) {
        return new Node[length];
    }
}
