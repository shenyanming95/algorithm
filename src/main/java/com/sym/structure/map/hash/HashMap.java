package com.sym.structure.map.hash;

import com.sym.structure.map.IMap;

import java.util.Objects;

/**
 * 通过数组+红黑树实现的哈希表. 通过hashcode()求得key在数组中的位置, 若发生hash冲突,
 * 通过红黑树来关联.但由于红黑树要求其节点可以比较, 所以比较大的难题在于怎么将通用的Key换
 * 算可比较的形式.
 * <p>
 * put()若发生hash冲突：
 * - 比较equals(), 是同一个对象则覆盖; 若不是, 则需要查找;
 * - 是否实现{@link Comparable}, 通过比较结果决定往红黑树左边还是右边查找;
 * - 如果Compare结果还为0, 只能扫描左右子树, 通过equals()判断是否存在;
 * - 若存在则覆盖, 若不存在则put.
 * <p>
 * get()查找逻辑:
 * - 比较hashcode();
 * - 比较equals(), 相等的说明找到了, 方法直接返回;
 * - 是否实现{@link Comparable}, 通过比较结果决定往左还是往右;
 * - 如果Compare结果还为0, 扫描右子树;
 * - 左子树扫描不到, 扫描左子树;
 * - 若能扫描到则返回, 还是扫描不到返回null.
 * <p>
 * 在扩容时, 节点的索引有且仅有两种情况：
 * - 保持不变;
 * - index = index + 旧容量
 *
 * @author shenyanming
 * @date 2020/7/19 12:36.
 */
public class HashMap<K, V> implements IMap<K, V> {

    /**
     * {@link HashMap}的内部类, 作为红黑树的节点
     *
     * @param <K> key
     * @param <V> value
     */
    private static class Entry<K, V> {
        int hash;
        K key;
        V value;
        boolean color = RED;
        Entry<K, V> left;
        Entry<K, V> right;
        Entry<K, V> parent;

        public Entry(K key, V value, int hash, Entry<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hash = hash;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Entry<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            } else if (isRightChild()) {
                return parent.left;
            }
            return null;
        }

