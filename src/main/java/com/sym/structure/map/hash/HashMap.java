package com.sym.structure.map.hash;

import com.sym.structure.map.IMap;
import com.sym.structure.map.linked.LinkedHashMap;
import com.sym.structure.queue.IQueue;
import com.sym.structure.queue.linked.LinkedQueue;
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

        public void ResetTreeInfo() {
            this.parent = null;
            this.left = null;
            this.right = null;
            this.color = RED;
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
    private int threshold;
    private Entry<K, V>[] entryTable;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("invalid capacity");
        }
        this.capacity = tableSizeFor(capacity);
        this.threshold = (int) (capacity * DEFAULT_LOAD_FACTORY);
    }

    @Override
    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        // 延迟加载 + 扩容判断
        initEntryTableOrResize();
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
        if (isEmpty()) {
            return false;
        }
        // 根据值来确定Entry, 需要对每个Entry进行判断, 其实际就是对Entry树的遍历.
        // 通常使用采用层序遍历的方式
        IQueue<Entry<K, V>> queue = new LinkedQueue<>();
        for (Entry<K, V> entry : entryTable) {
            if (entry == null) {
                continue;
            }
            // 当前EntryTable的根节点入队
            queue.offer(entry);
            while (!queue.isEmpty()) {
                Entry<K, V> kvEntry = queue.poll();
                if (Objects.equals(kvEntry.value, value)) {
                    return true;
                }
                if (kvEntry.left != null) {
                    queue.offer(kvEntry.left);
                }
                if (kvEntry.right != null) {
                    queue.offer(kvEntry.right);
                }
            }
        }
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
     * 用于{@link LinkedHashMap}, 维持链表关系
     *
     * @param plannedRemovalEntry 计划删除Entry
     * @param realRemovalEntry    真正删除Entry
     */
    protected void EntryAfterRemoval(Entry<K, V> plannedRemovalEntry, Entry<K, V> realRemovalEntry) {

    }

    /**
     * 添加新的Entry节点后, 需要对其进行红黑树的平衡处理
     *
     * @param insertionEntry 新添加的Entry节点
     */
    private void afterAdd(Entry<K, V> insertionEntry) {
        Entry<K, V> parent = insertionEntry.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            black(insertionEntry);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;

        // 叔父节点
        Entry<K, V> uncle = parent.sibling();
        // 祖父节点
        Entry<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            afterAdd(grand);
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftChild()) { // L
            if (insertionEntry.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(insertionEntry);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (insertionEntry.isLeftChild()) { // RL
                black(insertionEntry);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    /**
     * 删除Entry节点后, 需要对其进行红黑树的平衡处理
     *
     * @param removalEntry 真正被删除的Entry节点
     */
    private void afterRemove(Entry<K, V> removalEntry) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(removalEntry)) {
            black(removalEntry);
            return;
        }

        Entry<K, V> parent = removalEntry.parent;
        if (parent == null) return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || removalEntry.isLeftChild();
        Entry<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    private void rotateLeft(Entry<K, V> grand) {
        Entry<K, V> parent = grand.right;
        Entry<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void rotateRight(Entry<K, V> grand) {
        Entry<K, V> parent = grand.left;
        Entry<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Entry<K, V> grand, Entry<K, V> parent, Entry<K, V> child) {
        // 让parent称为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // grand是root节点
            entryTable[index(grand.key)] = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;
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
     *
     * @param entry 待删除的Entry
     * @return 该Entry的值
     */
    private V remove(Entry<K, V> entry) {
        if (entry == null) {
            return null;
        }
        size--;
        // 为了子类LinkedHashMap的重新建立关联关系使用
        Entry<K, V> EntryPlanToRemove = entry;
        // 待返回的旧值
        V oldValue = entry.value;

        if (entry.hasTwoChildren()) {
            // 度为2的节点, 寻找它的前驱节点或后继节点的值, 替换它的值
            Entry<K, V> successor = successor(entry);
            entry.key = successor.key;
            entry.value = successor.value;
            entry.hash = successor.hash;
            // 实际删除的的是前驱节点或后继节点
            entry = successor;
        }

        // 处理了度为2的Entry, 剩下的只有3种情况：
        // 度为1的Entry、度为0的Entry且是根节点、度为0的Entry的普通节点
        Entry<K, V> replaceElement = entry.left != null ? entry.left : entry.right;
        int index = index(entry.key);
        if (replaceElement != null) { //度为1
            replaceElement.parent = entry.parent;
            if (entry.parent == null) {
                // entry自身就是根节点, 然后只有一个Entry子节点
                entryTable[index] = replaceElement;
            } else if (entry.isLeftChild()) {
                entry.parent.left = replaceElement;
            } else {
                entry.parent.right = replaceElement;
            }
        } else if (entry.parent == null) { // 度为0, 但是根节点
            entryTable[index] = null;
        } else { // 度为0, 但不是根节点
            if (entry.isLeftChild()) {
                entry.parent.left = null;
            } else {
                entry.parent.right = null;
            }
        }
        // reBalance
        afterRemove(entry);
        // for LinkedHashMap
        EntryAfterRemoval(EntryPlanToRemove, entry);

        return oldValue;
    }

    /**
     * 获取Entry节点的后继节点
     *
     * @param entry 指定Entry
     * @return Entry后继节点
     */
    private Entry<K, V> successor(Entry<K, V> entry) {
        // 如果右子树不为空, 寻找右子树的最左节点
        Entry<K, V> cur = entry.right;
        if (cur != null) {
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur;
        }
        // 如果右子树为空, 就得寻找最大的父节点, 使其能位于父节点的左子树部分
        cur = entry;
        while (cur.isRightChild()) {
            cur = cur.parent;
        }
        return cur.parent;
    }

    /**
     * 动态扩容
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        if (size <= threshold) {
            // 未达到扩容条件
            return;
        }
        // 新的EntryTable扩容为原先的2倍
        Entry<K, V>[] oldEntryTable = entryTable;
        entryTable = new Entry[capacity << 1];

        IQueue<Entry<K, V>> queue = new LinkedQueue<>();
        for (Entry<K, V> entry : oldEntryTable) {
            if (entry == null) {
                continue;
            }
            queue.offer(entry);
            while (!queue.isEmpty()) {
                Entry<K, V> kvEntry = queue.poll();
                if (kvEntry.left != null) {
                    queue.offer(kvEntry.left);
                }
                if (kvEntry.right != null) {
                    queue.offer(kvEntry.right);
                }
                // 将原先的Entry的左右子节点入队后, 对该Entry进行移动
                moveEntry(kvEntry);
            }
        }
    }

    /**
     * 扩容时移动Entry
     *
     * @param entry
     */
    @SuppressWarnings("unchecked")
    private void moveEntry(Entry<K, V> entry) {
        // 重置Entry的红黑树信息
        entry.ResetTreeInfo();

        // 判断在EntryTable扩容后, 该entry的新位置,
        // 如果该位置上的Entry为null, 说明当前这个Entry就是根节点, 直接赋值
        int index = index(entry.key);
        Entry<K, V> root = entryTable[index];
        if (root == null) {
            root = entry;
            entryTable[index] = root;
            // reBalance
            afterAdd(root);
            return;
        }
        // 如果该位置上已经有元素, 其实就是发生了hash冲突了, 采用链地址法
        // 将其移动到红黑树上.
        Entry<K, V> parent = root;
        Entry<K, V> node = root;
        int cmp = 0;
        K k1 = entry.key;
        int h1 = entry.hash;
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                // 只为了获取比较结果
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            // 根据比较结果, 决定向左走, 还是向右走
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
        } while (node != null);

        // 将新结点重新关联成红黑树
        entry.parent = parent;
        if (cmp > 0) {
            parent.right = entry;
        } else {
            parent.left = entry;
        }

        // reBalance
        afterAdd(entry);
    }

    /**
     * 初始化EntryTable 或者 扩容EntryTable
     */
    @SuppressWarnings("unchecked")
    private void initEntryTableOrResize() {
        if (entryTable == null) {
            entryTable = new Entry[capacity];
            return;
        }
        resize();
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

    private Entry<K, V> color(Entry<K, V> entry, boolean color) {
        if (entry == null) return entry;
        entry.color = color;
        return entry;
    }

    private Entry<K, V> red(Entry<K, V> entry) {
        return color(entry, RED);
    }

    private Entry<K, V> black(Entry<K, V> entry) {
        return color(entry, BLACK);
    }

    private boolean colorOf(Entry<K, V> entry) {
        return entry == null ? BLACK : entry.color;
    }

    private boolean isBlack(Entry<K, V> entry) {
        return colorOf(entry) == BLACK;
    }

    private boolean isRed(Entry<K, V> entry) {
        return colorOf(entry) == RED;
    }
}
