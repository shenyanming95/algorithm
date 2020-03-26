package com.sym.dataStructure.list.arrayList;

import com.sym.dataStructure.list.ISymList;

import java.util.Objects;

/**
 * 基于数组的单向列表。
 * 线性表之顺序表，顺序表是一块连续存储地址, 物理上和逻辑上都是相邻的;
 * 顺序表是以数组实现的, 查询快, 但是插入和删除慢, 需要修改大量元素的位置
 *
 * Created by shenym on 2019/9/19.
 */
public class SingleArrayList<T> implements ISymList<T> {

    // 顺序表是连续的存储单元，底层可以用数组来表示
    private Object[] elementData;

    // 顺序表的当前元素个数
    private int curLen;

    // 无参构造方法，默认新建一个大小为10的顺序表
    public SingleArrayList() {
        elementData = new Object[10];
        curLen = 0;// 因为顺序表没有任何元素，所以curLen为0
    }

    // 有参构造方法，指定大小的顺序表
    public SingleArrayList(int size) {
        if (size > 0) {
            elementData = new Object[size];
            curLen = 0;
        } else {
            throw new IllegalArgumentException("非法的顺序表大小");
        }
    }

    @Override
    public void add(T o) {
        // 先判顺序表是否已达到最大值
        if (curLen == elementData.length)
            throw new RuntimeException("顺序表已满");
        elementData[curLen++] = o;
    }

    @Override
    public void add(int index, T o) {
        // 先判顺序表是否已达到最大值
        if (curLen == elementData.length)
            throw new IllegalArgumentException("顺序表已满");
        // 判断i是否合法
        if (index < 0 || index > curLen) {
            throw new IllegalArgumentException("插入位置不合法");
        }
        // 像数组后移的操作，可以借用数据拷贝的方式来实现
        // 要将新元素插入到原数组的index位置上，则原index位置后的元素都要向后移动一位，相当于将原数组的index位置上的元素拷贝到原数组的index+1位置上
		System.arraycopy(elementData, index, elementData, index + 1, curLen - index);
        elementData[index] = o;
        curLen++;
    }

    @Override
    public void update(int index, T o) {
        // 为了校验参数的合法性
        get(index);
        elementData[index] = o;
    }

    @Override
    public void delete(int index) {
        if( isEmpty() ) throw new IllegalArgumentException("顺序表为空");
        if (index < 0 || index > curLen - 1) throw new IllegalArgumentException("删除位置不合法");
		// 像数组前移的操作，可以借用数组拷贝的方式来实现
        System.arraycopy(elementData, index + 1, elementData, index, curLen - 1 - index);
        curLen--;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if( isEmpty() ) throw new IllegalArgumentException("顺序表为空");
        if( index < 0 || index >= curLen ) throw new IllegalArgumentException("下标位置不合法，正确区间为[0,"+(curLen-1)+"]");
        return (T)elementData[index];
    }

    @Override
    public int indexOf(T o) {
        if( isEmpty() ) return -1;
        for( int i = 0 ; i < curLen ; i++ ){
            if(Objects.equals(elementData[i],o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return curLen;
    }

    @Override
    public boolean isEmpty() {
        return curLen == 0;
    }

    @Override
    public void display() {
        System.out.print("[ ");
        for (int i = 0; i < curLen; i++) {
            System.out.printf("%s  ",elementData[i]);
        }
        System.out.println("]");
    }
}
