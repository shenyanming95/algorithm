package com.sym.algorithm.leetcode.math;

/**
 * 给你一个正整数 n ，找出满足下述条件的 中枢整数 x ：
 * 1 和 x 之间的所有元素之和等于 x 和 n 之间所有元素之和。
 * 返回中枢整数 x 。如果不存在中枢整数，则返回 -1
 *
 * @author shenyanming
 * {@link <a href="https://leetcode.cn/problems/find-the-pivot-integer">找出中枢整数</a>}
 * Created on 2023/6/28 8:23.
 */

public class PivotInteger {
    public static void main(String[] args) {
        System.out.println(new PivotInteger().pivotInteger(4));
        System.out.println(new PivotInteger().pivotIntegerV2(4));
    }

    public int pivotInteger(int n) {
        int prev = 0, tail = (1 + n) * (n / 2) + middle(n);
        for (int z = 1; z <= n; z++) {
            if ((prev += z) == (tail -= (z - 1))) {
                return z;
            }
        }
        return -1;
    }

    /**
     * 官方题解: 根据等差公式求和公式=n*a1+n(n-1)d/2或Sn=n(a1+an)/2.
     * 即求解 sum(1, x) = sum(x, n), 其中x为中枢整数, n为指定数值, 公差为1.
     * 套用公式: x*(1+x)/2 = (n-x+1)(x+n)/2 → x=开根号(n^2+n)/2
     */
    public int pivotIntegerV2(int n) {
        int t = (n * n + n) / 2;
        int x = (int) Math.sqrt(t);
        if (x * x == t) {
            return x;
        }
        return -1;
    }

    private int middle(int i) {
        if (i % 2 == 0) {
            return 0;
        } else {
            return (int) Math.ceil((double) i / 2);
        }
    }
}
