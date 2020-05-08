package com.sym.algorithm.sort;

import com.sym.util.SymArrayUtil;

/**
 * 快速排序。
 * 取数组的某一个数为基数(可以取首个数也可以取随机值)，定义2个变量start,end分别指向数组的首下标和尾下标，让end依次递减与基数比较，
 * 若发现比基数小，与start指向的数互换，end保持不变转而让start依次递加与基数比较，若发现比基数大，与end指向的数互换位置，start
 * 保持不变转而让end递减比较...以此类推直至start==end，第一次快速排序得到的结果：比基数小的位于基数左边，比基数大的位于基数右边，
 * 第一次排序后，数组可以分为两部分，对这两部分在按照上面的排序方式进行排序...直至所有部分都排序完毕
 *
 * @author ym.shen
 */
public class QuickSort {


    public static void sort(int[] arr) {
        QuickSort.sort(arr, 0, arr.length - 1);
    }

    /**
     * 递归方法，当start == end 时，递归停止
     *
     * @param arr   待排序数组
     * @param start 待排序起始点
     * @param end   待排序终止点
     */
    private static void sort(int[] arr, int start, int end) {
        if (start == end) {
            return;
        }
        int left = start;
        int right = end;
        int base = arr[start];
        boolean flag = true;
        while (left != right) {
            if (flag) {
                if (arr[right] > base) {
                    right--;
                } else {
                    arr[left++] = arr[right];
                    flag = false;
                }
            } else {
                if (arr[left] < base) {
                    left++;
                } else {
                    arr[right--] = arr[left];
                    flag = true;
                }
            }
        }
        arr[left] = base;
        // 递归执行左边
        if (--left < start) {
            left = start;
        }
        sort(arr, start, left);
        // 递归执行右边
        if (++right > end) {
            right = end;
        }
        sort(arr, right, end);
    }

    public static void main(String[] args) {

        int[] arr = {50, 45, 120, 5, 4551, 12, 454, 502, 16, 3, 232};
        System.out.println("未排序前...");
        SymArrayUtil.print(arr);

        QuickSort.sort(arr, 0, arr.length - 1);

        System.out.println("排序后...");
        SymArrayUtil.print(arr);
    }
}
