package com.sym.algorithm.example.sort.impl;

import com.sym.algorithm.example.sort.AbstractIntegerSort;
import com.sym.util.ArrayUtil;

/**
 * 快速排序, 采用分治思想.
 * 取数组的某一个数为基数(可以取首个数也可以取随机值)，定义2个变量start, end分别指向数组的首下标和尾下标，让end依次递减与基数比较，
 * 若发现比基数小，与start指向的数互换，end保持不变转而让start依次递加与基数比较，若发现比基数大，与end指向的数互换位置，start
 * 保持不变转而让end递减比较...以此类推直至start==end，第一次快速排序得到的结果：比基数小的位于基数左边，比基数大的位于基数右边，
 * 第一次排序后，数组可以分为两部分，对这两部分在按照上面的排序方式进行排序...直至所有部分都排序完毕
 *
 * @author shenyanming
 * Created on 2020/9/2 13:44
 */
public class QuickSort extends AbstractIntegerSort {

    public static void main(String[] args) {
        int[] array = ArrayUtil.getRandomArray(50);
        QuickSort sort = new QuickSort(array);
        ArrayUtil.print(array);
        sort.internalSort(array);
        ArrayUtil.print(array);
    }

    public QuickSort(int[] array) {
        super(array, "快速排序");
    }

    @Override
    protected void internalSort(int[] array) {
        //sortV1(array, 0, array.length - 1);
        sortV2(array, 0, array.length);
    }

    /**
     * 快速排序实现版本一, 通过定义个布尔变量flag来决定左边比较,还是右边比较,
     * 参数的startIndex和endIndex都表示下标, 即是一个闭区间[starIndex, endIndex]
     *
     * @param array      待排序数组
     * @param startIndex 待排序起始点
     * @param endIndex   待排序终止点
     */
    private void sortV1(int[] array, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            return;
        }
        int left = startIndex;
        int right = endIndex;
        int base = array[startIndex];
        boolean flag = true;
        while (left != right) {
            if (flag) {
                if (compareByValue(array[right], base) > 0) {
                    right--;
                } else {
                    cover(left, right);
                    left++;
                    flag = false;
                }
            } else {
                if (compareByValue(array[left], base) < 0) {
                    left++;
                } else {
                    cover(right, left);
                    right--;
                    flag = true;
                }
            }
        }
        array[left] = base;
        // 递归执行左边
        if (--left < startIndex) {
            left = startIndex;
        }
        sortV1(array, startIndex, left);
        // 递归执行右边
        if (++right > endIndex) {
            right = endIndex;
        }
        sortV1(array, right, endIndex);
    }

    /**
     * 递归实现版本二, 通过三个while循环来决定向左比较还是向右比较.
     * 参数的start和end是左闭右开区间的关系, 即[start, end)
     *
     * @param array 待排序数组
     * @param start 起始索引(包含)
     * @param end   终止索引(不包含)
     */
    private void sortV2(int[] array, int start, int end) {
        if (end - start < 2) {
            // 如果元素数量只剩一个, 就没必要再比较了, 直接返回
            return;
        }
        // 在[start, end)数组区间范围内定位锚点元素的下标位置
        int pivotIndex = pivotIndex(array, start, end);
        // 递归调用, 以pivotIndex分割, 对数组左边元素进行快速排序, 即[start, pivotIndex)
        sortV2(array, start, pivotIndex);
        // 递归调用, 以pivotIndex分割, 对数组右边元素进行快速排序, 即[pivotIndex + 1, end)
        sortV2(array, pivotIndex + 1, end);
    }

    /**
     * 递归实现版本二, 确定锚点元素在"未来排好序"的数组中的实际下标位置
     *
     * @param array 待排序数组
     * @param start 起始索引
     * @param end   终止索引
     * @return 锚点元素的索引
     */
    private int pivotIndex(int[] array, int start, int end) {
        // 获取锚点元素, 为了排列均匀, 可以采取随机取值的做法
        int base = array[start];
        // 获取终止下标
        end--;

        while (start < end) {
            // 循环执行右边
            while (start < end) {
                // 这边只能使用小于, 而不能使用小于等于.
                // 因为当数组的元素都与锚点元素值相等时, 如果是小于等于, 则end一直递减, 则锚点元素
                // 每次都会定位到start位置, 然后下一次递归就定位到start + 1位置, 最终导致快速排序
                // 退化为O(n^2). 但是如果是小于的话, 可以使元素分布更加均匀, 每遇到值相等元素就执行
                // else语句块, 交由左边执行...
                if (compareByValue(base, array[end]) < 0) {
                    end--;
                } else {
                    // 用end指定的元素去覆盖start的元素, 然后start++, 开始从左边比较.
                    cover(start++, end);
                    // 执行左边
                    break;
                }
            }

            // 循环执行左边
            while (start < end) {
                // 这边用大于的原因跟上面的理由一样
                if (compareByValue(base, array[start]) > 0) {
                    start++;
                } else {
                    // 用start指定的元素去覆盖end的元素, 然后end--, 开始从右边比较.
                    cover(end--, start);
                    // 执行右边
                    break;
                }
            }
        }

        // 跳出循环说明, start==end, 数组两边都已经整理有序
        array[start] = base;
        return start;
    }
}
