package com.sym.algorithm.leetcode.geometry;

/**
 * 给你一个以 (radius, xCenter, yCenter) 表示的圆和一个与坐标轴平行的矩形 (x1, y1, x2, y2) ，其中 (x1, y1) 是矩形左下角的坐标，而 (x2, y2) 是右上角的坐标。
 * 如果圆和矩形有重叠的部分，请返回 true ，否则返回 false。即检测是否 存在 点 (xi, yi) ，它既在圆上也在矩形上（两者都包括点落在边界上的情况）。
 *
 * @author shenyanming
 * {@link <a href="https://leetcode.cn/problems/circle-and-rectangle-overlapping">圆和矩形是否有重叠</a>}
 * Created on 2023/6/25 20:26.
 */

public class CheckOverlap {
    public static void main(String[] args) {
        System.out.println(new CheckOverlap().checkOverlap(1, 1, 1, -3, -3, 3, 3));
    }

    public boolean checkOverlap(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {
        if (xCenter > x1 && xCenter < x2 && yCenter > y1 && yCenter < y2) {
            return true;
        }

        // (x1->x2, y1/y2 这两条边)
        int x = x1;
        for (; x <= x2; x++) {
            if (isOverLap(radius, xCenter, yCenter, x, y1)) {
                return true;
            }
            if (isOverLap(radius, xCenter, yCenter, x, y2)) {
                return true;
            }
        }

        // (x1/x2, y1->y2 这两条边)
        int y = y1;
        for (; y <= y2; y++) {
            if (isOverLap(radius, xCenter, yCenter, x1, y)) {
                return true;
            }
            if (isOverLap(radius, xCenter, yCenter, x2, y)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断一个坐标点是否处于圆中
     */
    private boolean isOverLap(int radius, int xCenter, int yCenter, int x, int y) {
        // 欧几里得(Euclidean Distance Formula)距离公式: d = 平方根((x - x1)^2 + (y - y1)^2)
        int distance = (int) Math.sqrt(Math.pow((x - xCenter), 2) + Math.pow((y - yCenter), 2));
        // 小于等于圆的半径, 说明就在圆上
        return distance <= radius;
    }
}
