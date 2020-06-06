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
        TimeUtil.execute("kmp", () -> kmp(s1, s2));
    }

    public static int kmp(String mainString, String modelString) {
        int[] next = getNext(modelString);
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


    /**
     * @param modelString 模式字符串
     *                    求next数组
     */
    public static int[] getNext(String modelString) {
        int[] next = new int[100];
        next[0] = -1;
        int i = 0;
        int j = -1;
        while (i < modelString.length()) {
            if (j == -1 || modelString.charAt(i) == modelString.charAt(j)) {
                //相等的情况
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }

        return next;
    }
}
