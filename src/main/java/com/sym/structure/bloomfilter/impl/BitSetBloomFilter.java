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

    public BitSetBloomFilter(int dataCount, double misjudgmentRate) {
        super(dataCount, misjudgmentRate);
        bitSet = new BitSet(bitCount);
    }

    @Override
    protected void setBit(int index) {
        bitSet.set(index);
    }

    @Override
    protected boolean existBit(int index) {
        return bitSet.get(index);
    }

}
