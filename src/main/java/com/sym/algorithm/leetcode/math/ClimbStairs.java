package com.sym.algorithm.leetcode.math;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶.
 * 每次你可以爬 1 或 2 个台阶, 你有多少种不同的方法可以爬到楼顶呢？
 * <pre>
 *     输入：n = 2
 *     输出：2
 *     解释：有两种方法可以爬到楼顶。
 *          1. 1 阶 + 1 阶
 *          2. 2 阶
 * </pre>
 * <pre>
 *     输入：n = 3
 *     输出：3
 *     解释：有三种方法可以爬到楼顶。
 *     1. 1 阶 + 1 阶 + 1 阶
 *     2. 1 阶 + 2 阶
 *     3. 2 阶 + 1 阶
 * </pre>
 *
 * @author shenyanming
 * {@link <a href="https://leetcode.cn/problems/climbing-stairs/description/">爬楼梯</a>}
 * Created on 2023/7/29 16:12.
 */

public class ClimbStairs {
    
    public static void main(String[] args) {
        ClimbStairs o = new ClimbStairs();
        System.out.println(o.climbStairs(6));
        System.out.println(o.climbStairs(13));
    }

    /**
     * n指楼梯
     */
    int climbStairs(int n) {
        // 要达到n阶楼梯, 最后一步要么走一步, 要么走两步, 则: f(n) = f(n-1)+f(n-2).
        // f(0)=0, f(1)=1, f(2)=2, f(3)=3, f(4)=5, ...
        // 可以通过递归实现, 也可以通过动态规划, 由底向上演进
        int x1 = 0, x2 = 1, ret = 0;
        for (int i = 0; i < n; i++) {
            ret = x1 + x2;
            x1 = x2;
            x2 = ret;
        }
        return ret;
    }
}
