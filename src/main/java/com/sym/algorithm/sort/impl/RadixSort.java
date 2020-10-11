package com.sym.algorithm.sort.impl;

import com.sym.algorithm.sort.AbstractIntegerSort;

/**
 * 基数排序
 *
 * @author shenyanming
 * @date 2020/10/11 15:23.
 */

public class RadixSort extends AbstractIntegerSort {


    public RadixSort(int[] array) {
        super(array, "基数排序");
    }

    @Override
    protected void internalSort(int[] array) {
        // 找出最大值
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        // 个位数: array[i] / 1 % 10 = 3
        // 十位数：array[i] / 10 % 10 = 9
        // 百位数：array[i] / 100 % 10 = 5
        // 千位数：array[i] / 1000 % 10 = ...

        for (int divider = 1; divider <= max; divider *= 10) {
            countingSort(divider);
        }
    }

    protected void countingSort(int divider) {
        // 开辟内存空间，存储次数
        int[] counts = new int[10];

        // 统计每个整数出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i] / divider % 10]++;
        }

        // 累加次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        // 从后往前遍历元素，将它放到有序数组中的合适位置
        int[] newArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            newArray[--counts[array[i] / divider % 10]] = array[i];
        }

        // 将有序数组赋值到array
        for (int i = 0; i < newArray.length; i++) {
            array[i] = newArray[i];
        }
    }
}
