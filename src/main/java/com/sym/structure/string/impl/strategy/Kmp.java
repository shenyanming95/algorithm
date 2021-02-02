package com.sym.structure.string.impl.strategy;

import com.sym.structure.string.IString;

import java.util.Arrays;
import java.util.Objects;

/**
 * KMP算法实现.
 *
 * @author shenyanming
 * Created on 2021/2/1 14:25
 */
public class Kmp implements IString.MatchingStrategy {

    @Override
    public int match(IString text, IString pattern) {
        checkNotNull(text, pattern);
        int tl = text.length(), pl = pattern.length();
        // 模式串的长度比文本串还大, 那肯定匹配不上了
        if (pl > tl || pl == 0) {
            return -1;
        }
        // 获取模式串的next数组
        int[] next = next(pattern);
        // ti指针指向文本串, pi指针指向模式串
        int ti = 0, pi = 0;

        while (ti < tl && pi < pl) {
            if (pi == -1 || Objects.equals(text.charAt(ti), pattern.charAt(pi))) {
                // pi == -1, 是指通过next数组已经回到了next[0]位置, 也就意味着模式串需要前进一步, 去比较文本串的下一个字符;
                // 如果文本串ti位置的字符等于模式串pi位置的字符, 则移动到下一个字符去比较;
                ti++;
                pi++;
                continue;
            }
            // ti位置的字符与pi位置的字符不等, 如果按照蛮力算法, 它会让pi重置为0, ti自增1,
            // 但是kmp算法会让ti暂时不变, 让pi滑动一块距离, 即next[pi]
            pi = next[pi];
        }

        // 跳出循环后, 只要pi的值等于pl, 说明模式串已经匹配完毕, 那么可以肯定模式串确实存在于文本串中.
        return pi == pl ? ti - pi : -1;
    }

    /**
     * 获取模式串的next数组
     *
     *
     * @param pattern 模式串
     * @return next数组
     */
    private int[] next(IString pattern) {
        int n = -1, i = 0, pl = pattern.length();
        // 返回值
        int[] next = new int[pl];
        // 规定next[0]等于-1, 便于kmp主算法逻辑实现
        next[0] = -1;

        // kmp算法关键：让模式串自己跟自己比较, 变量n在这边既作为模式串下标又作为最大公共子串长度,
        // 变量i一直递增用于计算模式串每个位置上的next[i]值.
        while (i < pl - 1) {
            if (n == -1 || Objects.equals(pattern.charAt(n), pattern.charAt(i))) {
                i++;
                n++;
                if (!Objects.equals(pattern.charAt(n), pattern.charAt(i))) {
                    // 这里是对next[]数组做的一个优化, 在计算next[i+1]并且明确其值等于n+1时,
                    // 那么如果主算法在pattern[i+1]匹配失败, 实际下一个要比较的字符就是pattern[n+1],
                    // 如果这两个字符一开始就是不等的, 那就没必要继续比较了. 所以这边预先做一个判断, 如果
                    // pattern[i+1] != pattern[n+1], 就让next[n]的值赋给next[i]
                    next[i] = next[n];
                } else {
                    next[i] = n;
                }
                continue;
            }
            n = next[n];
        }
        return next;
    }

    /**
     * 非空校验
     *
     * @param objects 参数集合
     */
    private void checkNotNull(Object... objects) {
        Arrays.stream(objects).filter(Objects::nonNull).findAny().orElseThrow(IllegalArgumentException::new);
    }
}
