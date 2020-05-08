package com.sym.datastructure.list.array;

import com.sym.datastructure.list.IList;

import java.util.Objects;

/**
 * 基于数组的单向列表。
 * 线性表之顺序表，顺序表是一块连续存储地址, 物理上和逻辑上都是相邻的;
 * 顺序表是以数组实现的, 查询快, 但是插入和删除慢, 需要修改大量元素的位置
 * <p>
 *
 * @author ym.shen
 * @date 2019/9/19
 */
public class SymArrayList<T> implements IList<T> {

    /**
     * 顺序表是连续的存储单元，底层可以用数组来表示
     */
    private Object[] elementData;

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
    private int currentSize;

    public SymArrayList() {
        elementData = new Object[10];
        currentSize = 0;
    }

    public SymArrayList(int size) {
        if (size > 0) {
            elementData = new Object[size];
            currentSize = 0;
            needToExpand = (int)(size * factor);
        } else {
            throw new IllegalArgumentException("illegal size");
        }
    }

    @Override
    public void add(T t) {
        if(needExpand()){
            // 如果需要扩容, 先对列表进行扩容
            this.doExpand();
        }
        elementData[currentSize++] = t;
    }

    @Override
    public void add(int index, T t) {
        if(needExpand()){
            // 如果需要扩容, 先对列表进行扩容
            this.doExpand();
        }
        // 判断i是否合法
        if (index < 0 || index > currentSize) {
            throw new IllegalArgumentException("illegal index");
        }
        // 像数组后移的操作，可以借用数据拷贝的方式来实现：
        // 要将新元素插入到原数组的index位置上，则原index位置后的元素都要向后移动一位，相当于将原数组的index位置上的元素拷贝到原数组的index+1位置上
        System.arraycopy(elementData, index, elementData, index + 1, currentSize - index);
        elementData[index] = t;
        currentSize++;
    }

    @Override
    public void update(int index, T t) {
        // 为了校验参数的合法性
        get(index);
        elementData[index] = t;
    }

    @Override
    public void delete(int index) {
        if (isEmpty()) {
            throw new IllegalArgumentException("list is null");
        }
        if (index < 0 || index > currentSize - 1) {
            throw new IllegalArgumentException("illegal index");
        }
        // 像数组前移的操作，可以借用数组拷贝的方式来实现
        System.arraycopy(elementData, index + 1, elementData, index, currentSize - 1 - index);
        currentSize--;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (isEmpty()) {
            throw new IllegalArgumentException("list is null");
        }
        if (index < 0 || index >= currentSize) {
            throw new IllegalArgumentException("illegal index");
        }
        return (T) elementData[index];
    }

    @Override
    public int indexOf(T t) {
        if (isEmpty()) {
            return -1;
        }
        for (int i = 0; i < currentSize; i++) {
            if (Objects.equals(elementData[i], t)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public void display() {
        System.out.print("[ ");
        for (int i = 0; i < currentSize; i++) {
            System.out.printf("%s  ", elementData[i]);
        }
        System.out.println("]");
    }

    /**
     * 判断是否需要扩容
     * @return true-需要扩容
     */
    private boolean needExpand(){
        return currentSize >= needToExpand;
    }

    /**
     * 执行扩容
     */
    private void doExpand(){
        // 原数组容量
        int oldCapacity = elementData.length;
        // 新数组容量, 默认为原数组的两倍
        int newCapacity = oldCapacity * 2;
        Object[] newElementData = new Object[newCapacity];
        // 执行拷贝
        System.arraycopy(elementData, 0, newElementData, 0, oldCapacity);
        // 修改列表原先属性
        elementData = newElementData;
        needToExpand = (int)(newCapacity * factor);
    }
}
