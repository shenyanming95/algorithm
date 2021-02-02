package com.sym.structure.string.impl.strategy;

import com.sym.structure.string.IString;

/**
 * 蛮力算法
 *
 * @author shenyanming
 * Created on 2021/2/1 15:37
 */
public class BruteForce implements IString.MatchingStrategy {

    @Override
    public int match(IString text, IString pattern) {
        // 指针i,j分别指向主串和模式串, iLen,jLen分别表示主串和模式串的长度
        int i = 0, j = 0, il = text.length(), jl = pattern.length();
        // 循环条件就是未达到任意一个串的长度大小
        while (i < il && j < jl) {
            if (text.charAt(i) == pattern.charAt(j)) {
                // 如果当前字符相等, 就一起加1, 比较下一个字符
                i++;
                j++;
            } else {
                // 如果当前字符不等, 主串移到下一个字符, 模式串回溯成0, 继续比较
                i = i - j + 1;
                j = 0;
            }
        }
        return j == jl ? i - j : -1;
    }

}
