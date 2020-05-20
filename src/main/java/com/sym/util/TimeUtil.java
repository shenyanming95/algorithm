package com.sym.util;

import lombok.SneakyThrows;

import java.util.function.Supplier;

/**
 * 计时工具
 *
 * @author shenym
 * @date 2020/3/26 18:37
 */

public class TimeUtil {

    private final static long NS_TO_MS = 1000000;
    private final static long MS_TO_S = 1000;

    /**
     * 同步执行 描述
     */
    public static void execute(String description, Runnable task) {
        timing(description, task, null);
    }

    /**
     * 同步执行 描述
     */
    @SneakyThrows
    public static <T> void execute(String description, Supplier<T> supplier) {
        timing(description, null, supplier);
    }

    private static <T> void timing(String description, Runnable r, Supplier<T> supplier){
        long start = System.nanoTime();
        T t = null;
        if(null != r){
            r.run();
        }
        if(null != supplier){
            t = supplier.get();
        }
        long end = System.nanoTime();
        // 纳秒、毫秒、秒
        long ns = end - start;
        long ms = ns / NS_TO_MS;
        long s = ms / MS_TO_S;
        System.out.println(description);
        if(null != t){
            System.out.println("[执行结果]：" + t);
        }
        System.out.println("[消耗时间]：" + ns + "ns, " + ms + "ms, " + s + "s");
        System.out.println("**********************************************");
        System.out.println();
    }
}
