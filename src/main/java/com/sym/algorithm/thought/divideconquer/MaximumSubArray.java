package com.sym.algorithm.thought.divideconquer;

/**
 * 最大连续子序列和：给定一个长度为 n 的整数序列, 求它的最大连续子序列和.
 * 比如 [-2, 1, -3, 4, -1, 2, 1, -5, 4]的最大连续子序列和为 [4, -1, 2, 1].
 * 理论上这道题可以用动态规划来实现, 但是也属于最大切片问题, 所以可以通过分治解题：
 *
 * <pre>
 * 最大连续子序列, 假设数组为[start, end)
 *
 *  start                                      end
 *    ↓                                         ↓
 *    ------------------------------------------
 *
 * 那么它的最大连续子序列(i, j)有且只会存在次存在三种情况：
 *  1)、(i~j)位于左半区域, 即[start, middle);
 *  2)、(i~j)位于右半区域, 即[middle, end);
 *  3)、(i~j)位于middle左右两边, 即[i, middle) + [middle, j)
 *
 *  start              middle                  end
 *    ↓                   ↓                     ↓
 *    ------------------------------------------
 *       1.(i ~ j)               2.(i ~ j)
 *                   3.(i, j)
 * </pre>
 *
 * @author shenyanming
 * Created on 2021/5/16 20:49.
 */

public class MaximumSubArray {

    public static void main(String[] args) {
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maximumSubArray(0, array.length, array));
    }

    /**
     * 最大连续子序列
     *
     * @param startIndex 起始索引(包括)
     * @param endIndex   终止索引(不包括)
     * @param array      数组
     * @return 最大连续子序列和
     */
    private static int maximumSubArray(int startIndex, int endIndex, int[] array) {
        if (endIndex - startIndex < 2) {
            // 递归到范围只剩一个元素, 直接返回
            return array[startIndex];
        }
        // 获取居中索引
        int middleIndex = (endIndex + startIndex) >> 1;

        /*
         * 从middle出发, 向左右两边延伸计算局部最大连续子序列和, 最后与
         * 左半区和右半区比较, 这三者谁大谁就是整个数组的最大连续子序列和.
         */

        // middle为起点, 然后向左延伸计算
        int leftMax = array[middleIndex - 1];
        int leftSum = 0;
        for (int i = middleIndex - 1; i >= startIndex; i--) {
            leftSum += array[i];
            leftMax = Math.max(leftMax, leftSum);
        }
        // middle为起点, 然后向右延伸计算
        int rightMax = array[middleIndex];
        int rightSum = 0;
        for (int i = middleIndex; i < endIndex; i++) {
            rightSum += array[i];
            rightMax = Math.max(rightMax, rightSum);
        }

        // 这三个区域选择最大的作为整个数组的最大连续子序列和.
        return Math.max(leftMax + rightMax,
                Math.max(maximumSubArray(startIndex, middleIndex, array),
                maximumSubArray(middleIndex, endIndex, array)));
    }
}
