package com.sym.structure.heap.impl;

import com.sym.structure.heap.IHeap;
import java.util.Comparator;
import java.util.Objects;

/**
 * 二叉堆, 逻辑上属于完全二叉树结构, 所以可以使用数组作为它的底层存储结构.
 * 二叉堆一般可以用来解决 TopK 问题, 学习它记住两个点, 假设一个堆元素的下标为i：
 * 1.其父节点下标, floor((i-1)/2)
 * 2.其左子节点下标, 2i+1(如果有); 其右子节点下标, 2i+2(如果有)
 *
 * @author shenyanming
 * Created on 2020/7/29 15:12
 */
@SuppressWarnings("unchecked")
public class BinaryHeap<E> implements IHeap<E> {

    /**
     * 默认加载因子、默认容量
     */
    private final static float DEFAULT_LOAD_FACTORY = 0.75f;
    private final static int DEFAULT_CAPACITY = 16;

    /**
     * 实际元素数量
     */
    private int size;

    /**
     * 底层存储结构
     */
    private E[] elements;

    /**
     * 比较器
     */
    private Comparator<E> comparator;

    /**
     * 扩容临界点
     */
    private int threshold;

    public BinaryHeap() {
        this(DEFAULT_CAPACITY, null);
    }

    public BinaryHeap(int capacity) {
        this(capacity, null);
    }

    public BinaryHeap(int capacity, Comparator<E> comparator) {
        this.size = 0;
        this.elements = (E[]) new Object[capacity];
        this.comparator = comparator;
        this.threshold = (int) (capacity * DEFAULT_LOAD_FACTORY);
    }

