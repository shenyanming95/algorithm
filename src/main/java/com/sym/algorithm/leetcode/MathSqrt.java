package com.sym.algorithm.leetcode;

/**
 * 链接：https://leetcode.cn/problems/sqrtx/
 *
 * @author shenyanming
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
