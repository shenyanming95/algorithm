package com.sym.algorithm.example.sort.impl;

import com.sym.algorithm.example.sort.AbstractIntegerSort;
import com.sym.util.ArrayUtil;

/**
 * 堆排序.
 * 先对原数组进行堆化, 借助堆的性质(假设最大堆), 数组index=0的元素必定为最大值,
 * 将其与数组末尾元素交换位置, 这就相当于将数组最大值移动到数组末尾; 接着再对堆顶
 * 元素进行下滤, 重新恢复最大堆的性质, 然后重复之前的操作, 将堆顶元素index=0与
 * 数组倒数第二的元素交换位置...重复这个步骤, 直到堆的元素为1.
 *
 * @author shenyanming
 * Created on 2020/9/2 11:40
 */
public class HeapSort extends AbstractIntegerSort {

    public static void main(String[] args) {
        int[] arr1 = ArrayUtil.getRandomArray(50);
        ArrayUtil.print(arr1);
        ArrayUtil.print(sort(arr1));
    }

    public HeapSort(int[] array) {
        super(array, "堆排序");
    }

    @Override
    protected void internalSort(int[] array) {
        // 跟 sort() 方法一样
        int heapSize = array.length;
        // 原数组建堆
        heapify(array);
        while(heapSize > 0){
            // 首尾交换, 将值最大的元素移到数组末尾
            swap(0, heapSize -1);
            // 堆元素数量减一, 因为数组最后一个元素已经有序,
            // 所以只要前面 heapSize -1 个元素重新堆化即可.
            heapSize -= 1;
            // 由于将最后一个元素移到堆顶 , 所以对其进行下滤
            siftDown(array, 0, heapSize);
        }
    }

    private static int[] sort(int[] array){
        int heapSize = array.length;
        // 原数组建堆
        heapify(array);
        //
        while(heapSize > 0){
            // 首尾交换, 将值最大的元素移到数组末尾
            int tmp = array[0];
            array[0] = array[heapSize -1];
            array[heapSize - 1] = tmp;
            //
            heapSize -= 1;
            siftDown(array, 0, heapSize);
        }
        return array;
    }

    /**
     * 对原数组建堆
     */
    private static void heapify(int[] array){
        for (int i = (array.length >> 1); i >= 0; i--) {
            siftDown(array, i, array.length);
        }
    }

    private static void siftDown(int[] heap, int index, int size){
        int el = heap[index];
        for (; ; ) {
            // 获取最大子元素下标
            int cIndex = maxChildIndex(heap, index, size);
            if (cIndex == -1) {
                // 没有子元素就跳出循环
                break;
            }
            // 与最大子元素比较大小
            if (el - heap[cIndex] >= 0) {
                // 如果比最大子元素还大, 就不用再下滤了, 跳出循环
                break;
            }
            // 比最大子元素还小, 则与最大子元素互换位置, 然后继续下滤
            heap[index] = heap[cIndex];
            index = cIndex;
        }
        heap[index] = el;
    }

    private static int maxChildIndex(int[] heap, int index, int size) {
        // 左子元素索引
        int left = (index << 1) + 1;
        if (left < size) {
            // 左子元素存在, 判断右子元素存在
            int right = left + 1;
            if (right < size) {
                // 右子元素也存在
                return heap[left] - heap[right] > 0 ? left : right;
            } else {
                return left;
            }
        } else {
            // 左右子元素都不存在
            return -1;
        }
    }
}
