package com.sym.structure.bloomfilter;

import java.util.Collection;
import java.util.Objects;

/**
 * 抽象的布隆过滤器实现
 *
 * @author shenyanming
 * Created on 2021/1/16 9:39.
 */

public abstract class AbstractBloomFilter<T> implements IBloomFilter<T> {

    /**
     * 总的二进制位
     */
    protected long bitCount;

    /**
     * 哈希函数个数
     */
    protected int hashCount;

    /**
     * 预估数据量
     */
    protected long dataCount;

    /**
     * 误判率
     */
    protected double misjudgmentRate;

    /**
     * 构造布隆过滤器
     *
     * @param dataCount       数据规模
     * @param misjudgmentRate 允许的误判率, (0, 1)
     */
    protected AbstractBloomFilter(long dataCount, double misjudgmentRate) {
        initialize(dataCount, misjudgmentRate);
        this.dataCount = dataCount;
        this.misjudgmentRate = misjudgmentRate;
    }

    @Override
    public void put(T t) {
        Objects.requireNonNull(t, "element is null");
        // 根据参数生成2个hash值
        int hash1 = t.hashCode();
        int hash2 = hash1 >>> 16;
        for (int i = 0; i < hashCount; i++) {
            // 根据哈希函数计算二进制位 位置
            long index = computeIndex(hash1, hash2, i);
            // 设置位
            setBit(index);
        }
    }

    @Override
    public void put(Collection<T> collection) {
        Objects.requireNonNull(collection, "collection is null");
        collection.forEach(this::put);
    }

    @Override
    public boolean contains(T t) {
        // 根据参数生成2个hash值
        int hash1 = t.hashCode();
        int hash2 = hash1 >>> 16;
        // 要验证的哈希函数
        for (int i = 0; i < hashCount; i++) {
            // 计算二进制位下标
            long index = computeIndex(hash1, hash2, i);
            // 只要有一个位不存在, 那就说明这个元素一定不存在
            if (!existBit(index)) {
                return false;
            }
        }
        // 可能存在, 会有一定的误判率
        return true;
    }


    /**
     * 计算二进制位数量和哈希函数数量
     *
     * @param dataCount       数据规模
     * @param misjudgmentRate 误判率
     */
    private void initialize(long dataCount, double misjudgmentRate) {
        if (dataCount <= 0 || (misjudgmentRate <= 0 || misjudgmentRate >= 1)) {
            throw new IllegalArgumentException("valid parameter");
        }
        double ln2 = Math.log(2);
        // 计算所需的二进制位数量
        this.bitCount = (long) ((dataCount * Math.log(misjudgmentRate)) / (ln2 * ln2));
        // 计算所需的哈希函数数量
        this.hashCount = (int) (bitCount * ln2 / dataCount);
    }

    /**
     * 根据哈希码计算索引位置
     *
     * @param hash1 first hashcode
     * @param hash2 second hashcode
     * @param salt  盐值
     * @return index
     */
    private long computeIndex(int hash1, int hash2, int salt) {
        // 计算hashCode
        int combinedHash = hash1 + (salt * hash2);
        if (combinedHash < 0) {
            // 越界时, 按位取反
            combinedHash = ~combinedHash;
        }
        return combinedHash % bitCount;
    }

    /**
     * 将指定位置{@code index}的二进制位置为1
     *
     * @param index 位置
     */
    protected abstract void setBit(long index);

    /**
     * 判断指定位置{@code index}是否存在, 即值是否为1
     *
     * @param index 位置
     * @return true-值为1, 表示存在
     */
    protected abstract boolean existBit(long index);
}
