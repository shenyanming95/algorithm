package com.sym.structure.heap.impl;

import com.sym.structure.heap.IHeap;
import com.sym.util.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 二叉堆, 是属于完全二叉树结构, 所以可以使用数组作为它的底层存储结构.
 * 二叉堆一般可以用来解决 TopK 问题.
 *
 * @author shenyanming
 * Created on 2020/7/29 15:12
 */
@SuppressWarnings("unchecked")
public class BinaryHeap<E> implements IHeap<E>, BinaryTreeInfo {

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
    private Object[] elements;

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
        this.elements = new Object[capacity];
        this.comparator = comparator;
        this.threshold = (int) (capacity * DEFAULT_LOAD_FACTORY);
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
        for (int i = 0, len = elements.length; i < len; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        if (size == 0) {
            elements[0] = element;
        } else {
            if (size > threshold) {
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
        return size == 0 ? null : (E) elements[0];
    }

    @Override
    public E remove() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            E e = (E) elements[0];
            elements[0] = null;
            size--;
            return e;
        } else {
            E e = (E) elements[0];
            // 用数组最后一个元素替换掉堆顶元素, 同时删掉最后一个元素
            elements[0] = elements[--size];
            elements[size] = null;
            // 下滤
            siftDown(0);
            return e;
        }
    }

    @Override
    public E replace(E element) {
        if (size == 0) {
            elements[size++] = element;
            return null;
        }
        // 当前堆顶元素
        E e = (E) elements[0];
        // 新元素直接覆盖堆顶元素
        elements[0] = element;
        // 下滤
        siftDown(0);
        return e;
    }

    /**
     * 二叉堆上滤.
     * 以最大堆为例, 依次与父节点比较, 若比父节点大, 则与父节点互换位置.
     *
     * @param index 准备上滤的元素下标
     */
    private void siftUp(int index) {
        for (; ; ) {
            if (index - 1 < 0) {
                break;
            }
            // 算出父节点索引
            int pIndex = (index - 1) / 2;
            // 比较大小
            int result = compare((E) elements[index], (E) elements[pIndex]);
            if (result > 0) {
                // 互换位置
                Object p = elements[pIndex];
                elements[pIndex] = elements[index];
                elements[index] = p;
                // 继续检索
                index = pIndex;
            } else {
                break;
            }
        }
    }

    /**
     * 二叉堆下滤.
     * 以最大堆为例, 依次与最大子节点比较, 若子节点大, 则与子节点互换位置.
     *
     * @param index 准备上滤的元素下标
     */
    private void siftDown(int index) {
        int pIndex = index;
        for (; ; ) {
            // 获取最大子节点下标
            int cIndex = maxChild(pIndex);
            if (cIndex == -1) {
                // 没有子节点, 返回
                break;
            }
            // 与最大子节点比较大小
            int compare = compare((E) elements[pIndex], (E) elements[cIndex]);
            if (compare < 0) {
                // 比子节点小, 跟子节点互换位置
                Object p = elements[pIndex];
                elements[pIndex] = elements[cIndex];
                elements[cIndex] = p;
                // 继续检索
                pIndex = cIndex;
            } else {
                break;
            }
        }
    }

    /**
     * 获取指定位置的元素的最大子节点
     */
    private int maxChild(int index) {
        // 左子节点索引
        int left = (index << 1) + 1;
        if (left < size) {
            // 左子节点存在, 判断右子节点存在
            int right = left + 1;
            if (right < size) {
                // 右子节点也存在
                return compare((E) elements[left], (E) elements[right]) > 0 ? left : right;
            } else {
                return left;
            }
        } else {
            // 左右子节点都不存在
            return -1;
        }
    }

    /**
     * 数组扩容
     */
    private void resize() {
        // 旧数组
        Object[] old = elements;
        // 新数组容量
        Object[] newElements = new Object[elements.length << 1];
        // 复制旧数组数组
        System.arraycopy(old, 0, newElements, 0, old.length);
        // 更改引用
        elements = newElements;
        // 重新计算 threshold
        this.threshold = (int) (newElements.length * DEFAULT_LOAD_FACTORY);
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

    @Override
    public Object printRoot() {
        return size == 0 ? null : 0;
    }

    @Override
    public Object printLeft(Object node) {
        if (node == null) {
            return null;
        }
        int index = (int) node;
        int leftChildIndex = (index << 1) + 1;
        return leftChildIndex <= size - 1 ? leftChildIndex : null;
    }

    @Override
    public Object printRight(Object node) {
        if (node == null) {
            return null;
        }
        int index = (int) node;
        int rightChildIndex = (index << 1) + 2;
        return rightChildIndex <= size - 1 ? rightChildIndex : null;
    }

    @Override
    public Object printNodeString(Object node) {
        if (node == null) {
            return null;
        }
        return elements[(int) node];
    }
}
