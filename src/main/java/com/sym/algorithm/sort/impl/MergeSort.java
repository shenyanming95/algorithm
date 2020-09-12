package com.sym.algorithm.sort.impl;

import com.sym.algorithm.sort.AbstractIntegerSort;
import com.sym.util.SymArrayUtil;

/**
 * 归并排序.
 * 包含两个操作：divide和merge, 先通过递归调用, 将原数组分割为不可再分割的序列
 * (每次对半分割), 接着再不断地合并子序列, 使其成为一个新的有序数组.
 *
 * @author shenyanming
 * @date 2020/9/12 19:13.
 */

public class MergeSort extends AbstractIntegerSort {

    public static void main(String[] args) {
        // 本地测试
        int[] array = SymArrayUtil.getRandomArray(50);
        MergeSort sort = new MergeSort(array);
        SymArrayUtil.print(array);
        sort.internalSort(array);
        SymArrayUtil.print(array);
    }

    /**
     * 用以拷贝原数组的一半元素, 用来merge使用
     */
    private int[] temp;

    public MergeSort(int[] array) {
        super(array, "归并排序");
        temp = new int[array.length >> 1];
    }

    @Override
    protected void internalSort(int[] array) {
        divide(array, 0, array.length);
    }

    /**
     * 归并排序的第一步：先将数组依次对半拆分, 直至无法分割
     *
     * @param array      未排序数组
     * @param startIndex 起始索引
     * @param endIndex   截止索引(不包含), 即左闭右开的区间
     */
    private void divide(int[] array, int startIndex, int endIndex) {
        if (endIndex - startIndex <= 1) {
            // 范围[starIndex, endIndex)数组已经无法分割, 即这区间的元素数量只有1或0
            return;
        }
        int middleIndex = (endIndex + startIndex) >> 1;
        // 递归调用, 对左右子序列进行分割
        divide(array, startIndex, middleIndex);
        divide(array, middleIndex, endIndex);
        // 递归调用结束, 说明数组已经无法再分割了, 此时进行合并操作
        merge(array, startIndex, middleIndex, endIndex);
    }


    /**
     * 归并排序的第二步：将数组的子序列进行合并操作(注意, 合并操作都是在基于原数组上完成的)
     *         (startIndex)      (middleIndex)          (endIndex)
     *              ls              le/rs                re
     * — — — — — — — — — — — — — — — —  — — — — — — — — — — — — — — — — — — — — —
     *              |                 |                 |
     * — — — — — — — — — — — — — — — —  — — — — — — — — — — — — — — — — — — — — —
     * 0            ci                                                            array.length
     *
     * 假设现在要合并的子序列如上(归并排序merge的两方必定是相邻的), 其实很简单, 就是将左边即
     * [ls, le), 和右边即[rs, re), 依次取index=ls++和index=rs++的两个数来比较, 将小的值放入
     * 到index=i的位置上, 这个操作就跟要合并有两个有序链表的做法一样.
     *
     * 但是归并排序会特殊一点, 它并不需要开辟一个新的数组, 将比较结果保存进去, 而是在原先数组的
     * 基础上进行值的覆盖操作. 所以呢, 就把左边的数组[ls, le)拷贝一份, 然后i指向原数组的ls位置,
     * 这样子就能保住左右两边仍然可以比较, 而且可以把比较结果重新更新到原先数组上.
     *
     *
     * @param array       未排序数组
     * @param startIndex  起始位置
     * @param middleIndex 中间位置
     * @param endIndex    终止位置
     */
    private void merge(int[] array, int startIndex, int middleIndex, int endIndex) {
        // 因为要将两个有序的子数组合并为一个新数组, 所以需要定义5个指针, 分别：
        // ls即leftStart, 表示复制的temp数组的起始索引, 固定为0; le即leftEnd, 表示复制的temp数组的终止索引(不包括)
        int ls = 0, le = middleIndex - startIndex;
        // rs即rightIndex, 表示右边数组(原数组)的起始索引; re即rightEnd, 表示右边数组(原数组)的终止索引(不包括)
        int rs = middleIndex, re = endIndex;
        // 每次比较结果都需要更新到原数组上, 所以还需要额外定义一个指针ci, 即currentIndex
        int ci = startIndex;

        // 备份出左边数组
        for(int i = 0; i < le; i++){
            // 注意这边要用 startIndex + i, 因为并不是从原数组下标为0的位置开始拷贝
            temp[i] = array[startIndex + i];
        }

        // 一旦左边数组比较完了, 即ls >= le, 那么就没必要再继续比较了, 右边数组也不需要再操作,
        // 因此这边使用左边数组(备份的数组)的指针来做循环条件.
        while(ls < le){
            // 这个if条件有两个含义：
            // 其一, 外层循环已经保证ls和le的正确性, 这里rs < re来保证右边数组遍历的正确性, 一旦右边数组已经比较完毕, 则直接进入else语句块
            // 其二, 依次比较temp和array的元素大小, 如果temp的元素比array的元素, 那么就需要将值较小的元素移位, 移动到ci位置上, 为了保证稳定性, 这边只能用大于而不不能用大于等于.
            if(rs < re && compareByValue(temp[ls], array[rs]) > 0){
                array[ci++] = array[rs++];
            }else{
                // 否则右边index=rs的元素比左边index=rs的大,
                array[ci++] = temp[ls++];
            }
            swapCount();
        }

    }
}
