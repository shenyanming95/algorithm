package com.sym.algorithm.thought.dynamic;

/**
 * 找零钱：假设有25分、20分、5分、1分的硬币, 现要找给客户41分的零钱, 如何办到硬币个数最少？
 * <p>
 * 动态规划通常是一个演变过程, 一开始, 都是以暴力递归开始,
 * 接着发现其存在大量重复计算, 而将其改为记忆化搜索, 最终
 * 发现可以自底向上的的递推.
 * </p>
 *
 * @author shenyanming
 * Created on 2021/5/17 21:47.
 */

public class CoinChange {

    public static void main(String[] args) {
        int n = 19;
        System.out.println(coinsV1(n));
        System.out.println(coinsV2(n));
        System.out.println(coinsV3(n));
        places(n);
    }

    /**
     * 暴力递归(自顶向下的调用, 同时出现重叠子问题).
     * 假设dp(n)是凑到n分需要的最少硬币个数:
     * <pre>
     *   若第1次选择了25分的硬币, 那么dp(n) = dp(n - 25) + 1;
     *   若第1次选择了20分的硬币, 那么dp(n) = dp(n - 20) + 1;
     *   若第1次选择了5分的硬币, 那么dp(n) = dp(n - 5) + 1;
     *   若第1次选择了1分的硬币, 那么dp(n) = dp(n - 1) + 1;
     *   所以dp(n) = min{dp(n - 25), dp(n - 20), dp(n - 5), dp(n - 1)} + 1.
     * </pre>
     *
     * @param n 需要的零钱
     * @return 最少硬币个数
     */
    private static int coinsV1(int n) {
        if (n < 1) {
            return Integer.MAX_VALUE;
        }
        if (n == 25 || n == 20 || n == 5 || n == 1) {
            return 1;
        }
        int m1 = Math.min(coinsV1(n - 25), coinsV1(n - 20));
        int m2 = Math.min(coinsV1(n - 5), coinsV1(n - 1));
        return Math.min(m1, m2) + 1;
    }


    /**
     * {@link #coinsV1(int)}的解法会出现大量的重叠子问题, 造成同一个n计算了多次,
     * 因此很自然地想到, 将子问题的结果保存起来, 使用记忆化搜索.
     *
     * @param n 需要的零钱
     * @return 最少硬币个数
     */
    private static int coinsV2(int n) {
        // 定义dp数组存储子问题的解
        int[] dp = new int[n + 1];
        // 预先存储好硬币
        int[] coins = {1, 5, 20, 25};
        for (int coin : coins) {
            if (n < coin) {
                continue;
            }
            dp[coin] = 1;
        }
        return coinsV2(n, dp);
    }

    private static int coinsV2(int n, int[] dp) {
        if (n < 1) {
            return Integer.MAX_VALUE;
        }
        if (dp[n] == 0) {
            // dp[n] == 0, 说明选择n分硬币的最少个数还没有计算
            int m1 = Math.min(coinsV2(n - 25, dp), coinsV2(n - 20, dp));
            int m2 = Math.min(coinsV2(n - 5, dp), coinsV2(n - 1, dp));
            dp[n] = Math.min(m1, m2) + 1;
        }
        return dp[n];
    }


    /**
     * {@link #coinsV2(int)} 使用记忆化搜索, 但是仍是以自顶向下的执行逻辑,
     * 这样就会导致递归的出现. 因此最好的方式是自底向上的执行逻辑, 也就是动态规划
     *
     * @param n 需要的零钱
     * @return 最少硬币个数
     */
    private static int coinsV3(int n) {
        // 定义dp数组用于存储状态
        int[] dp = new int[n + 1];
        // 动态规划是自底向上的执行
        for (int i = 1; i <= n; i++) {
            // dp[i] 还是等于 dp[i-25]、dp[i-20]、dp[i-5]、dp[i-1] 取最小值后 + 1
            int min = dp[i - 1];
            if (i >= 5) min = Math.min(min, dp[i - 5]);
            if (i >= 20) min = Math.min(min, dp[i - 20]);
            if (i >= 25) min = Math.min(min, dp[i - 25]);
            dp[i] = min + 1;
        }
        return dp[n];
    }

    /**
     * 同{@link #coinsV3(int)} 一样,  使用动态规划的方式,
     * 但是可以计算出如何选取硬币.
     */
    private static void places(final int n) {
        // 保存状态
        int[] dp = new int[n + 1];
        // 保存在第i分时选择的最后一枚硬币
        int[] places = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            if (i >= 1 && min > dp[i - 1]) {
                min = dp[i - 1];
                places[i] = 1;
            }
            if (i >= 5 && min > dp[i - 5]) {
                min = dp[i - 5];
                places[i] = 5;
            }
            if (i >= 20 && min > dp[i - 20]) {
                min = dp[i - 20];
                places[i] = 20;
            }
            if (i >= 25 && min > dp[i - 25]) {
                min = dp[i - 25];
                places[i] = 25;
            }
            dp[i] = min + 1;
        }

        // 解析places, 计算出总共要选择的硬币
        String suffix = "分 + ";
        int total = n;
        StringBuilder sb = new StringBuilder("选择方案：");
        while (total > 0) {
            // 读取出每次选择的硬币分值
            int coin = places[total];
            sb.append(coin).append(suffix);
            // 剩余要凑的硬币分值
            total -= coin;
        }
        if (sb.length() > 6) {
            System.out.println(sb.substring(0, sb.length() - 3));
        }
    }
}
