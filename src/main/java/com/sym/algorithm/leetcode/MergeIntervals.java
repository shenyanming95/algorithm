package com.sym.algorithm.leetcode;

import com.sym.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 给出一个区间的集合, 合并所有重叠的区间.例如：
 * 输入: [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 *
 * 输入: [[1,4],[4,5]]
 * 输出: [[1,5]]
 * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-intervals
 *
 * @author shenym
 * @date 2020/3/26 19:33
 */

public class MergeIntervals {

    public static void main(String[] args) {
        // [1, 3], [2, 6], [8, 10], [15, 18]
        int[][] data = new int[][]{new int[]{1, 3}, new int[]{2, 6}, new int[]{8, 10}, new int[]{15, 18}};
        merge(data);
    }

    /**
     * 题目前提是保证了：按开始时间从⼩小到⼤大排序.所以只要前一个数组的下标为1的元素,
     * 大于下一个数组下标为0的元素, 就可以将它们合并起来
     */
    private static void merge(int[][] intervals) {
        // 结果集
        List<int[]> list = new ArrayList<>();
        // 临时对象
        int[] temp = null;

        for (int i = 0, len = intervals.length; i < len; ) {
            // 如果已经达到最后位置了, 判断此时temp是否有值, 有值就直接加入;
            // 为null则将数组的最后元素添加进行
            if (i == len - 1) {
                if (null != temp) {
                    list.add(temp);
                } else {
                    list.add(intervals[i]);
                }
                break;
            }
            // 前一个数组
            int[] frontArray = null == temp ? intervals[i] : temp;
            // 后一个数组
            int[] behindArray = intervals[i + 1];

            if (frontArray[1] >= behindArray[0]) {
                // 如果数组相交，判断与下一个数组相交
                temp = new int[]{frontArray[0], behindArray[1]};
            } else {
                // 如果区间不相交，直接添加到结果集
                list.add(frontArray);
                temp = null;
            }
            i++;
        }
        // 处理结果集
        int[][] result = new int[list.size()][2];
        for (int i = 0, size = list.size(); i < size; i++) {
            result[i] = list.get(i);
        }
        ArrayUtil.print(result);
    }
}
