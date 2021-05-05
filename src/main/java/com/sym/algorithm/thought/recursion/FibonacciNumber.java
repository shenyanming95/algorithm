package com.sym.algorithm.thought.recursion;

import com.sym.util.TimeUtil;

/**
 * 题目：给定一个数位n, 求得该位上的斐波那契数.
 * 斐波那契数, Fibonacci number, 某位上的数值等于前两位的值之和, 例如：0 1 1 2 3 5 8 13 21....
 *
 * @author shenym
 * @date 2020/3/26 20:52
 */

public class FibonacciNumber {

    public static void main(String[] args) {
        // 假设求第45位的斐波那契数
        int i = 45;
        // 递归算法
        TimeUtil.execute("递归算法", () -> {
            int sum = FibonacciNumber.fib0(i);
            System.out.println("结果值：" + sum);
        });
        // 数组+递归算法
        TimeUtil.execute("数组递归算法", () -> {
            int sum = FibonacciNumber.fib1(i);
            System.out.println("结果值：" + sum);
        });
        // 循环算法
        TimeUtil.execute("循环算法", () -> {
            int sum = FibonacciNumber.fib2(i);
            System.out.println("结果值：" + sum);
        });
        // 特征方程求解
        TimeUtil.execute("特征方程", () -> {
            int sum = FibonacciNumber.fib3(i);
            System.out.println("结果值：" + sum);
        });
    }


    /**
     * 通过递归求得某位的斐波那契数, 存在大量重复的重复计算, 执行效率低.
     * 并且n值不能太大(50以后就开始慢了), 因为方法栈深度太多了
     */
    private static int fib0(int n) {
        if (n <= 1) {
            // 递归终止条件就是第0位和第1位返回它自己
            return n;
        }
        // 因为n位上的斐波那契数等于前两位之和, 即 n-1 和 n-2 的和, 然后 n-1 位又等于
        // n-2 和 n-3 的和...以此类推, 类似二叉树一样一只扩展, 最后把这些值加起来
        return fib0(n - 1) + fib0(n - 2);
    }


    /**
     * 直接使用递归, 通过递归树可以看出来存在大量的重复计算, 所以可以通过数组保存,
     * 减少这些重复计算.
     */
    private static int fib1(int n){
        if (n < 2) {
            // 斐波那切数列, 第0位等于0, 第1位等于1.
            return n;
        }
        // 通过数组存储计算的中间值, 以避免重复计算, 但是会额外耗费空间
        int[] arr = new int[n + 1];
        arr[0] = 0;
        arr[1] = 1;
        return fib1(n, arr);
    }
    private static int fib1(int n, int[] array){
        if (array[n] == 0 && n > 0) {
            // 说明数组没有保存中间值, 需要计算
            array[n] = fib1(n - 1, array) + fib1(n - 2, array);
        }
        return array[n];
    }


    /**
     * 通过循环的方式求斐波那契数.
     * 它是这样一个思想, 假设求第5位, 即0 1 1 2 3, 刚开始第0位和第1位相加得值1, 这个结果就是第2位的值;
     * 然后第1位和第2位相加得值2, 这个结果就是第3位的值...可以发现每次相加后的结果, 都作为下一个位的第二个加数
     */
    private static int fib2(int n) {
        if (n <= 1) {
            return n;
        }
        // 斐波那契数的前两位的值
        int first = 0;
        int second = 1;

        for (int i = 2; i <= n; i++) {
            second = first + second;
            first = second - first;
        }
        return second;
    }

    /**
     * 斐波那契额数的线性代数解法-特征方程
     */
    private static int fib3(int n){
        double c = Math.sqrt(5);
        return (int)((Math.pow((1 + c) / 2, n) - Math.pow((1 - c) / 2, n)) / c);
    }
}
