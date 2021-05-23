package com.sym.algorithm.thought.dynamic;

/**
 * 动态规划求解：最大连续子序列和, 例如：
 * <pre>
 *     给定一个长度为n的整数序列, 求它的最大连续子序列和. 比如,
 *     {-2, 1, -3, 4, -1, 2, 1, -5, 4} 的最大连续子序列和
 *     是 4 + (-1) + 2 + 1 = 6.
 * </pre>
 *
 * @see CoinChange (入门)
 *
 * @author shenyanming
 * Created on 2021/5/22 21:08.
 */
public class MaximumSubArray {

    public static void main(String[] args) {
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maximumV1(array));
        System.out.println(maximumV2(array));
    }


    /**
     * 按照动态规划的解题步骤：
     * 1.定义状态, 假设dp[i]为原数组第i位的最大连续子序列和;
     * 2.初始状态, dp[0] = array[0];
     * 3.状态转移, 若dp[i-1]为负数, dp[i]=array[i]; 若dp[i-1]为正数, dp[i]=dp[i-1] + array[i];
     *
     * @param array 数组
     * @return 最大连续子序列和
     */
    private static int maximumV1(int[] array) {
        int[] dp = new int[array.length];
        int max = dp[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            if (dp[i - 1] <= 0) {
                dp[i] = array[i];
            } else {
                dp[i] = dp[i - 1] + array[i];
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * 基于{@link #maximumV1(int[])}, 可以发现实际使用到的dp数组其实只有一个,
     * 因此没必要初始化全部的dp数组, 只需要一个dp变量来存储动态规划的中间值即可.
     *
     * @param array 数组
     * @return 最大连续子序列和
     */
    private static int maximumV2(int[] array) {
        int dp = array[0], max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (dp <= 0) {
                dp = array[i];
            } else {
                dp = dp + array[i];
            }
            max = Math.max(max, dp);
        }
        return max;
    }

}
