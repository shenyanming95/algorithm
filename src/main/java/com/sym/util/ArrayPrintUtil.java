package com.sym.util;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数组信息打印工具类
 *
 * Created by shenYm on 2019/9/3.
 */
public class ArrayPrintUtil {

    /**
     * 打印一维数组
     * @param array
     */
    public static void print(int[] array){
        if( array == null ) return;
        int len = array.length;
        AtomicInteger index = new AtomicInteger(0);
        System.out.print("[");
        Arrays.stream(array).forEach(obj->{
            index.incrementAndGet();
            System.out.print(obj+( index.intValue() == len?"":"," ));
        });
        System.out.println("]");
    }


    /**
     * 打印二维数组
     * @param array
     */
    public static void print(int[][] array){
        if( null == array || array.length == 0 )return;
        for( int[] internalArray : array ){
            for( int data : internalArray ){
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }
    }



}
