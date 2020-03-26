package com.sym.algorithm.snowFlake;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Twitter的雪花算法，用于分布式系统中生成唯一性ID，算法思想是：
 *
 * 规定一个1位的固定数，一个41位的时间戳，一个5位的数据中心，一个5位的机器编码和一个12位的序列号，组装成一个64位的二进制
 * 而在java中，64位的二进制可以用 long 类型来表示，样子如下：
 *           0      00000.....000000   00000    00000     000000000000
 *        --------  ----------------- -------  --------  -------------
 *        固定为0，    41位表示时间戳    5位表示   5位表示      12位表示
 *        表示符号位                   数据中心   机器编码     序列号
 *
 * 理解这个算法，需要先去理解下Java的位运算（ << 、 &  、 | ）
 *
 * Created by 沈燕明 on 2019/6/5 15:59.
 */
public class SnowFlake {

    /*
     * 设置一个基本时间戳，当前时间戳-基本时间戳作为41位时间戳位
     */
    private static long baseTimeStamp = LocalDateTime.of(2019,1,1,0,0,0).toInstant(ZoneOffset.of("+8")).toEpochMilli();

    private final static long dataCenterBits = 5;// 数据中心的位数
    private final static long machineCodeBits = 5;// 机器编码的位数
    private final static long serialNumBits = 12;// 序列号的位数

    private final static long maxDataCenter = ~(-1 << 5); //数据中心允许的最大值：31（5位二进制最多只能表示32个数，从0开始计数，总共为0-31）
    private final static long maxMachineCode = ~(-1 << 5);//机器编码允许的最大值：31
    private final static long maxSerialNum = ~(-1 << 12);//序列号允许的最大值：4095

    /*
     * 这里需要好好理解下，是一个关键。由前面的介绍知道，机器编码位于序列号的前面，序列号规定为12位，
     * 机器编码本身定义为long类型，它也是64位，但我们逻辑上只允许它只有5位，也就是说它实际上有用的就只有这5位
     * 所以在最后组装64位结果时，我们要把这5位左移12位，到达它组装64位结果的正确位置上
     */
    private static long machineCodeLeftMove = serialNumBits;//机器编码需要左移的位数（12位）
    private static long dataCenterLeftMove = machineCodeLeftMove+machineCodeBits;//数据中心需要左移的位数（17位）
    private static long timeStampLeftMove = dataCenterLeftMove+dataCenterBits;//时间戳需要左移的位数（22位）

    private long dataCenter;//数据中心
    private long machineCode;//机器编码
    private long serialNum;//序列号
    private long lastTimeStamp;//表示上一次获取全局ID时的时间戳

    /**
     * 构造方法需要指定 数据中心和机器编码的值，这两个值合起来必须唯一
     */
    public SnowFlake(long dataCenterId,long machineCode){
        if( dataCenterId > maxDataCenter || maxDataCenter < 0 ){
            throw new IllegalArgumentException("数据中心ID只允许在[0,31]之间,当前值为："+dataCenterId);
        }
        if( machineCode > maxMachineCode || machineCode < 0 ){
            throw new IllegalArgumentException("机器编码只允许在[0,31]之间,当前值为："+machineCode);
        }
        this.dataCenter = dataCenterId;
        this.machineCode = machineCode;
        this.lastTimeStamp = 0L;
        this.serialNum = 0L;
    }

    /**
     * 获取分布式唯一性ID
     */
    public synchronized long nextID(){
        // 当前时间戳
        long nowTimeStamp = System.currentTimeMillis();
        if( nowTimeStamp < lastTimeStamp ){
            throw new RuntimeException("系统时间被后退,拒绝生成id");
        }
        /*
         * 如果当前时间戳与上一次时间戳一样，说明在同一毫秒内，只需要把序列号加1即可
         */
        if( nowTimeStamp == lastTimeStamp ){
            serialNum = serialNum+1 & maxSerialNum;
            /*
             * 如果一个毫秒内，序列号已经达到最大,这边解释一下为啥可以这样来判断serialNum等于0时就已经达到最大值
             *
             * maxSerialNum是long类型变量，但是只有12位有效字节，所以它的最大值就是 0...0 1111 1111 1111
             * 当它加1后就会变成0...1 0000 0000 0000（与运算进1），这样进行与运算后就会变为0
             */
            if( serialNum == 0L ){
                // 更换到下一个毫秒数
                nowTimeStamp = nextTimeStamp();
            }
        }else{
            /*
             * 如果当前时间戳与上一次时间戳不一样，说明不再同一毫秒内，序列号值为0；
             */
            serialNum = 0L;
        }
        lastTimeStamp = nowTimeStamp;
        return ( nowTimeStamp - baseTimeStamp ) << timeStampLeftMove |
                dataCenter << dataCenterLeftMove |
                machineCode << machineCodeLeftMove |
                serialNum;
    }

    private long nextTimeStamp(){
        long l = System.currentTimeMillis();
        while( l <= lastTimeStamp ){
            l = System.currentTimeMillis();
        }
        return l;
    }

}
