package com.sym.structure.bloomfilter.impl;

import com.sym.structure.bloomfilter.AbstractBloomFilter;
import lombok.AllArgsConstructor;

/**
 * 基于Long数组实现的布隆过滤器
 *
 * @author shenyanming
 * Created on 2021/1/16 10:00.
 */

public class LongArrayBloomFilter<T> extends AbstractBloomFilter<T> {

    /**
     * 每个 long 有8个字节, 可以表示64位
     */
    private long[] longArray;

    public LongArrayBloomFilter(int dataCount, double misjudgmentRate) {
        // 通过父类计算需要的二进制位总数量
        super(dataCount, misjudgmentRate);
        // 初始化整型数组
        int size = bitCount / Long.SIZE;
        longArray = new long[++size];
    }

    @Override
    protected void setBit(int index) {
        // 计算出要使用long数组中的哪一个long、哪一位
        Pair pair = compute(index);
        // 取出数组中的对应的long数
        long l = longArray[pair.index];
        // 因为1与任何数进行或运算, 都是1, 所以我们将1右移指定位数,
        // 跟原先的long进行或运算, 就可以将指定位置的二进制位置为1.
        l |= 1L << pair.bit;
        // 最后要记得将计算后的long放回到数组中
        longArray[pair.index] = l;
    }

    @Override
    protected boolean existBit(int index) {
        // 计算出要使用long数组中的哪一个long、哪一位
        Pair pair = compute(index);
        // 取出数组中的对应的long数
        long l = longArray[pair.index];
        // 与setBit()方法相反, 指定位置为1, 其它位置为0, 如果进行与运算后,
        // 值还大于0, 说明原先的值就为1, 反之就是0.
        return (l & (1L << pair.bit)) > 0;
    }

    /**
     * 计算bit对应在long数组中哪一个位置
     *
     * @param index 需要放置的bit位
     * @return <下标, 位数>
     */
    private Pair compute(int index) {
        // 首先, 确定long数组的下标, 即 使用哪一个long
        int idx = index / Long.SIZE;
        // 其次, 从选出的long中, 定位它是处于哪一位
        int num = index % (Long.SIZE - 1);
        return Pair.of(idx, num);
    }

    @AllArgsConstructor
    private static class Pair {
        /**
         * 表示long数组下标
         */
        int index;

        /**
         * 一个long里面的第几位
         */
        int bit;

        static Pair of(int index, int bit) {
            return new Pair(index, bit);
        }
    }
}
