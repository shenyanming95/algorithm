package com.sym.algorithm.thought.recursion;

/**
 * 汉诺塔问题
 *
 * @author shenyanming
 * Created on 2021/5/5 12:36.
 */

public class TowersOfHanoi {

    public static void main(String[] args) {
        hanoi(5, 'A', 'B', 'C');
    }

    /**
     * @param n           n个盘子
     * @param startPoint  起点
     * @param middlePoint 中间点
     * @param endPoint    终点
     */
    private static void hanoi(int n, char startPoint, char middlePoint, char endPoint) {
        if (n <= 1) {
            // 只有一个盘子时, 直接从起点移动到终点
            move(1, startPoint, endPoint);
            return;
        }
        // 先将 n-1 个盘子, 从起点移到中间点, 这样第n个盘子才可以移到终点
        hanoi(n - 1, startPoint, endPoint, middlePoint);
        // n - 1个盘子移到中间点以后, 就可以直接将第n个盘子移到终点
        move(n, startPoint, endPoint);
        // 再将 n - 1个盘子, 原先位于中间点, 将其移动到终点
        hanoi(n - 1, middlePoint, startPoint, endPoint);
    }

    private static void move(int number, char startPoint, char endPoint) {
        System.out.println(String.format("将%s号盘子, 从%s移动到%s", number, startPoint, endPoint));
    }
}
