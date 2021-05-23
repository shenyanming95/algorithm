package com.sym.algorithm.thought.dynamic;

/**
 * 动态规划求解：最长上升子序列(LIS), 例如：
 * <pre>
 *     给定一个无序的整数序列, 求出它最长上升子序列的长度(要求严格上升), 比如
 *     {10, 2, 2, 5, 1, 7, 101, 18} 的最长上升子序列是{2, 5, 7, 101}或
 *     {2, 5, 7, 18}, 因此它的最长上升子序列为4.
 * </pre>
 *
 * @see CoinChange (入门)
 *
 * @author shenyanming
 * Created on 2021/5/23 8:26.
 */
public class LongestIncreasingSubsequence {

    public static void main(String[] args) {
        int[] array = {1,3,6,7,9,4,10,5,6};
        System.out.println(lengthOfLIS(array));
    }


    /**
     * 按照动态规划的解题步骤：
     * 1.定义状态: dp[i]为原数组以下标为i的元素为结尾的最长上升子序列的值;
     * 2.初始状态: dp[0]因为只有一个元素, 所以它的最长上升子序列值为1, 并且所有dp[i]初始值都为1;
     * 3.状态转移: 由于上升子序列可以是非连续的, 所以求得dp[i]需要遍历j∈[0,j), 当nums[i] > nums[j],
     *            说明nums[i]可以拼在nums[j]后面, 从而得到一个比dp[j]更大得上升子序列, 即dp[j] + 1,
     *            反之说明nums[i]不能拼在nums[j]后面, 此时就保持dp[i] = 1.所以状态转移方程为：dp[i] = max{dp[i], dp[j] + 1}
     *
     * @param nums 数组
     * @return 最长上升子序列值
     */
    private static int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) {
                    continue;
                }
                dp[i] = Math.max(dp[i], dp[j] +1);
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
