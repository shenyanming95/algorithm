package com.sym.structure.bloomfilter.impl;

import com.sym.structure.bloomfilter.AbstractBloomFilter;

/**
 * 基于Long数组实现的布隆过滤器
 *
 * @author shenyanming
 * Created on 2021/1/16 10:00.
 */

public class LongArrayBloomFilter<T> extends AbstractBloomFilter<T> {

    public LongArrayBloomFilter(long dataCount, double misjudgmentRate) {
        super(dataCount, misjudgmentRate);
    }

    @Override
    protected void setBit(long index) {

    }

    @Override
    protected boolean existBit(long index) {
        return false;
    }
}
