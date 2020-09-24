package com.sym.algorithm.sort;

import com.sym.algorithm.sort.impl.BubbleSort;
import com.sym.algorithm.sort.impl.HeapSort;
import com.sym.algorithm.sort.impl.InsertionSort;
import com.sym.algorithm.sort.impl.MergeSort;
import com.sym.algorithm.sort.impl.QuickSort;
import com.sym.algorithm.sort.impl.SelectionSort;
import com.sym.algorithm.sort.impl.ShellSort;
import com.sym.util.SymArrayUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        int length = 100;
        array = SymArrayUtil.getRandomArray(length);
    }

    @Test
    public void totalTest() {
        AbstractIntegerSort[] array = new AbstractIntegerSort[]{
                new BubbleSort(copy()),
                new SelectionSort(copy()),
                new HeapSort(copy()),
                new InsertionSort(copy()),
                new QuickSort(copy()),
                new MergeSort(copy()),
                new ShellSort(copy())
        };

        // 执行排序
        List<AbstractIntegerSort> sortList = Arrays.asList(array);
        sortList.parallelStream().forEach(ISort::sort);

        // 根据排序时间来打印信息
        sortList.sort(AbstractIntegerSort.comparator);
        sortList.forEach(System.out::println);
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
    public void insertionSort() {
        doSort(new InsertionSort(copy()));
    }

    /**
     * 快速排序
     */
    @Test
    public void quickSort() {
        doSort(new QuickSort(copy()));
    }

    /**
     * 归并排序
     */
    @Test
    public void mergeSort() {
        doSort(new MergeSort(copy()));
    }

    /**
     * 希尔排序
     */
    @Test
    public void shellSort() {
        doSort(new ShellSort(copy()));
    }

    // 执行排序
    private void doSort(ISort<?> sort) {
        sort.sort();
        System.out.println(sort);
    }

    //  拷贝数组
    private int[] copy() {
        return Arrays.copyOf(array, array.length);
    }
}
