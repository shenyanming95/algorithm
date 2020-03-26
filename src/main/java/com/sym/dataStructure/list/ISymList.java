package com.sym.dataStructure.list;

/**
 * 列表接口
 *
 * Created by shenym on 2019/9/19.
 */
public interface ISymList<T> {

    /**
     * 在列表末尾新增一个元素
     *
     * @param t 元素
     */
    void add(T t);

    /**
     * 在列表指定位置新增一个元素
     *
     * @param index 指定下标
     * @param t     元素
     */
    void add(int index, T t);

    /**
     * 修改列表指定位置上的数据
     *
     * @param index 下标
     * @param t     元素
     */
    void update(int index, T t);

    /**
     * 删除指定位置上的元素
     *
     * @param index 指定下标
     */
    void delete(int index);

    /**
     * 获取指定位置上的元素
     *
     * @param index 下标
     * @return T
     */
    T get(int index);

    /**
     * 获取指定元素的下标位置
     *
     * @param t 元素
     * @return 下标
     */
    int indexOf(T t);

    /**
     * 获取列表的长度
     *
     * @return 长度
     */
    int size();

    /**
     * 判断列表是否为空
     *
     * @return true-为空
     */
    boolean isEmpty();

    /**
     * 打印列表的信息
     */
    void display();

}
