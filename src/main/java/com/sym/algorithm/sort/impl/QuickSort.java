package com.sym.algorithm.sort.impl;

import com.sym.algorithm.sort.AbstractIntegerSort;

/**
 * 快速排序, 采用分治思想.
 * 取数组的某一个数为基数(可以取首个数也可以取随机值)，定义2个变量start,end分别指向数组的首下标和尾下标，让end依次递减与基数比较，
 * 若发现比基数小，与start指向的数互换，end保持不变转而让start依次递加与基数比较，若发现比基数大，与end指向的数互换位置，start
 * 保持不变转而让end递减比较...以此类推直至start==end，第一次快速排序得到的结果：比基数小的位于基数左边，比基数大的位于基数右边，
 * 第一次排序后，数组可以分为两部分，对这两部分在按照上面的排序方式进行排序...直至所有部分都排序完毕
 *
 * @author shenyanming
 * Created on 2020/9/2 13:44
 */
public class QuickSort extends AbstractIntegerSort {

    public QuickSort(int[] array) {
        super(array, "快速排序");
    }

    @Override
    protected void internalSort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    /**
     * 递归方法，当start == end 时，递归停止
     *
     * @param array      待排序数组
     * @param startIndex 待排序起始点
     * @param endIndex   待排序终止点
     */
    private void sort(int[] array, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            return;
        }
        int left = startIndex;
        int right = endIndex;
        int base = array[startIndex];
        boolean flag = true;
        while (left != right) {
            if (flag) {
                if (compareByValue(array[right], base) > 0) {
                    right--;
                } else {
                    cover(left, right);
                    left++;
                    flag = false;
                }
            } else {
                if (compareByValue(array[left], base) < 0) {
                    left++;
                } else {
                    cover(right, left);
                    right--;
                    flag = true;
                }
            }
        }
        array[left] = base;
        // 递归执行左边
        if (--left < startIndex) {
            left = startIndex;
        }
        sort(array, startIndex, left);
        // 递归执行右边
        if (++right > endIndex) {
            right = endIndex;
        }
        sort(array, right, endIndex);
    }
}
