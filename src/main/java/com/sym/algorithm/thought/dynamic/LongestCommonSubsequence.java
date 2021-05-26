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
        int[] num1 = {1, 3, 5, 9, 10};
        int[] num2 = {1, 4, 9, 10};
        System.out.println(lengthOfLcsV1(num1, num2));
        System.out.println(lengthOfLcsV2(num1, num2));
        System.out.println(lengthOfLcsV3(num1, num2));
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
    private static int lengthOfLcsV1(int[] nums1, int[] nums2) {
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

    /**
     * 通过动态规划{@link #lengthOfLcsV1(int[], int[])}可以求解最大公共子序列的值,
     * 但实际上发生每求得新一行数据, 都只需要借助前一行数据, 所以可以节省空间不需要初始
     * 化i行j列的数组.
     *
     * @param nums1 序列1
     * @param nums2 序列2
     * @return 最大公共子序列
     */
    private static int lengthOfLcsV2(int[] nums1, int[] nums2){
        int[][] dp = new int[2][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            // 分别对2求余, 以确定它们存放在dp数组的哪一行上, 又因为对2求余实际上等同于和1进行与运算.
            int row = i & 1;
            int prevRow = (i - 1) & 1;
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[row][j] = dp[prevRow][j - 1] + 1;
                } else {
                    dp[row][j] = Math.max(dp[prevRow][j], dp[row][j - 1]);
                }
            }
        }
        return dp[nums1.length & 1][nums2.length];
    }

    /**
     * 通过{@link #lengthOfLcsV2(int[], int[])}可以将空间利用到两行多列的<b>滚动数组</b>,
     * 但实际上, 每次计算当前行dp[i][j]只需要用到前一行数据, 之所以要用两行的滚动数组, 是因为要
     * 存储新计算得到的当前行dp[i][j], 以便在求解下一行数据用到.
     *
     * 因此还可以继续对其优化, 将二维数组压缩成一维数组, 当前行新计算的结果覆盖掉原先一维dp数组的值,
     * 所以呢, nums1[i] == nums2[j], 则dp[i]=dp[i-1]+1; 而nums1[i]!=nums2[j], 那么dp[i]
     * = max{dp[i-1],dp[i]}, 其中dp[i]是上一行计算好的值, 则dp[i-1]是当前行计算好的值, 但是呢
     * 上一行<b>i-1</b>这个位置的值已经被覆盖掉, 所以我们必须开辟一个变量用来存储上一行<b>i-1</b>这个位置的值.
     *
     * @param nums1 序列1
     * @param nums2 序列2
     * @return 最大公共子序列
     */
    private static int lengthOfLcsV3(int[] nums1, int[] nums2){
        int[] dp = new int[nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            int cur = 0;
            for (int j = 1; j <= nums2.length; j++) {
                int leftTop = cur;
                cur = dp[j];
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[j] = leftTop + 1;
                } else {
                    dp[j] = Math.max(dp[j - 1], dp[j]);
                }
            }
        }
        return dp[nums2.length];
    }
}
