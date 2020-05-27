package com.sym.algorithm.question.math;

import java.util.HashSet;
import java.util.Set;

/**
 * 题目：一个数如果恰好等于它的因子之和，这个数就称为"完数" .
 * 例如6=1＋2＋3.编程 找出1000以内的所有完数
 *
 * @author shenym
 * @date 2020/3/26 19:30
 */

public class Question4 {
    
    public static void main(String[] args) {
        Question4.getPerfectNumber(1000);
    }

    /**
     * 找出从 2 ~ number 的完数
     *
     */
    private static void getPerfectNumber(int number) {
        for (int i = 2; i < number; i++) {
            Set<Integer> tmp = getFactors(i);
            if (!tmp.isEmpty()) {
                int sum = 0;
                for (Integer integer : tmp) {
                    sum += integer;
                }
                if (sum == i) {
                    System.out.println(i);
                }
            }
        }
    }

    /**
     * 求某数所有的真因子（即除了自身以外的约数）
     *
     * @param num 待计算的数
     * @return 真因子集合
     */
    private static Set<Integer> getFactors(int num) {
        Set<Integer> factors = new HashSet<>();
        // 保证元数据不受污染
        int index = num;
        for (int i = 1; i < index; i++) {
            if (index % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }
}
