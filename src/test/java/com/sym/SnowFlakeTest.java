package com.sym;

import com.sym.algorithm.example.snowflake.SymSnowFlake;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * 1、请记住：-1的二进制为：11111111111111111111111111111111,任何跟-1做异或运算(^),都相当于对其取非(~)
 * 2、请记住：正数取非(~)相当于原值+1后取负数，如 ~10 = -11
 * 负数取非(~)这对于原值取绝对值后-1，如 ~ -10 = 9
 *
 * @author shenyanming
 * @date 2019/6/4 15:41
 */
public class SnowFlakeTest {

    /**
     * java位运算测试
     */
    @Test
    public void testOne() {
        /*
         *  如果原值为正数，正数的二进制，高位在左边，低位在右边，所以左移是往高位移动，原值会变大：
         *
         *  << 左移运算符，将原值（正数）扩大 2的n次幂
         *  >> 右移运算法，将原值（负数）缩小 2的n次幂
         */
        int i1 = 0b1000;
        System.out.println("i1=" + i1);
        //相当于将 8 扩大4倍
        i1 = i1 << 2;
        System.out.println("i1=" + i1);
        //相当于将 32 缩小16倍
        i1 = i1 >> 4;
        System.out.println("i1=" + i1);
    }

    /**
     * java位运算测试
     */
    @Test
    public void testTwo() {
        /*
         *  如果原值为负数，负数的二进制，由于要取倒数，所以原本高位变为低位，低位变为高位，导致左移是往低位移动，原值会变小：
         *
         *  << 左移运算符，将原值（负数）缩小 2的n次幂
         *  >> 右移运算法，将原值（负数）扩大 2的n次幂
         */
        // -8的二进制为 11111111111111111111111111111000
        int i1 = -8;
        //相当于将 -8 缩小4倍，-8*4=-32
        int i2 = i1 << 2;
        System.out.println(i2);
        //相当于将 -8 扩大4倍，-8/4=-2
        i2 = i1 >> 2;
        System.out.println(i2);
    }

    /**
     * java位运算测试
     */
    @Test
    public void testThree() {
        //十进制为10
        int i1 = 0b1010;
        //十进制为12
        int i2 = 0b1100;
        System.out.println("i1=" + i1 + ",i2=" + i2);
        /*
         * | 或运算，两数取二进制值进行位运算，只要有1个位为1，其结果值就为1
         */
        int i3 = i1 | i2; //0b1110 → 14
        System.out.println(i3);
        /*
         * & 与运算，两数取二进制值进行位运算，只有两个位值都为1，其结果值才为1
         */
        int i4 = i1 & i2; //10000 → 8
        System.out.println(i4);
        /*
         * ^ 异或运算，两数取二进制值进行位运算，只有两个位值都不同，其结果值才为1
         */
        int i5 = i1 ^ i2; //0110 → 6
        System.out.println(i5);
        /*
         * ~ 非运算，将原值的二进制的每个位都取反（包括符号位）
         */
        int i6 = ~i1;
        System.out.println(i6);
        printBinaryString(i6);
    }


    /**
     * 单线程环境下测试雪花算法
     */
    @Test
    public void testFour() {
        SymSnowFlake symSnowFlake = new SymSnowFlake(0, 0);
        Set<Long> set = new HashSet<>(1000);
        for (int i = 0; i < 1000; i++) {
            long id = symSnowFlake.nextID();
            if (set.contains(id)) {
                throw new RuntimeException("代码有bug，生成重复ID了,id=" + id);
            } else {
                set.add(id);
                System.out.println(id);
            }
        }
    }

    /**
     * 多线程环境下测试雪花算法
     */
    @Test
    public void testFive() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);
        SymSnowFlake symSnowFlake = new SymSnowFlake(05, 0);
        Set<Long> set = new HashSet<>(3000);
        for (int i = 1; i < 4; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        long id = symSnowFlake.nextID();
                        if (set.contains(id)) {
                            throw new RuntimeException("多线程环境下，生成重复ID了,id=" + id + "，thread=" + Thread.currentThread().getName());
                        } else {
                            set.add(id);
                            System.out.println(Thread.currentThread().getName() + "：" + id);
                        }
                    }
                } finally {
                    latch.countDown();
                }

            }, "线程" + i).start();
        }
        latch.await();
    }


    @Test
    public void testSix() {
        System.out.println(2 & 4);
    }

    private void printBinaryString(int val) {
        System.out.println(Integer.toBinaryString(val));
    }


}
