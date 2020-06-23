package com.sym.algorithm.kmp;

import com.sym.util.RandomUtil;
import com.sym.util.TimeUtil;

/**
 * KMP算法的实现, KMP与BF(暴力匹配)在比较字符串时大体上方式一样, 毕竟字符串确实需要一个字符一个字符比较. 但是KMP不同于BF的关键：
 * <p>
 * 当某个主串和模式串某个字符不匹配时, BF会回溯模式串的指针到0, 再将主串指针回溯到下一个字符,然后再继续比较;
 * 而KMP算法的关键在于在主串和模式串某个字符不匹配时, 主串的指针不用动, 模式串的指定根据一个规则a, 回退到指定位置再进行比较,
 * 而规则a就是kmp算法的核心 → next[]数组
 * </p>
 * <p>
 * 所以KMP算法的关键, 就变为如何求得 next[] 数组. 在这之前需要先理解字符串真前缀和真后缀的概念, 以'ababc'为例：
 * 它的所有前缀有a, ab, aba, abab, ababc, 其中真前缀有a, ab, aba, abab(即真前缀不包括它自身)；
 * 它的所有后缀有c, bc, abc, babc, ababc, 其中真后缀有c, bc, abc, babc(即真后缀不包括它自身).
 * 现在来算'ababc'的 next[] 数组是多少？它有5个字符, 所以 next[] 大小就为5, 每遍历到第i个字符, next[i]的值就等于 0~i 的子串的真前缀和真后缀匹配的最大长度,
 * a, next[0]=-1(-1是一个标识符, 这是为了后面再KMP算法中便于使用而定义的);
 * ab, next[1]=0, 因为b字符不看, 只剩a字符, 所以为0;
 * aba, next[2]=0, 因为最后一个字符a不看, 只剩ab, 真前缀为a, 真后缀为b, 没有交集所以为0;
 * abab, next[3]=1, 因为最后一个字符b不看, 只剩aba, 真前缀有a ab, 真后缀有ba a, 最大交集为a, 所以next数组值为1;
 * ababc, next[4]=2, 因为最后一个字符c不看, 只剩abab, 真前缀有a ab aba, 真后缀有bab ab b, 最大交集为ab, 所以next数组值为2;
 * <b>这边解释下为啥每求一个位置上的next值时, 当前位置上的字符不看, 因为你想在KMP算法匹配到当前i这个位置不匹配, 是不是只会去找 0~ (i-1) 这个区间上的next值</b>
 * 每次字符不匹配时, 就找当前位置i上的next[i]值, 它表示这位置上的最大公共前缀的长度, 就可以省略掉这部分的比对, 直接跳到让模式串指针跳到next[i]指向的值继续比较
 * </p>
 *
 * @author shenyanming
 * Created on 2020/6/2 18:23
 */
public class StringKmp {

    public static void main(String[] args) {
        String s1 = RandomUtil.getRandomString(1024);
        String s2 = RandomUtil.getRandomString(52);

        // kmp求解
        int[] next = next(s2);
        TimeUtil.execute("kmp求解", () -> kmp(s1, s2, next));

        // 暴力求解
        TimeUtil.execute("bf求解", () -> bruteForce(s1, s2));
    }

    /**
     * 暴力求解
     *
     * @param main    主串
     * @param pattern 模式串
     * @return 返回-1表示不匹配, 反之返回第一个字符在主串所在的位置
     */
    public static int bruteForce(String main, String pattern) {
        // 指针i,j分别指向主串和模式串, iLen,jLen分别表示主串和模式串的长度
        int i = 0, j = 0, iLen = main.length(), jLen = pattern.length();
        // 循环条件就是未达到任一个串的长度大小
        while (i < iLen && j < jLen) {
            if (main.charAt(i) == pattern.charAt(j)) {
                // 如果当前字符相等, 就一起赠一, 比较下一个字符
                i++;
                j++;
            } else {
                // 如果当前字符不等, 主串移到下一个字符, 模式串回溯成0, 继续比较
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == jLen) {
            return i - j;
        } else {
            return -1;
        }
    }

    /**
     * kmp方式求解, 自己算next[]数组
     *
     * @param main    主串
     * @param pattern 模式串
     * @return 返回-1表示不匹配, 其它返回模式串在主串匹配时的第一个字符位置
     */
    public static int kmp(String main, String pattern) {
        return kmp(main, pattern, next(pattern));
    }

    /**
     * kmp方式求解, 提供next[]数组
     *
     * @param main    主串
     * @param pattern 模式串
     * @param next    模式串的next[]数组
     * @return 返回-1表示不匹配, 其它返回模式串在主串匹配时的第一个字符位置
     */
    public static int kmp(String main, String pattern, int[] next) {
        // 获取next数组
        if (next == null) {
            next = next(pattern);
        }
        // i, j 分别是主串和模式串的下标,
        int i = -1, j = -1, iLen = main.length(), jLen = pattern.length();
        // 循环终止的条件就是有一方遍历达到临界值
        while (i < iLen && j < jLen) {
            if (j == -1 || main.charAt(i) == pattern.charAt(j)) {
                // 如果当前字符匹配成功, 无可厚非, 两个指针后增一, 比较下一个字符;
                // 这边当j=-1的时候也要各自加一的原因：模式串指针若回到第一个字符时,
                // 就需要比较主串的下一个字符串, 所以这边要求next数组的next[0]=-1表示一个标识.
                i++;
                j++;
            } else {
                // 如果当前字符不匹配, 根据KMP算法的核心, 主串指针i不回退, 模式串指针j回退到next[j], 也就是
                // 模式串[0, j-1]区间内的最大公共前后缀匹配子串的位置
                j = next[j];
            }
        }
        if (j == jLen) {
            return i - j;
        }
        return -1;
    }

    /**
     * 求模式串的next数组, 因为每次在主串和模式串不匹配的时候, 假设在当前字符n不匹配了, 其实
     * 会从 0~(n-1) 前缀字符中取到回溯的位置. 所以在构造的next数组时, 其实计算是到前一个字符
     * 为止的字符串, 举个例子, next[i] 其实算的是 0~(i-1) 的子串的最大前缀匹配数.
     * a b a b c
     * -1 0 0 1 2
     * a,ab,aba,abab
     * babc,abc,bc,c
     *
     * @param pattern 模式串
     * @return 整型数组
     */
    private static int[] next(String pattern) {
        // 模式串的每一个字符都有一个对应的next值, 所以模式串有多少个字符, next数组就有多少个；
        // 变量k表示j位置上的next值, j对应模式串pattern的每个字符的下标.
        int len = pattern.length(), k = -1, j = 0;

        // 创建next[]数组, 并指定next[0]=-1, 这样做的目的是为了当模式串回溯到next[0]这个位置时,
        // 让主串的指针递增1, 比较下一个主串字符, 所以它是起到一个标识的作用.
        int[] next = new int[len];
        next[0] = -1;

        // 这边比较难理解, 需要让模式串自己跟自己比较, 刚开始k=-1, j=0, 所以循环开始next[1]固定等于0, 这也就是说：
        // next[0]=-1, next[1]=0 是已经固定好了. 实际上next[]要从下标为2(也就是第三个字符开始)才会真正比较, 此时k=0, j=2.
        while (j < len - 1) {
            if (k == -1 || pattern.charAt(k) == pattern.charAt(j)) {
                k++;
                j++;
                next[j] = k;
            } else {
                k = next[k];
            }
        }
        return next;
    }
}
