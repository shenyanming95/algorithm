package com.sym.algorithm.sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * 整型数组排序抽象父类
 *
 * @author shenyanming
 * Created on 2020/9/2 09:57
 */
public abstract class AbstractIntegerSort implements ISort<Integer> {

    public AbstractIntegerSort(int[] array, String algorithm) {
        this.array = Objects.requireNonNull(array);
        this.algorithm = algorithm;
    }

    /**
     * 1ms = 1,000,000 ns
     */
    private static BigDecimal divisor = new BigDecimal(1000000);

    /**
     * 待排序数组
     */
    protected int[] array;

    /**
     * 排序算法名称
     */
    private String algorithm;

    /**
     * 数组元素比较次数
     */
    private int compareCount;

    /**
     * 数组元素交换次数
     */
    private int swapCount;

    /**
     * 花费的时间
     */
    private long costTime;

    @Override
    public Integer[] sort() {
        long start = System.nanoTime();
        internalSort(array);
        long end = System.nanoTime();
        costTime = end - start;
        // int[] 转 integer[]
        return Arrays.stream(array).boxed().toArray(Integer[]::new);
    }

    /**
     * 互换下标位置
     *
     * @param index1 索引1
     * @param index2 索引2
     */
    protected void swap(int index1, int index2) {
        // 互换索引位置
        int tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
        // 交换计数累加1
        swapCount++;
    }

    /**
     * 下标位置覆盖
     *
     * @param index1
     * @param index2
     */
    protected void cover(int index1, int index2) {
        array[index1] = array[index2];
        // 交换计数累加1
        swapCount++;
    }

    /**
     * 比较数组两个元素的大小
     *
     * @param i1 索引1
     * @param i2 索引2
     * @return 大于0, i1>i2; 等于0, i1==i2; 小于0, i1 < i2;
     */
    protected int compareByIndex(int i1, int i2) {
        // 比较计数累加1
        compareCount++;
        return array[i1] - array[i2];
    }

    /**
     * 比较两个值的大小
     *
     * @param v1 第一个值
     * @param v2 第二个值
     * @return 大于0, v1>v2; 等于0, v1==v2; 小于0, v1 < v2;
     */
    protected int compareByValue(int v1, int v2) {
        // 比较计数累加1
        compareCount++;
        return v1 - v2;
    }

    /**
     * 不同排序算法, 重写这个方法
     *
     * @param array 数组
     */
    protected abstract void internalSort(int[] array);

    /**
     * 重置, 重新添加未排序数组, 清空所有统计信息
     *
     * @param array 新数组
     */
    public void reset(int[] array) {
        this.array = Objects.requireNonNull(array);
        this.compareCount = 0;
        this.costTime = 0L;
        this.swapCount = 0;
    }

    /**
     * 通过toString()方法统计排序信息
     */
    @Override
    public String toString() {
        return "[" + algorithm + "]\n" + "比较次数：" + compareCount + "\n" +
                "交换次数：" + swapCount + "\n" +
                "耗时：" + toMilliTime() + " ms" + "\n" +
                "结果：" + buildSortedResult() + "\n" +
                "======================================================\n";
    }

    private double toMilliTime() {
        return new BigDecimal(costTime).divide(divisor, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private String buildSortedResult() {
        StringBuilder sb = new StringBuilder("[");
        Arrays.stream(array).forEach(obj -> {
            sb.append(obj).append(",");
        });
        return sb.deleteCharAt(sb.length() - 1).append("]").toString();
    }
}
