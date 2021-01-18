package com.sym;

import com.sym.structure.bloomfilter.IBloomFilter;
import com.sym.structure.bloomfilter.impl.BitSetBloomFilter;
import com.sym.structure.bloomfilter.impl.LongArrayBloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 布隆过滤器测试类
 *
 * @author shenyanming
 * Created on 2021/1/16 15:33.
 */
@Slf4j
public class BloomFilterTest {

    @Test
    public void test01() {
        run(new BitSetBloomFilter<>(100_0000, 0.1));
    }

    @Test
    public void test02() {
        run(new LongArrayBloomFilter<>(100_0000, 0.1));
    }

    private void run(IBloomFilter<Integer> bloomFilter) {
        // 预先塞入100万个数字
        for (int i = 0; i < 100_0000; i++) {
            bloomFilter.put(i);
        }

        // 50W ~ 60W 之间的数字一定存在于布隆过滤器之间
        List<Integer> warnList = new ArrayList<>();
        for (int i = 50_0000; i < 60_0000; i++) {
            if (!bloomFilter.contains(i)) {
                warnList.add(i);
            }
        }
        int size = warnList.size();
        log.info("误判数: {}, 误判率: {}", size, size / 100_0000);

        // 理论上来说, 100万以后的数字都不在布隆过滤器中
        warnList.clear();
        for (int i = 100_0001; i < 150_0000; i++) {
            if (bloomFilter.contains(i)) {
                warnList.add(i);
            }
        }
        size = warnList.size();
        log.info("误判数: {}, 误判率: {}", size, size / 100_0000);
    }
}
