package com.sym.algorithm.sort.impl;

import com.sym.algorithm.sort.AbstractIntegerSort;
import com.sym.util.SymArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 希尔排序.
 * 也称缩小增量排序, 插入排序的改进版, 因为插入排序的效率受到逆序对数量的影响, 而希尔排序的本质就是降低逆序对的数量.
 * 它的思想是, 将未排序的数组按一个<b>步长序列</b>拆分成多列, 每一列单独进行一次插入排序, 当拆分
 * 成1列排序后, 数组就可以变为有序的!~
 * <p>
 * 比如说:
 * 具有17个元素的数组[0...16], 按照规则2的k次方划分步长序列为[8,4,2,1], 表示原数组需要先拆分8列排序,
 * 再拆分4列排序, 再拆分2列排序, 最后拆分为1(其实就是原数组)排序, 最终数组就可以变为有序的!~
 * 第一次拆分为8列, 注意[]描述的数组索引下标, 变为：
 * <pre>
 *     [0,1,2,3,4,5,6,7]
 *     [8,9,10,11,12,13,14,15]
 *     [16]
 *     原数组就被分为了3行, 每行8列. 然后每一列单独进行插入排序, 即
 *     第一次比较[0,8,16]插入排序,
 *     第二次比较[1,9]插入排序,
 *     ...
 * </pre>
 * 第二次拆分为4列, 变为
 * <pre>
 *     [0,1,2,3]
 *     [4,5,6,7]
 *     [8,9,10,11]
 *     [12,13,14,15]
 *     [16]
 *     原数组就被分为了5行, 每行4列. 然后每一列单独进行插入排序, 即
 *     第一次比较[0,4,8,12]插入排序,
 *     第二次比较[1,5,9,13]插入排序,
 *     ...
 * </pre>
 * ...以此类推, 所以希尔排序的关键, 就是定义<b>步长序列</b>, 一个合适的步长序列,
 * 可以有效地发挥希尔排序的核心思想.
 *
 * @author shenyanming
 * Created on 2020/9/23 17:30
 */
public class ShellSort extends AbstractIntegerSort {

    public static void main(String[] args) {
        int[] array = SymArrayUtil.getRandomArray(50);
        ShellSort sort = new ShellSort(array);
        sort.sort();
        SymArrayUtil.print(array);
    }

    public ShellSort(int[] array) {
        super(array, "希尔排序");
    }

    @Override
    protected void internalSort(int[] array) {
        int[] steps = shellStepSequence(array);
        // 按照步长序列, 将数组依次拆分为指定的列数
        for (int step : steps) {
            // step表示要将数组拆分的列数, 分成几列, 就要比较几列
            for (int col = 0; col < step; col++) {
                // 开始比较的位置, 因为插入排序不需要比较第一个位置, 所以直接向后移动一位.
                // 然后依次取本列中下一个需要比较的元素.
                for (int start = col + step; start < array.length; start += step) {
                    // 这边就是简单插入排序的代码: 依次将元素与其左边的各个元素比较
                    int cur = start;
                    int prev = cur - step;
                    while (cur > col && compareByIndex(cur, prev) < 0) {
                        swap(cur, prev);
                        cur = prev;
                    }
                }
            }
        }
    }


    /**
     * 划分数组的步长序列, 这个是关键. 希尔排序的发明者给出的
     * 计算公式是：n/2^k, k=1,2,3..., 换句话说, 17个元素的数组划分为：8-4-2-1.
     *
     * @param array 待排序数组
     * @return 步长序列
     */
    private int[] shellStepSequence(int[] array) {
        int len = array.length;
        List<Integer> list = new ArrayList<>();
        while ((len /= 2) > 0) {
            list.add(len);
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
