package com.sym.algorithm.leetcode.string;

/**
 * 一个由字母和数字组成的字符串的 值定义如下：
 * <p>
 * 如果字符串 只 包含数字，那么值为该字符串在 10进制下的所表示的数字。
 * 否则，值为字符串的 长度。
 * 给你一个字符串数组strs，每个字符串都只由字母和数字组成，请你返回 strs中字符串的 最大值。
 *
 * @author shenyanming
 * {@link <a href="https://leetcode.cn/problems/maximum-value-of-a-string-in-an-array">数组中字符串的最大值</a>}
 * Created on 2023/6/23 16:15.
 */

public class MaximumValue {
    public static void main(String[] args) {
        String[] strs = {"alic3", "bob", "3", "4", "00000"};
        int val = new MaximumValue().maximumValue(strs);
        System.out.println(val);
    }

    public int maximumValue(String[] strs) {
        int maximum = Integer.MIN_VALUE;
        for (String s : strs) {
            int cur = isDigital(s) ? Integer.parseInt(s) : s.length();
            maximum = Math.max(maximum, cur);
        }
        return maximum;
    }

    private boolean isDigital(String s) {
        for (char c : s.toCharArray()) {
            // 直接使用JDK提供API也行: Character.isDigit(c);
            if (c < 48 || c > 57) {
                return false;
            }
        }
        return true;
    }
}
