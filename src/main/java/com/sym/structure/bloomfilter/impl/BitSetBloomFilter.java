package com.sym.structure.bloomfilter.impl;

import com.sym.structure.bloomfilter.AbstractBloomFilter;

import java.util.BitSet;

/**
 * 基于JDK自带的{@link java.util.BitSet}的布隆过滤器
 *
 * @author shenyanming
 * Created on 2021/1/16 10:01.
 */

public class BitSetBloomFilter<T> extends AbstractBloomFilter<T> {

    private BitSet bitSet;

    public BitSetBloomFilter(long dataCount, double misjudgmentRate) {
        super(dataCount, misjudgmentRate);
        bitSet = new BitSet(toInt(bitCount));
    }

    @Override
    protected void setBit(long index) {
        bitSet.set(toInt(index));
    }

    @Override
    protected boolean existBit(long index) {
        return bitSet.get(toInt(index));
    }

    private int toInt(long value) {
        return Integer.parseInt(Long.toString(value));
    }
}
