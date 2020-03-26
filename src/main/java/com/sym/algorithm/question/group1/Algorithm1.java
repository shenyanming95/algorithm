package com.sym.algorithm.question.group1;

/**
 * 问题：输入两个正整数m和n，求其最大公约数和最小公倍数
 *
 * @author shenym
 * @date 2020/3/26 19:25
 */

public class Algorithm1 {
    
    public static void main(String[] args) {
        int num1 = 17;
        int num2 = 34;
        System.out.println("最大公约数：" + getMaxDivisor(num1, num2));
        System.out.println("最小公倍数：" + getMinMultiple(num1, num2));
    }

    /**
     * 求最大公约数
     *
     * @param num1 第一个参数
     * @param num2 第二个参数
     * @return 返回这两个参数的最大公约数
     */
    public static int getMaxDivisor(int num1, int num2) {
        int result = 0;
        int min = Math.min(num1, num2);
        for (int i = 2; i <= min; i++) {
            if (num1 % i == 0 && num2 % i == 0) {
                if (i > result) {
                    // 符合能整除两个参数且比上一个公约数大
                    result = i;
                }
            }
        }
        return result;
    }

    /**
     * 求最小公倍数
     *
     * @param num1 第一个参数
     * @param num2 第二个参数
     */
    public static int getMinMultiple(int num1, int num2) {
        int small = Math.min(num1, num2);
        int big = num1 * num2;
        for (; small <= big; small++) {
            if (small % num1 == 0 && small % num2 == 0) {
                return small;
            }
        }
        return big;
    }
}
