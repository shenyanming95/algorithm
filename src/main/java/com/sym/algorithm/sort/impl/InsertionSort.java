package com.sym.algorithm.sort.impl;

import com.sym.algorithm.sort.AbstractIntegerSort;

/**
 * 插入排序
 * <p>
 * 取出数组中的元素i，将i与i左边元素按照从右往左的顺序依次比较，若遇到比i大的元素j，将i与j互换位置，继续比较直至遇到比i小的元素或达到数组头元素停止;依次取出i右边元素，重复此过程
 * 第一次循环：取到下标为1的元素，与其左边元素(下标为0)比较，若大于它则结束第一次循环，若小于它则互换他两的位置
 * 第二次循环：取到下标为2的元素，与其左边元素(下标为1,0)依次比较，若大于下标为1的元素则结束第二次循环，若小于下标为1的元素，互换他两位置；比较下标为1和下标为0的元素大小
 * 第三次循环：取到下标为3的元素，与其左边元素(下标为2,1,0)依次比较...若大于下标为2的元素则结束第三次循环，若小于则互换他两的位置，比较下标为2和1的元素大小,...依次类推
 * ...
 * 第n-1次循环，取到下标为(n-2,n-3,...,0)的元素，依次比较...
 *
 * @author shenyanming
 * Created on 2020/9/2 11:59
 */
public class InsertionSort extends AbstractIntegerSort {

    public InsertionSort(int[] array) {
        super(array, "插入排序");
    }

    @Override
    protected void internalSort(int[] array) {
        int start;
        for (int i = 1, len = array.length; i < len; i++) {
            start = i;
            while (start > 0) {
                // 依次将元素与其左边的各个元素比较
                if (compareByIndex(start - 1, start) > 0) {
                    swap(start, start - 1);
                    start--;
                } else {
                    // 因为元素左边以前有序了，如果元素最右边都小于这个元素了，那么之前的元素肯定都小于它
                    break;
                }
            }
        }
    }
}
