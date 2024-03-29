package com.sym.algorithm.leetcode.array;

import com.sym.util.ArrayUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * @author shenyanming
 * {@link <a href="https://leetcode-cn.com/problems/two-sum">两数之和</a>}
 * Created on 2020/5/12 08:39
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] array = new int[]{2, 10, 40, 14, 67, 55, 21};
        int target = 95;
        int[] ret = twoSum(array, target);
        ArrayUtil.print(ret);
    }

    /**
     * 返回数据
     *
     * @param intArray 数组
     * @param target   目标值
     * @return 符合条件的原数组的下标组合, 若不存在这样的数返回null
     */
    public static int[] twoSum(int[] intArray, int target) {
        Map<Integer, Integer> tempMap = new HashMap<>(intArray.length);
        // 一次遍历处理
        for (int i = 0, len = intArray.length; i < len; i++) {
            int key;
            // 由于要在数组中找到两数和为target, 那么target减去两数中的任意一数
            // 的结果值都可以在原数组中找到:
            if (tempMap.containsKey((key = target - intArray[i]))) {
                // 若当前遍历, 数组存在这样的数, 就返回它们的下标
                return new int[]{tempMap.get(key), i};
            } else {
                // 若当期遍历, 数组不存在这样的数, 则将它的值和它的下标保存在map中
                tempMap.put(intArray[i], i);
            }
        }
        // 遍历结束仍未返回说明数组不存在这样的两数
        return null;
    }
}
