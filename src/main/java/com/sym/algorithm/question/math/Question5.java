package com.sym.algorithm.question.math;

import com.sym.util.TimeUtil;

/**
 * 题目：给定一个数位n, 求得该位上的斐波那契数.
 * 斐波那契数, Fibonacci number, 某位上的数值等于前两位的值之和, 例如：0 1 1 2 3 5 8 13 21....
 *
 * @author shenym
 * @date 2020/3/26 20:52
 */

public class Question5 {

    public static void main(String[] args) {
        // 假设求第45位的斐波那契数
        int i = 45;
        // 递归算法
        TimeUtil.execute("递归算法", () -> {
            int sum = Question5.getFibonacci0(i);
            System.out.println("结果值：" + sum);
        });
        // 循环算法
        TimeUtil.execute("循环算法", () -> {
            int sum = Question5.getFibonacci1(i);
            System.out.println("结果值：" + sum);
        });
        // 特征方程求解
        TimeUtil.execute("特征方程", () -> {
            int sum = Question5.getFibonacci2(i);
            System.out.println("结果值：" + sum);
        });
    }


    /**
     * 通过递归求得某位的斐波那契数.
     * n值不能太大(50以后就开始慢了), 因为方法栈深度太多了
     */
    private static int getFibonacci0(int n) {
        if (n <= 1) {
            // 递归终止条件就是第0位和第1位返回它自己
            return n;
        }
        // 因为n位上的斐波那契数等于前两位之和, 即 n-1 和 n-2 的和, 然后 n-1 位又等于
        // n-2 和 n-3 的和...以此类推, 类似二叉树一样一只扩展, 最后把这些值加起来
        return getFibonacci0(n - 1) + getFibonacci0(n - 2);
    }


    /**
     * 通过循环的方式求斐波那契数.
     * 它是这样一个思想, 假设求第5位, 即0 1 1 2 3, 刚开始第0位和第1位相加得值1, 这个结果就是第2位的值;
     * 然后第1位和第2位相加得值2, 这个结果就是第3位的值...可以发现每次相加后的结果, 都作为下一个位的第二个加数
     */
    private static int getFibonacci1(int n) {
        if (n <= 1) {
            // 这个需要啊保证从第2位开始
            return n;
        }
        // 斐波那契数的前两位的值
        int start = 0;
        int end = 1;
        // 假设求第4位, 就需要相加3次, 所以求第n位(n>1)就需要 n-1 次, 意味着循环 n-1 次
        for (int i = 0; i < n - 1; i++) {
            // 每次都是前两个位的值相加
            int sum = start + end;
            // 加完后, 原先的end就变为start, 而加完后的sum就变为end, 这样就是求下一个位的两个加数
            // 重复这个操作.
            start = end;
            end = sum;
        }
        return end;
    }

    /**
     * 斐波那契额数的线性代数解法-特征方程
     */
    private static int getFibonacci2(int n){
        double c = Math.sqrt(5);
        return (int)((Math.pow((1 + c) / 2, n) - Math.pow((1 - c) / 2, n)) / c);
    }
}
