package com.sym;

import com.sym.algorithm.example.cache.impl.LruCache;
import org.junit.Test;

/**
 * 缓存测试类
 *
 * @author shenyanming
 * Created on 2020/5/20 17:48
 */
public class CacheTest {

    /**
     * 基于{@link java.util.LinkedHashMap}实现的 LRU cache 测试
     */
    @Test
    public void test01(){
        LruCache<String, String> lruCache = new LruCache<>(3);
        lruCache.set("aa", "aa");
        lruCache.set("bb", "bb");
        lruCache.set("cc", "cc");

        // 此时容量正好3个, 所以不会触发清除操作
        System.out.println(lruCache);

        // 添加了新的元素, 已经超过最大值, 会清除最少使用的节点,
        // 由于aa是最早加入的, 所以它就会被剔除
        lruCache.set("dd", "dd");
        System.out.println(lruCache);

        // 再添加新的元素, 按道理应该会剔除bb(因为它第二个加入),
        // 但是我们在这里先获取下它, 它就变为最近使用的节点, 就不会被剔除
        // 反而, cc节点会被剔除
        lruCache.get("bb");
        lruCache.set("ee", "ee");
        System.out.println(lruCache);
    }
}
