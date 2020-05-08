package com.sym.algorithm.sort;

import com.sym.util.SymArrayUtil;

/**
 * 选择排序。
 * 循环第一次，遍历数组(0~n)，将最小值放在数组下标为0的位置上 循环第二次，遍历数组(1~n)，将最小值放在数组下标为1的位置上
 * 循环第三次，遍历数组(2~n)，将最小值放在数组下标为2的位置上 ... ...
 * 循环n-1次，遍历数组(n-1~n)，将最小值放在数组下标为n-1的位置上，完成排序
 *
 * @author ym.shen
 */
public class SelectSort {

    /**
     * 排序
     */
    public static int[] sort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                int min = arr[i];
                int temp = arr[j];
                if (temp < min) {
                    arr[i] = temp;
                    arr[j] = min;
                }
            }
        }
        return arr;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        int[] arr = {50, 45, 120, 5, 4551, 12, 454, 502, 16, 3, 232};

        System.out.println("排序前...");
        SymArrayUtil.print(arr);

        SelectSort.sort(arr);

        System.out.println("排序后...");
        SymArrayUtil.print(arr);
    }
}
