package com.sym.util;

/**
 * 线程安全的计时工具
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
        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();
        // 纳秒
        long ns = end - start;
        // 毫秒
        long ms = ns / NS_TO_MS;
        // 秒
        long s = ms / MS_TO_S;
        System.out.println(description);
        System.out.println("耗时：" + ns + "ns, " + ms + "ms, " + s + "s");
        System.out.println("*************************");
        System.out.println();
    }
}
