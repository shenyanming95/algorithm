package com.sym.algorithm.kmp;

import com.sym.util.TimeUtil;

/**
 * 网上的kmp算法实现
 *
 * @author shenyanming
 * @date 2020/6/6 22:10.
 */

public class StringKmpFromNet {
    
    public static void main(String[] args) {
        String s1 = "dashhcjkbjdfhasdbnsakjdbaskhdioasbdjwbejsah";
        String s2 = "dbaskhdi";
        TimeUtil.execute("kmp版本1", () -> kmpV1(s1, s2));
        TimeUtil.execute("kmp版本2", () -> kmpV2(s1, s2));
    }

    public static int kmpV1(String mainString, String modelString) {
        // 获取next数组
        int[] next = new int[100];
        next[0] = -1;
        int x = 0;
        int y = -1;
        while (x < modelString.length()) {
            if (y == -1 || modelString.charAt(x) == modelString.charAt(y)) {
                //相等的情况
                x++;
                y++;
                next[x] = y;
            } else {
                y = next[y];
            }
        }
        // kmp算法逻辑
        int i = 0;
        int j = 0;
        while (i < mainString.length() && j < modelString.length()) {
            if (-1 == j || mainString.charAt(i) == modelString.charAt(j)) {
                i++;
                j++;
            } else {
                //不相等,滑动
                j = next[j];
            }
        }
        if (j == modelString.length()) {
            //如果最后j到达模式串的尾部,则说明匹配上了
            return i - j;
        } else {
            return -1;
        }
    }


    public static int kmpV2(String text, String pattern) {
        if ("".equals(pattern)) {
            return 0;
        }
        int[] table = new int[pattern.length()];
        int i = 0, j = 1;
        while (j < table.length) {
            if (pattern.charAt(i) == pattern.charAt(j)) {
                table[j++] = i + 1;
                i++;
            } else {
                if (i == 0) {
                    j++;
                } else {
                    i = table[i - 1];
                }
            }
        }

        i = 0;
        j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = table[j - 1];
                }
            }
            if (j == pattern.length()) {
                return i - j;
            }
        }
        return -1;
    }
}
