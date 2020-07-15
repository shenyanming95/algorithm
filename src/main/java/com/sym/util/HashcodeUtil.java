package com.sym.util;

/**
 * @author shenyanming
 * Created on 2020/7/15 10:51
 */
public class HashcodeUtil {

    /**
     * 整数值的哈希码就是它自身
     *
     * @param value 整数值
     * @return 哈希值
     */
    public static int hashcode(Integer value) {
        return value;
    }

    /**
     * 长整型的哈希码, 就是让它的高32位和低32位进行异或, 这样能保证长整型对应二进制的
     * 每个位都能够充分比较. 最后将结果强转为int, 只保留异或结果的低32位
     *
     * @param value 长整型
     * @return 哈希码
     */
    public static int hashcode(Long value) {
        return (int) ((value >>> 32) ^ value);
    }

    /**
     * 单精度浮点数的哈希码. 根据 IEEE-754 规定, 单精度浮点数在计算机表示为：
     * X = (-1)^S * M * R^E, 实际就是一个二进制串. 所以通过它可以表示出一个
     * 整数, 这个整数就是单精度浮点数的哈希码
     *
     * @param value 单精度浮点数
     * @return 哈希码
     */
    public static int hashcode(Float value) {
        return Float.floatToIntBits(value);
    }

    /**
     * 双精度浮点数的哈希码. 根据 根据 IEEE-754 规定, 双精度浮点数在计算机表示为：
     * X = (-1)^S * M * R^E, 实际就是一个二进制串. 通过它可以表示出一个长整数,
     * 再对这个长整数做hashcode
     *
     * @param value 双精度浮点数
     * @return 哈希码
     */
    public static int hashcode(Double value) {
        // 通过 double 转换出 long, 再对long求hashcode即可
        long l = Double.doubleToLongBits(value);
        return hashcode(l);
    }

    /**
     * 字符串的哈希码, 其实可以拆分成每个字符单独取hashcode, 可以参照整数取hashcode的过程, 例如：
     * 9527 = 9 * 10^3 + 5 * 10^2 + 2 * 10^1 + 7 * 10^0, 所以一个字符串"abcd"也可以表示成：
     * 'a' * n^3 + 'b' * n^2 + 'c' * n^1 + 'd' * n^0. 问题就给到了如何定义n值为多少?JDK是
     * 使用了31, 因为它既是奇数又是素数, 而且31 = 32 - 1 = 2^5 - 1, 即任何数乘以31都等于它自身
     * 左移5位减去它自身：i * 31 = i << 5 - i
     *
     * 上面的表达式等价转换下, 其计算方式为：((((('a' * n) + 'b') * n) + 'c') * n) + 'd', 实际上就是：
     * 上一个字符的hashcode * n + 当前字符 = 当前字符的hashcode.
     *
     * @param value 字符串
     * @return 哈希值
     */
    public static int hashcode(String value){
        int hashcode = 0;
        for(int i = 0, length = value.length(); i < length; i++){
            char c = value.charAt(i);
            hashcode = (hashcode * 31) + c;
        }
        return hashcode;
    }
}
