package com.sym.algorithm.sort.impl;

import com.sym.algorithm.sort.AbstractIntegerSort;

/**
 * 计数排序
 *
 * @author shenyanming
 * @date 2020/10/11 14:50.
 */

public class CountingSort extends AbstractIntegerSort {

    public CountingSort(int[] array) {
        super(array, "计数排序");
    }

    @Override
    protected void internalSort(int[] array) {
        // 找出最值
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        // 开辟内存空间，存储次数
        int[] counts = new int[max - min + 1];
        // 统计每个整数出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i] - min]++;
        }
        // 将上面统计到的counts数组, 每个索引下的元素累加次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        // 从后往前遍历原数组，将它放到有序数组中的合适位置.
        // 注意这边一定需要开辟一个新的数组
        int[] newArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            newArray[--counts[array[i] - min]] = array[i];
        }
        // 将有序数组赋值到array
        for (int i = 0; i < newArray.length; i++) {
            array[i] = newArray[i];
        }
    }

    /**
     * 简单版的计数排序, 依次统计每个元素出现的次数
     *
     * @param array 待排序数组
     */
    private void sort0(int[] array) {
        // 找出最大值
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        } // O(n)

        // 开辟内存空间，存储每个整数出现的次数
        int[] counts = new int[1 + max];
        // 统计每个整数出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i]]++;
        } // O(n)

        // 根据整数的出现次数，对整数进行排序
        int index = 0;
        for (int i = 0; i < counts.length; i++) {
            while (counts[i]-- > 0) {
                array[index++] = i;
            }
        } // O(n)
    }
}
