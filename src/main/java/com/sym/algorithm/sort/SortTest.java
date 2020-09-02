package com.sym.algorithm.sort;

import com.sym.algorithm.sort.impl.BubbleSort;
import com.sym.algorithm.sort.impl.HeapSort;
import com.sym.algorithm.sort.impl.InsertionSort;
import com.sym.algorithm.sort.impl.QuickSort;
import com.sym.algorithm.sort.impl.SelectionSort;
import com.sym.util.SymArrayUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * 排序算法测试
 *
 * @author shenyanming
 * Created on 2020/9/2 11:15
 */
public class SortTest {

    private int[] array;

    @Before
    public void init() {
        int length = 10;
        array = SymArrayUtil.getRandomArray(length);
    }

    @Test
    public void totalTest() {
        // 冒泡排序
        bubbleSort();
        // 选择排序
        selectionSort();
        // 堆排序
        heapSort();
        // 插入排序
        insertionSort();
        // 快速排序
        quickSort();
    }

    /**
     * 冒泡排序
     */
    @Test
    public void bubbleSort() {
        doSort(new BubbleSort(copy()));
    }

    /**
     * 选择排序
     */
    @Test
    public void selectionSort() {
        doSort(new SelectionSort(copy()));
    }

    /**
     * 堆排序
     */
    @Test
    public void heapSort() {
        doSort(new HeapSort(copy()));
    }

    /**
     * 插入排序
     */
    @Test
    public void insertionSort(){
        doSort(new InsertionSort(copy()));
    }

    /**
     * 快速排序
     */
    @Test
    public void quickSort(){
        doSort(new QuickSort(copy()));
    }

    // 执行排序
    private void doSort(ISort sort) {
        sort.sort();
        System.out.println(sort);
    }

    //  拷贝数组
    private int[] copy() {
        return Arrays.copyOf(array, array.length);
    }
}
