package com.sym.algorithm.sort;

import com.sym.util.SymArrayUtil;
import com.sym.util.TimeUtil;

import java.util.Arrays;

/**
 * 冒泡排序, 依次比较相邻两个元素, 若后一个元素比前一个元素大, 则互换位置.
 * 每经过一轮, 数组区间内的最大元素就会被移动到末尾.
 * <p>
 * 优化a：如果序列已经完全有序, 可以提前终止冒泡排序;
 * 优化b：如果序列末尾已局部有序, 记录最后一次比较的位置, 下一轮直接到该位置即可;
 *
 * @author ym.shen
 */
public class BubbleSort {

    /**
     * 本地测试
     */
    public static void main(String[] args) {
        int[] arr1 = SymArrayUtil.getRandomArray(1000);
        int[] arr2 = Arrays.copyOf(arr1, arr1.length);
        int[] arr3 = Arrays.copyOf(arr1, arr1.length);
        int[] arr4 = Arrays.copyOf(arr1, arr1.length);

        TimeUtil.execute("最简版V1", () -> SymArrayUtil.print(BubbleSort.sortV1(arr1)));
        TimeUtil.execute("优化版V2", () -> SymArrayUtil.print(BubbleSort.sortV2(arr2)));
        TimeUtil.execute("优化版V3", () -> SymArrayUtil.print(BubbleSort.sortV3(arr3)));
        TimeUtil.execute("优化版V4", () -> SymArrayUtil.print(BubbleSort.sortV4(arr4)));
    }

    /**
     * 最终版冒泡排序, 效率最好, 适应场景多
     */
    public static int[] sort(int[] array) {
        return sortV4(array);
    }

    /**
     * 简单版的冒泡排序实现
     * <p>
     * 第一个for循环, 循环数组的长度, 保证每两个数据之间都可以比较到
     * 第二个for循环, 是真正比较两两之间的逻辑, 这个循环每循环一遍, 就是(数组长度-1)次.
     */
    private static int[] sortV1(int[] array) {
        int len = array.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1; j++) {
                int left = array[j];
                int right = array[j + 1];
                if (left > right) {
                    array[j + 1] = left;
                    array[j] = right;
                }
            }
        }
        return array;
    }

    /**
     * 每一轮次的循环, 会把最大的元素移到末尾位置, 即：
     * 第一轮循环, 移动当前数组最大元素到最末尾位置;
     * 第二轮循环，移动当前数组第二大元素到末尾第二位置;
     * ...
     * 实际每经过一轮冒泡排序, 其实数组后面已经有序, 就不用每次都遍历array.length - 1次
     */
    private static int[] sortV2(int[] array) {
        for (int i = 0; i < array.length; i++) {
            // 其实这边可以看下V4版本, 没必要每次进来计算end取值,
            // end直接放到外层循环定义即可.
            int end = array.length - i - 1;
            for (int j = 0; j < end; j++) {
                if (array[j] > array[j + 1]) {
                    int n = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = n;
                }
            }
        }
        return array;
    }

    /**
     * 如果数组一开始就是有序的, 那就没必要进行冒泡排序
     */
    private static int[] sortV3(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int end = array.length - i - 1;
            // 在每一轮开始前, 重置这个变量为true, 如果再这一轮次中, 都没有发生过变化
            // 说明原数组之前就是有序的.
            boolean isSorted = true;
            for (int j = 0; j < end; j++) {
                if (array[j] > array[j + 1]) {
                    isSorted = false;
                    int n = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = n;
                }
            }
            if (isSorted) {
                return array;
            }
        }
        return array;
    }

    /**
     * 大部分情况下, 给定的数组全都有序的情况比较小, 但是却有可能经过一轮交换过后,
     * 数组尾部局部有序. 因此定义一个变量, 保存当前轮次最后一次交换位置的数组下标,
     * 然后下一轮就直接交换到这个下标为止.
     */
    private static int[] sortV4(int[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            // 记录最后一次交换的位置
            int lastSwapIndex = 1;
            for (int i = 0; i < end; i++) {
                if (array[i] > array[i + 1]) {
                    int n = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = n;
                    // 发生位置交换了, 记录当前位置
                    lastSwapIndex = i + 1;
                }
            }
            // 一轮遍历, 重置end的索引, 然后外层for循环就会对end-1, 再进行判断,
            // 是否要继续循环, 所以将lastSwapIndex的初始值设为1, 这样如果内层循环
            // 没有执行过, 那就意味着数组已经全有序, 外层for循环将end-1后变为0, 直接退出外层循环
            end = lastSwapIndex;
        }
        return array;
    }

}
