package com.sym;

import com.sym.algorithm.sort.BubbleSort;
import com.sym.algorithm.sort.InsertSort;
import com.sym.algorithm.sort.QuickSort;
import com.sym.algorithm.sort.SelectSort;
import com.sym.util.SymArrayUtil;
import com.sym.util.TimeUtil;
import org.junit.Test;

import java.util.Arrays;


/**
 * 比较各排序算法耗费的时间
 *
 * @author user
 */
public class SortAlgorithmTest {

    @Test
    public void test() {
        // 待排序数组
        int[] arr1 = SymArrayUtil.getRandomArray(12580);
        int[] arr2 = Arrays.copyOf(arr1, arr1.length);
        int[] arr3 = Arrays.copyOf(arr1, arr1.length);
        int[] arr4 = Arrays.copyOf(arr1, arr1.length);

        TimeUtil.execute("冒泡排序", () -> {
            BubbleSort.sort(arr1);
        });
        TimeUtil.execute("选择排序", () -> {
            SelectSort.sort(arr2);
        });
        TimeUtil.execute("快速排序", () -> {
            QuickSort.sort(arr3);
        });
        TimeUtil.execute("插入排序", () -> {
            InsertSort.sort(arr4);
        });

    }
}
