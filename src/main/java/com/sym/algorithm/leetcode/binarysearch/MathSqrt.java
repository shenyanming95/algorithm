package com.sym.algorithm.leetcode.binarysearch;

/**
 * 给你一个非负整数 x ，计算并返回x的 算术平方根 。
 * 由于返回类型是整数，结果只保留整数部分 ，小数部分将被舍去 。
 * 注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
 *
 * @author shenyanming、
 * {@link <a href="https://leetcode.cn/problems/sqrtx/">X的平方根</a>}
 * Create on 2023/5/17 下午5:00
 */
public class MathSqrt {

    public static void main(String[] args) {
        System.out.println(mySqrt(2147395599));
    }

    public static int mySqrt(int x) {
        int left = 0, right = x, ret = -1;
        while (left <= right) {
            // 二分查找, 定位mid
            int mid = left + (right - left) / 2;
            // 整型相乘会溢出, 需要转为长整型
            if ((long) (mid * mid) <= x) {
                ret = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ret;
    }
}