        @Override
        public String toString() {
            return "Entry{key=" + key + ", value=" + value + "}";
        }
    }

    /* static fields */

    /**
     * true-表示红黑树黑色节点, false-表示红黑树红色节点
     */
    private final static boolean RED = false;
    private final static boolean BLACK = true;

    /**
     * 默认容量
     */
    private final static int DEFAULT_CAPACITY = 1 >> 4;

    /**
     * 最大容量
     */
    private final static int MAX_CAPACITY = Integer.MAX_VALUE;

    /**
     * 默认加载因子
     */
    private final static float DEFAULT_LOAD_FACTORY = 0.75f;

    /* instance filed */

    private int capacity;
    private int size;
    private Entry<K, V>[] entryTable;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("invalid capacity");
        }
        this.capacity = tableSizeFor(capacity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        initEntryTable();
        // 对key取hashcode, 然后找到桶下标
        int index = index(key);
        // 获取Entry数组的元素
        Entry<K, V> entry = entryTable[index];
        // 说明此位置上还没有节点占用, 它就作为新节点
        if (entry == null) {
            size++;
            entry = createEntry(key, value, null);
            entryTable[index] = entry;
            // 添加新Entry节点后置处理, 维护红黑树的平衡
            afterAdd(entry);
            return null;
        }
        // 如果代码到这里, 说明EntryTable指定位置index已经有节点了, 即发生了hash冲突.
        // 解决hash冲突的方法之一就是链地址法, 但是这里不采用链表, 而是直接使用红黑树来实现.
        Entry<K, V> par = entry;
        Entry<K, V> cur = entry;
        int cmp = 0;
        int h1 = hash(key);
        Entry<K, V> result = null;
        boolean searched = false;
        // 通过比较大小, 确定新Entry需要添加的位置.
        do {
            // 每次循环之前, 先确定当前处理Entry的父Entry
            par = cur;
            K k2 = cur.key;
            int h2 = cur.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(key, k2)) {
                cmp = 0;
            } else if ((key != null && k2 != null)
                    && key instanceof Comparable
                    && key.getClass() == k2.getClass()
                    && (cmp = ((Comparable) key).compareTo(k2)) != 0) {
                // 这边只是了能比较出结果, 将值赋给cmp.
                // 如果比较得到的值不为0, 说明可以确定k1和k2的大小; 但是如果比较结果还等于0, 就得继续判断
            } else if (searched) {
                // 已经扫描过了, 还是判断不出来k1和k2的大小, 那只能通过内存来判断
                cmp = System.identityHashCode(key) - System.identityHashCode(k2);
            } else {
                // 因为能比较的手段都比较完了, 还是没办法判断出来k1和k2的比较结果谁大谁小, 所以只能通过扫描,
                // 去判断k1到底存不存在与当前的hashMap中, 如果存在那就对其覆盖, 不存在只能继续通过内存比较.
                if (entry.left != null && (result = findEntry(entry.left, key)) != null
                        || entry.right != null && (result = findEntry(entry.right, key)) != null) {
                    // 已经存在这个Key, 直接覆盖它
                    cur = result;
                    cmp = 0;
                } else {
                    // 扫描也么有找到这个Key
                    searched = true;
                    cmp = System.identityHashCode(key) - System.identityHashCode(k2);
                }
            }
            // 根据比较结果, 来决定是要向左找, 还是向右找.
            if (cmp > 0) {
                cur = cur.right;
            } else if (cmp < 0) {
                cur = cur.left;
            } else {
                V oldValue = cur.value;
                cur.key = key;
                cur.value = value;
                cur.hash = h1;
                return oldValue;
            }
        } while (cur != null);
        // 循环已经终止了, 并且方法也仍未返回, 则说明Key对应的Entry并不存在于当前Map中.
        // 为其创建一个新的Entry节点.
        Entry<K, V> newEntry = createEntry(key, value, par);
        if (cmp > 0) {
            par.right = newEntry;
        } else {
            par.left = newEntry;
        }
        size++;
        // 添加新Entry节点后置处理, 维护红黑树的平衡
        afterAdd(entry);
        return null;
    }

    @Override
    public V get(K key) {
        Entry<K, V> entry = findEntry(key);
        return entry == null ? null : entry.value;
    }

    @Override
    public V remove(K key) {
        return remove(findEntry(key));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }
        for (int i = 0, len = entryTable.length; i < len; i++) {
            entryTable[i] = null;
        }
    }

    @Override
    public boolean containKey(K key) {
        return findEntry(key) != null;
    }

    @Override
    public boolean containValue(V value) {
        return false;
    }

    /**
     * 创建一个新的Entry节点
     *
     * @param key    键
     * @param value  值
     * @param parent 父节点
     */
    protected Entry<K, V> createEntry(K key, V value, Entry<K, V> parent) {
        return new Entry<>(key, value, hash(key), parent);
    }

    /**
     * 添加新的Entry节点后, 需要对其进行红黑树的平衡处理
     *
     * @param newEntry 新添加的Entry节点
     */
    private void afterAdd(Entry<K, V> newEntry) {

    }

    /**
     * 寻找该key在此HashMap中对应的Entry
     *
     * @param key 键
     * @return Entry节点
     */
    private Entry<K, V> findEntry(K key) {
        // 先通过hashcode确定在EntryTable的位置,
        Entry<K, V> entry = entryTable[index(key)];
        // 若为空, 说明根本就不存在; 如果存在, 还要判断它是不是对应要找的
        // Entry, 因为这里通过链地址法(实际是红黑树)来关联hash冲突的key.
        return entry == null ? null : findEntry(entry, key);
    }

    /**
     * 真正找Entry节点的逻辑：
     * 1.比较hashcode, 能确定大小关系, 则向左或向右找;
     * 2.hashcode相同, 执行equals(), 等于true则直接返回该节点
     * 3.若equals()返回false, 则判断能否转成Compare比较, 能确定大小关系, 则向左或向右找;
     * 4.若Compare比较等于0或者不能转为Compare比较, 则扫描, 即递归调用;
     * 5.若能扫描到, 则直接返回, 否则返回null, 表示不存在此Entry.
     *
     * @param entry 红黑树的根节点
     * @param key   待寻找的键
     * @return 键对应的Entry
     */
    @SuppressWarnings("unchecked")
    private Entry<K, V> findEntry(Entry<K, V> entry, K key) {
        int h1 = hash(key);
        Entry<K, V> result = null;
        int cmp = 0;
        while (entry != null) {
            K k2 = entry.key;
            int h2 = entry.hash;
            if (h1 > h2) {
                entry = entry.right;
            } else if (h1 < h2) {
                entry = entry.left;
            } else if (Objects.equals(key, k2)) { // 代码能走到这边, 说明k1和k2的hashcode相等, 但是还不能确定它们是不是同一个对象
                // 规定它们的equals()方法返回true, 是同一个对象
                return entry;
            } else if ((key != null && k2 != null) // 代码走到这边, 说明k1和k2的hashcode相等, 但是equals()返回false, 说明不是同一个对象, 因此要继续判断出它们谁大谁小, 才能确定向右还是向左走.
                    && key instanceof Comparable
                    && key.getClass() == k2.getClass()
                    && (cmp = ((Comparable) key).compareTo(k2)) != 0) {
                entry = cmp > 0 ? entry.right : entry.left;
                // 如果比较结果不为0, 说明能确定出k1和k2的大小关系, 就跳出if-else if语句块
            } else if (entry.right != null && (result = findEntry(entry.right, key)) != null) { // 代码走到这边, 说明k1和k2的hashcode相等, compare比较也相等, 且两个还不是同一个对象, 此时只能依靠扫描了, 先扫描右边
                return result;
            } else { // 代码走到这边, 说明k1和k2的hashcode相等, compare比较也相等, 且两个还不是同一个对象, 且Entry的右半部分已经扫描完毕了, 那只能扫描左边了
                entry = entry.left;
            }
        }
        // 啥都没找到, 返回null
        return null;
    }

    /**
     * 删除一个Entry
     * @param entry 待删除的Entry
     * @return 该Entry的值
     */
    private V remove(Entry<K, V> entry){
        return null;
    }

    @SuppressWarnings("unchecked")
    private void initEntryTable() {
        if (entryTable == null) {
            entryTable = new Entry[capacity];
        }
    }

    /**
     * 保证容量一定是2的幂次方
     *
     * @param cap 原始容量
     * @return 修改后的容量(2的幂次方)
     */
    private int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAX_CAPACITY) ? MAX_CAPACITY : n + 1;
    }

    /**
     * 通过对key取hash值, 然后定位它位于Entry数组哪个位置上
     *
     * @param key 键
     * @return 数组下标
     */
    private int index(K key) {
        // 这边是设计 capacity 为2的幂次方, 这样每当它减一后,
        // 它的二进制位类似: 0111111111..., 从而进行与运算时,
        // 把决定权交给Key自己, 也就是取决于Key自身的hashcode
        return hash(key) & (capacity - 1);
    }

    /**
     * 对Key取哈希值
     *
     * @param key 键
     * @return hashcode
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        // 参考 jdk hashMap 实现, 将原key的hashcode
        // 的低16位和高16位取异或, 充分使用到各个位上面的数,
        // 也是为了避免hash冲突
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }
}
