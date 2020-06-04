package com.sym.algorithm.kmp;

import com.sym.util.TimeUtil;

/**
 * 《数据结构-Java语言描述第2版》提供的KMP算法实现
 *
 * @author shenyanming
 * @date 2020/6/4 21:05.
 */

public class StringKmpFromBook {

    public static void main(String[] args) {
        String s1 = "dashhcjkbjdfhasdbnsakjdbaskhdioasbdjwbejsah";
        String s2 = "dbaskhdi";
        TimeUtil.execute("kmp", () -> kmp(s1, s2));
    }

    public static int kmp(String main, String pattern) {
        int[] next = getNextVal(pattern);
        // i和j依次为主串和模式串的指针
        int i = 0;
        int j = 0;
        // 对两串从左到右逐个比较字符
        while (i < main.length() && j < pattern.length()) {
            //若对应字符匹配
            if (j == -1 || main.charAt(i) == pattern.charAt(j)) { // j == -1表示main[i]!=pattern[0]
                j++;
                i++;
            } else { //当main[i]!=pattern[j]时
                // 模式串右移
                j = next[j];
            }
        }
        if (i < pattern.length()) {
            return -1;
        }
        return i - pattern.length();
    }

    /**
     * 未优化的获取next[]数组的方法
     *
     * @param t 串
     * @return next[]数组
     */
    private static int[] getNext(String t) {
        int[] next = new int[t.length()];
        int j = -1;
        int k = 0;
        next[0] = -1;
        next[1] = 0;
        while (j < t.length() - 1) {
            if (t.charAt(j) == t.charAt(k)) {
                next[j + 1] = k + 1;
                j++;
                k++;
            } else if (k == 0) {
                next[j + 1] = 0;
                j++;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    /**
     * 优化后的获取next[]数组的方法
     *
     * @param t 串
     * @return next[]数组
     */
    private static int[] getNextVal(String t) {
        int[] nextVal = new int[t.length()];
        int j = 0;
        int k = -1;
        nextVal[0] = -1;
        while (j < t.length() - 1) {
            if (k == -1 || t.charAt(j) == t.charAt(k)) {
                j++;
                k++;
                if (t.charAt(j) != t.charAt(k)) {
                    nextVal[j] = k;
                } else {
                    nextVal[j] = nextVal[k];
                }
            } else {
                k = nextVal[k];
            }
        }
        return nextVal;
    }
}
