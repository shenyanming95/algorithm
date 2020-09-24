package com.sym.algorithm.sort.impl;

import com.sym.algorithm.sort.AbstractIntegerSort;
import com.sym.util.SymArrayUtil;

/**
 * 插入排序.
 * <p>
 * 取出数组中的元素i, 将i与i左边元素按照从右往左的顺序依次比较，若遇到比i大的元素j,
 * 将i与j互换位置, 否则继续比较直至遇到比i小的元素或达到数组头元素停止, 重复此过程.
 * <pre>
 *     第一次循环：取到下标为1的元素a，与其左边元素(下标为0)b比较, 若a > b则结束第一次循环, 若a < b则互换位置;
 *     第二次循环：取到下标为2的元素c, 与其左边元素(下标为1,0)a和b依次比较，若c > a则结束第二次循环, 若c < a则互换c和a位置; 再接着比较c和b;
 *     ...
 *     第n-1次循环，取到下标为(n-2,n-3,...,0)的元素，依次比较...
 * </pre>
 * 如果原数组中逆序对较少, 插入排序的效率很高, 反之效率较低.
 *
 * @author shenyanming
 * Created on 2020/9/2 11:59
 */
public class InsertionSort extends AbstractIntegerSort {

    public static void main(String[] args) {
        int[] array = SymArrayUtil.getRandomArray(11);
        SymArrayUtil.print(array);

        InsertionSort sort = new InsertionSort(array);
        sort.sort();

        SymArrayUtil.print(array);
    }

    public InsertionSort(int[] array) {
        super(array, "插入排序");
    }

    @Override
    protected void internalSort(int[] array) {
        // 插入排序的第一个元素不需要比较, 所以直接从下标为1的元素开始
        for (int i = 1, len = array.length; i < len; i++) {
            int start = i;
            // 依次与左边元素比较, 如果左边元素大, 则与左边元素互换位置.
            // 由于数组左边元素已经有序, 如果遇到一个左边元素比当前元素小, 那么可以跳出循环
            while (start > 0 && compareByIndex(start - 1, start) > 0) {
                swap(start - 1, start);
                start--;
            }
        }
    }
}