    public BinaryHeap(E[] elements) {
        // 拷贝原数组
        this.elements = (E[]) new Object[Objects.requireNonNull(elements).length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
        // 计算容量
        this.size = this.elements.length;
        // 在下一次add()的时候, 扩容
        this.threshold = (int) (size * DEFAULT_LOAD_FACTORY);
        // 修复堆的性质
        heapify();
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
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        checkNotNull(element);
        if (size == 0) {
            elements[0] = element;
        } else {
            if (size > threshold) {
                // 扩容
                resize();
            }
            // 将新元素放到数组末尾
            elements[size] = element;
            // 上滤
            siftUp(size);
        }
        size++;
    }

    @Override
    public E get() {
        // 返回堆顶元素
        return checkNotEmpty().elements[0];
    }

    @Override
    public E remove() {
        // 堆顶元素
        E top = checkNotEmpty().elements[0];
        // 用数组最后一个元素覆盖掉堆顶元素, 再删除数组最后一个元素
        int lastIndex = --size;
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        // 下滤
        siftDown(0);
        // 返回原堆顶元素
        return top;
    }

    @Override
    public E replace(E element) {
        if (size == 0) {
            // 原堆顶为null, 直接赋值
            elements[size++] = element;
            return null;
        }
        // 当前堆顶元素
        E top = elements[0];
        // 新元素直接覆盖堆顶元素
        elements[0] = element;
        // 下滤
        siftDown(0);
        return top;
    }

    /**
     * 二叉堆上滤.
     * 以最大堆为例, 依次与父元素比较, 若比父元素大, 则与父元素互换位置, 再继续向上层父元素比较.
     *
     * @param index 准备上滤的元素下标
     */
    private void siftUp(int index) {
        // 因为在上滤的过程中, index所在的元素, 不需要一直重新赋值到数组内,
        // 只需要在最后一次比较中赋值进去. 因此, 在循环之前就取到它的值.
        E el = elements[index];
        while (index > 0) {
            // 求得父元素的索引, 父元素的值
            int pIndex = (index - 1) >> 1;
            E parent = elements[pIndex];
            // 与父元素比较
            if (compare(el, parent) <= 0) {
                // 没有比父元素大, 那就直接退出循环
                break;
            }
            // 比父元素大, 就让父元素下来index这个位置, 然后元素el先不用放到pIndex位置, 继续与上层父元素比较
            elements[index] = parent;
            index = pIndex;
        }
        // 跳出循环后, 要么比较到堆顶了, 要么在已经比父元素小, 则直接赋值为index位置
        elements[index] = el;
    }

    /**
     * 二叉堆下滤.
     * 以最大堆为例, 依次与最大子元素比较, 若子元素大, 则与子元素互换位置, 再继续向下层子元素比较.
     *
     * @param index 准备下滤的元素下标
     */
    private void siftDown(int index) {
        // 因为在下滤的过程中, index所在的元素, 不需要一直重新赋值到数组内,
        // 只需要在最后一次比较中赋值进去. 因此, 在循环之前就取到它的值.
        E el = elements[index];
        for (; ; ) {
            // 获取最大子元素下标
            int cIndex = maxChildIndex(index);
            if (cIndex == -1) {
                // 没有子元素就跳出循环
                break;
            }
            // 与最大子元素比较大小
            if (compare(el, elements[cIndex]) >= 0) {
                // 如果比最大子元素还大, 就不用再下滤了, 跳出循环
                break;
            }
            // 比最大子元素还小, 则与最大子元素互换位置, 然后继续下滤
            elements[index] = elements[cIndex];
            index = cIndex;
        }
        elements[index] = el;

        // 也可以换一种考虑方式：
        // 仅对有子元素的父元素做下滤, 而在二叉堆中, 第一个叶子元素的索引 == 非叶子元素的数量, 即
        // int half = size >> 1;
        // 只要保证 index < half, 那么保证index位置是非叶子元素, 就变成只需要判断是否有右子元素,
        // 若没有, 则直接与左子元素比较; 若有, 则从左右子元素中选取最大的一个与其比较.
    }

    /**
     * 给定一个乱序的对象数组, 对其进行批量建堆, 做法：
     * 1.先将乱序数组赋值给底层存储数组;
     * 2.对数组选择上滤或下滤处理
     * 对于第2步, 有两种处理方式；
     * - 自上而下的上滤, 每进行一次上滤, 上面就有一小部分满足堆的性质(靠想象), 所以经过n次上滤以后, 整个数组就满足堆的性质;
     * - 自下而上的下滤, 每进行一次下滤, 下面就由一小部分满足堆的性质(靠想象), 所以经过n次下滤以后, 整个数组就满足堆的性质;
     */
    private void heapify() {
        // 自上而下的上滤, 因为堆顶元素没有父元素与其比较, 所以直接是从数组[1]开始上滤,
        // 这个操作的时间复杂度为O(logn)
        // for(int i = 1; i < size; i++){
        //     siftUp(i);
        // }

        // 自下而上的下滤, 如果元素没有子元素, 那么就必要进行下滤, 所以循环是从数组非叶子元素开始比较.
        // 根据完全二叉树的性质, 若节点总数量为n, 则非叶子节点的数量为floor(n/2)
        // 这个操作的时间复杂度为O(n), 所以在批量建堆的时候, 一般我们采用自下而上的下滤.
        for (int i = (size >> 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 获取指定位置的元素的最大子元素
     */
    private int maxChildIndex(int index) {
        // 左子元素索引
        int left = (index << 1) + 1;
        if (left < size) {
            // 左子元素存在, 判断右子元素存在
            int right = left + 1;
            if (right < size) {
                // 右子元素也存在
                return compare((E) elements[left], (E) elements[right]) > 0 ? left : right;
            } else {
                return left;
            }
        } else {
            // 左右子元素都不存在
            return -1;
        }
    }

    /**
     * 数组扩容, ensureCapacity
     */
    private void resize() {
        // 旧数组
        E[] old = elements;
        // 新数组容量
        E[] newElements = (E[]) new Object[elements.length << 1];
        // 复制旧数组数组
        System.arraycopy(old, 0, newElements, 0, old.length);
        // 更改引用
        elements = newElements;
        // 重新计算 threshold
        threshold = (int) (newElements.length * DEFAULT_LOAD_FACTORY);
    }

    @Override
    public Object printRoot() {
        return size == 0 ? null : 0;
    }

    @Override
    public Object printLeft(Object node) {
        int leftChildIndex = ((int) node << 1) + 1;
        return leftChildIndex < size ? leftChildIndex : null;
    }

    @Override
    public Object printRight(Object node) {
        int rightChildIndex = ((int) node << 1) + 2;
        return rightChildIndex < size ? rightChildIndex : null;
    }

    @Override
    public Object printNodeString(Object node) {
        return elements[(int) node];
    }

    /**
     * 元素大小比较
     *
     * @param e1 元素a
     * @param e2 元素b
     * @return 大于0, a>b; 等于0, a=b; 小于0, a<b;
     */
    private int compare(E e1, E e2) {
        return comparator != null ? comparator.compare(e1, e2) : ((Comparable) e1).compareTo(e2);
    }

    private void checkNotNull(E element) {
        if (element == null) {
            throw new NullPointerException("element is null");
        }
    }

    private BinaryHeap<E> checkNotEmpty() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("heap is null");
        }
        return this;
    }
}
