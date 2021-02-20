package com.sym.structure.list.array;

import com.sym.structure.list.IList;

import java.util.Objects;

/**
 * 基于数组的单向列表, 与{@link java.util.ArrayList}一样, 顺序表是一块连续存储地址,
 * 物理和逻辑上都是相邻的, 顺序表一般以数组实现的, 查询快但是更新慢, 需要修改大量元素的位置.
 *
 * @author ym.shen
 * @date 2019/9/19
 */
public class ArrayList<T> implements IList<T> {

    /**
     * 顺序表是连续的存储单元，底层可以用数组来表示
     */
    private Object[] elements;

    /**
     * 扩容因子
     */
    private double factor = 0.75;

    /**
     * 扩容临界值
     */
    private int needToExpand;

    /**
     * 顺序表的当前元素个数
     */
    private int size;

    public ArrayList() {
        this(10);
    }

    public ArrayList(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("illegal size");
        }
        this.elements = new Object[size];
        this.size = 0;
        this.needToExpand = (int) (size * factor);
    }

    @Override
    public void add(T t) {
        if (needExpand()) {
            // 如果需要扩容, 先对列表进行扩容
            this.doExpand();
        }
        elements[size++] = t;
    }

    @Override
    public void add(int index, T t) {
        if (needExpand()) {
            // 如果需要扩容, 先对列表进行扩容
            this.doExpand();
        }
        // 判断i是否合法
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("illegal index");
        }
        // 像数组后移的操作，可以借用数据拷贝的方式来实现：
        // 要将新元素插入到原数组的index位置上, 则原index位置后的元素都要向后移动一位,
        // 相当于将原数组的index位置上的元素拷贝到原数组的index+1位置上
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = t;
        size++;
    }

    @Override
    public void update(int index, T t) {
        // 为了校验参数的合法性
        get(index);
        elements[index] = t;
    }

    @Override
    public void delete(int index) {
        if (isEmpty()) {
            throw new IllegalArgumentException("list is null");
        }
        if (index < 0 || index > size - 1) {
            throw new IllegalArgumentException("illegal index");
        }
        // 像数组前移的操作，可以借用数组拷贝的方式来实现
        System.arraycopy(elements, index + 1, elements, index, size - 1 - index);
        size--;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (isEmpty()) {
            throw new IllegalArgumentException("list is null");
        }
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("illegal index");
        }
        return (T) elements[index];
    }

    @Override
    public int indexOf(T t) {
        if (isEmpty()) {
            return -1;
        }
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], t)) {
                return i;
            }
        }
        return -1;
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
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]).append(", ");
        }
        int length = sb.length();
        if (length > 1) {
            sb.delete(length - 2, length);
        }
        return sb.append("]").toString();
    }

    /**
     * 判断是否需要扩容
     *
     * @return true-需要扩容
     */
    private boolean needExpand() {
        return size >= needToExpand;
    }

    /**
     * 执行扩容
     */
    private void doExpand() {
        // 原数组容量
        int oldCapacity = elements.length;
        // 新数组容量, 默认为原数组的两倍
        int newCapacity = oldCapacity * 2;
        Object[] newElementData = new Object[newCapacity];
        // 执行拷贝
        System.arraycopy(elements, 0, newElementData, 0, oldCapacity);
        // 修改列表原先属性
        elements = newElementData;
        needToExpand = (int) (newCapacity * factor);
    }
}
