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
        System.out.println(new CheckOverlap().checkOverlapV2(1, 1, 1, -3, -3, 3, 3));
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
     * 官方解法, 总结一下, 其实就是这几种情况:
     * 1.圆在矩形内部
     * 2.圆在矩形上面的边
     * 3.圆在矩形下面的边
     * 4.圆在矩形右面的边
     * 5.圆在矩形左面的边
     * 6.圆在矩形的左上角
     * 7.圆在矩形的右上角
     * 8.圆在矩形的左下角
     * 9.圆在矩形的右下角
     * 当这九种情况都不满足, 说明圆和矩形肯定不相交.
     */
    public boolean checkOverlapV2(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {
        // 1.圆在矩形内部
        if (xCenter > x1 && xCenter < x2 && yCenter > y1 && yCenter < y2) {
            return true;
        }
        // 2.圆在矩形上面的边
        if (xCenter >= x1 && xCenter <= x2 && y2 <= yCenter && (yCenter - y2) <= radius) {
            return true;
        }
        // 3.圆在矩形下面的边
        if (xCenter >= x1 && xCenter <= x2 && yCenter <= y1 && (yCenter - y1) <= radius) {
            return true;
        }
        // 4.圆在矩形右面的边
        if (yCenter >= y1 && yCenter <= y2 && x2 <= xCenter && (xCenter - x2) <= radius) {
            return true;
        }
        // 5.圆在矩形左面的边
        if (yCenter >= y1 && yCenter <= y2 && x1 <= xCenter && (xCenter - x1) <= radius) {
            return true;
        }
        // 6.圆在矩形的左上角(圆点与左上角的直线距离小于等于半径)
        if (edf(xCenter, yCenter, x1, y2) <= radius) {
            return true;
        }
        // 7.圆在矩形的右上角
        if (edf(xCenter, yCenter, x2, y2) <= radius) {
            return true;
        }
        // 8.圆在矩形的左下角
        if (edf(xCenter, yCenter, x1, y1) <= radius) {
            return true;
        }
        // 9.圆在矩形的右下角
        if (edf(xCenter, yCenter, x2, y1) <= radius) {
            return true;
        }
        // 无相交
        return false;
    }

    /**
     * 判断一个坐标点是否处于圆中
     */
    private boolean isOverLap(int radius, int xCenter, int yCenter, int x, int y) {
        long distance = edf(xCenter, yCenter, x, y);
        // 小于等于圆的半径, 说明就在圆上
        return distance <= radius;
    }

    /**
     * 欧几里得距离公式: d = 平方根((x - x1)^2 + (y - y1)^2)
     * (Euclidean Distance Formula)
     */
    private long edf(int xCenter, int yCenter, int x, int y) {
        return (long) Math.sqrt(Math.pow((x - xCenter), 2) + Math.pow((y - yCenter), 2));
    }
}
