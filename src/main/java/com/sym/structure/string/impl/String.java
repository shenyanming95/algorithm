package com.sym.structure.string.impl;

import com.sym.structure.string.IString;
import com.sym.structure.string.impl.strategy.BruteForce;
import com.sym.structure.string.impl.strategy.Kmp;

import java.util.Objects;

/**
 * 字符串实现.
 *
 * @author shenyanming
 * Created on 2021/2/1 13:41
 */
public class String implements IString {

    public String(java.lang.String string) {
        this(string.toCharArray());
    }

    public String(IString string) {
        this(string.toArray());
    }

    public String(char[] chars) {
        this(chars, null);
    }

    public String(char[] chars, MatchingStrategy strategy) {
        this.chars = Objects.requireNonNull(chars, "char array is null");
        this.strategy = Objects.isNull(strategy) ? MatchingStrategies.KMP : strategy;
    }

    /**
     * 用于{@link #clear()}
     */
    private static char[] EMPTY_CHARS = new char[0];

    /**
     * 字符串底层就是字符数组
     */
    private char[] chars;

    /**
     * 模式匹配策略
     */
    private MatchingStrategy strategy;

    @Override
    public void clear() {
        chars = EMPTY_CHARS;
    }

    @Override
    public boolean isEmpty() {
        return length() == 0;
    }

    @Override
    public int length() {
        return chars.length;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= length() ) {
            throw new IllegalArgumentException("out of index");
        }
        return chars[index];
    }

    @Override
    public IString substring(int begin, int end) {
        if (begin < 0 || end > length()) {
            throw new IllegalArgumentException("out of index");
        }
        if (begin >= end) {
            throw new IllegalArgumentException("wrong index");
        }
        int length = end - begin;
        char[] chars = newChars(length);
        System.arraycopy(this.chars, begin, chars, 0, length);
        return new String(chars);
    }

    @Override
    public IString insert(int offset, IString str) {
        if (offset < 0 || offset >= length()) {
            throw new IllegalArgumentException("error offset");
        }
        if (Objects.isNull(str)) {
            throw new NullPointerException("str is null");
        }
        // 当前字符数组
        char[] localChars = this.chars;
        // 参数字符数组
        char[] paramChars = str.toArray();
        // 新的数组
        char[] newChars = newChars(localChars.length + paramChars.length);

        System.arraycopy(localChars, 0, newChars, 0, offset);
        System.arraycopy(paramChars, 0, newChars, offset, paramChars.length);
        System.arraycopy(localChars, offset, newChars, offset + paramChars.length, localChars.length - offset);
        return new String(newChars);
    }

    @Override
    public IString concat(IString str) {
        int l = length(), n = str.length();
        char[] newChars = newChars(l + n);
        System.arraycopy(this.chars, 0, newChars, 0, l);
        System.arraycopy(str.toArray(), 0, newChars, l, n);
        return new String(newChars);
    }

    @Override
    public int compareTo(IString str) {
        return new java.lang.String(this.chars).compareTo(new java.lang.String(str.toArray()));
    }

    @Override
    public int indexOf(IString str) {
        return strategy.match(this, str);
    }

    @Override
    public char[] toArray() {
        return chars;
    }

    @Override
    public java.lang.String toString() {
        return new java.lang.String(this.chars);
    }

    /**
     * 按序合并两个字符数组
     *
     * @param c1 字符数组
     * @param c2 字符数组
     * @return 新字符数组
     */
    private char[] mergeChars(char[] c1, char[] c2) {
        char[] chars = new char[c1.length + c2.length];
        System.arraycopy(c1, 0, chars, 0, c1.length);
        System.arraycopy(c2, 0, chars, c1.length - 1, c2.length);
        return chars;
    }

    private char[] newChars(int length) {
        return new char[length];
    }

    /**
     * 字符串模式匹配工具类
     */
    public static class MatchingStrategies {

        /**
         * KMP算法
         */
        public static MatchingStrategy KMP = new Kmp();

        /**
         * 暴力算法
         */
        public static MatchingStrategy BF = new BruteForce();
    }
}
