package com.sym.util;

import java.util.Random;

/**
 * 测试数组排序用到的工具类
 *
 * @author user
 */
public class ArraySortUtil {

    /**
     * 打印数组信息
     */
    public static void printArr(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }


    /**
     * 获取指定长度的随机数组
     */
    public static int[] getRandomArray(int len) {
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = (i + 1);
        }
        Random random = new Random();
        int temp = 0;
        for (int i = 0; i < len / 2; i++) {
            int x = random.nextInt(len);
            int y = random.nextInt(len);
            temp = res[x];
            res[x] = res[y];
            res[y] = temp;
        }
        return res;
    }
}
