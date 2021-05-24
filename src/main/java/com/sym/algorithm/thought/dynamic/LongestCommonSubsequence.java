package com.sym.algorithm.thought.dynamic;

/**
 * 动态规划求解：最长公共子序列(LCS), 例如：
 * <pre>
 *     给定两个字符串 text1 和 text2, 返回这两个字符串的最长公共子序列的长度, 如：
 *     text1="abcde", text2="ace", 它们的最长公共子序列是 "ace", 其长度为 3.
 * </pre>
 *
 * @author shenyanming
 * Created on 2021/5/23 20:32.
 */
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        int[] num1 = {1, 2, 3, 4};
        int[] num2 = {5, 3, 7, 8};
        System.out.println(lengthOfLcs(num1, num2));
    }

    /**
     * 按照动态规划的解题步骤：
     * 1.定义状态: dp[i][j]为截止到nums1[i]和nums2[j]的最大公共子序列长度;
     * 2.初始状态: dp[i][0] 和 dp[0][j] 值都为0 (当某一方没有任何子序列时, 其最大公共子序列肯定为0);
     * 3.状态转移: 若nums1[i] == nums2[j], 那么dp[i][j]=dp[i-1][j-1] + 1, 反之若nums1[i] != nums2[j],
     *            dp[i][j] = max{dp[i-1][j], dp[i][j-1]}, 因为当nums1[i]和nums2[j]不相等时, 就需要拿
     *            nums1[0, i-1]与nums2[0, j]、nums1[0, i]与nums2[0, j-1]这两组子序列去比较, 然后取最大值作为dp[i][j]的值.
     *
     * @param nums1 序列1
     * @param nums2 序列2
     * @return 最大公共子序列
     */
    private static int lengthOfLcs(int[] nums1, int[] nums2) {
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[nums1.length][nums2.length];
    }
}
