package com.sym.util;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数组相关的工具类
 *
 * @author ym.shen
 * Created on 2020/5/8 14:44
 */
public class ArrayUtil {

    /**
     * 打印一维数组
     */
    public static void print(int[] array) {
        if (array == null) {
            return;
        }
        int len = array.length;
        AtomicInteger index = new AtomicInteger(0);
        System.out.print("[");
        Arrays.stream(array).forEach(obj -> {
            index.incrementAndGet();
            System.out.print(obj + (index.intValue() == len ? "" : ","));
        });
        System.out.println("]");
    }

    /**
     * 打印二维数组
     */
    public static void print(int[][] array) {
        if (null == array || array.length == 0) {
            return;
        }
        for (int[] internalArray : array) {
            for (int data : internalArray) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
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
        int temp;
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
