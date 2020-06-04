package com.sym.algorithm.kmp;

/**
 * KMP算法的实现, KMP与BF在比较字符串时大体上方式一样, 毕竟字符串需要一个字符一个字符比较. 但是KMP不同于
 * BF的关键在于, 当某个主串和模式串某个字符不匹配时, BF会回溯模式串的指针到0, 再将主串指针回溯到下一个字符,
 * 然后再继续比较; 而KMP算法的关键在于在主串和模式串某个字符不匹配时, 主串的指针不用动, 模式串的指定根据
 * 一个规则a, 回退到指定位置再进行比较, 而规则a就是kmp算法的核心-next[]数组。
 *
 * 如何求得next[]数组, 需要先理解字符串真前缀和真后缀的概念, 以ababc为例：
 * 它的所有前缀有a, ab, aba, abab, ababc, 其中真前缀有a, ab, aba, abab(即真前缀不包括它自身),
 * 它的所有后缀有c, bc, abc, babc, ababc, 其中真后缀有c, bc, abc, babc(即真后缀不包括它自身).
 * 现在来算ababc的next[]数组是多少？它有5个字符串, 所以next[]数组大小就为5, 每遍历到第i个字符, next[i]的值就等于0~i的子串的最大真前缀和真后缀匹配的长度大小,
 * a, next[0]=-1(-1是一个标识符, 这是为了后面再KMP算法中便于使用而定义的)
 * ab, next[1]=0, 因为b字符不看, 只剩a字符, 所以为0
 * aba, next[2]=0, 因为最后一个字符a不看, 只剩ab, 真前缀为a, 真后缀为b, 没有交集所以为0
 * abab, next[3]=1, 因为最后一个字符b不看, 只剩aba, 真前缀有a ab, 真后缀有ba a, 最大交集为1所以next数组值为1
 * ababc, next[4]=2, 因为最后一个字符c不看, 只剩abab, 真前缀有a ab aba, 真后缀有bab ab b, 最大交集为2所以next数组值为2
 * 这边解释下为啥每求一个位置上的next值时, 当前位置上的字符不看, 因为你想在KMP算法匹配到当前i这个位置不匹配, 是不是去找 0~i这个区间上的next值。
 *
 * 每次KMP字符不匹配时, 就找当前位置i上的next[i]值, 它表示这位置上的最大公共前缀的长度, 就可以省略掉这部分的比对, 直接跳到让模式串指针跳到
 * next[i]指向的值继续比较
 *
 *
 * @author shenyanming
 * Created on 2020/6/2 18:23
 */
public class StringKmp {

    public static void main(String[] args) {
        String s1 = "abcdefabcdf";
        String s2 = "abcdf";

        // kmp求解
        System.out.println(kmp(s1, s2));

        // 暴力求解
        System.out.println(bruteForce(s1, s2));
    }

    /**
     * kmp方式求解
     *
     * @param main    主串
     * @param pattern 模式串
     * @return 返回-1表示不匹配, 其它返回模式串在主串匹配时的第一个字符位置
     */
    public static int kmp(String main, String pattern) {
        // 获取next数组
        int[] next = next(pattern);
        // i, j 分别是主串和模式串的下标,
        int i = -1, j = -1, iLen = main.length(), jLen = pattern.length();
        // 循环终止的条件就是有一方遍历达到临界值
        while(i < iLen && j < jLen){
            if(j == -1 || main.charAt(i) == pattern.charAt(j)){
                // 如果当前字符匹配成功, 无可厚非, 两个指针后增一, 比较下一个字符;
                // 这边当j=-1的时候也要各自加一的原因：模式串指针若回到第一个字符时,
                // 就需要比较主串的下一个字符串, 所以这边要求next数组的next[0]=-1表示一个标识.
                i++;
                j++;
            }else{
                // 如果当前字符不匹配, 根据KMP算法的核心, 主串指针i不回退, 模式串指针j回退到next[j], 也就是
                // 模式串[0, j-1]区间内的最大公共前后缀匹配子串的位置
                j = next[j];
            }
        }
        if(j == jLen){
            return i - j;
        }
        return -1;
    }

    /**
     * 求模式串的next数组,
     * a  b a b c
     * -1 0 0 1 2
     * a,ab,aba,abab
     * babc,abc,bc,c
     *
     * @param pattern 模式串
     * @return 整型数组
     */
    private static int[] next(String pattern) {
        // 模式串有多少个字符, next数组就有多少个
        int len = pattern.length();
        int[] next = new int[len];
        // 标示位, 表示说当模式串返回到下标为0的时候, 主串也要增一
        next[0] = -1;
        int k = -1;
        int j = 0;
        while(j < len -1){
            if(k == -1 || pattern.charAt(k) == pattern.charAt(j)){
                k++;
                j++;
                next[j] = k;
            }else{
                k = next[k];
            }
        }
        return next;
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
}
