package com.sym.algorithm.question.group2;

/**
 * 题目: 打印出所有的"水仙花数"，所谓"水仙花数"是指一个三位数，其各位数字立方和等于该数本身.
 *       例如153是一个"水仙花数"，因为153= 1^3 + 5^3 + 3^3
 *
 * @author shenym
 * @date 2020/3/26 19:13
 */

public class Algorithm0 {
    
    public static void main(String[] args) {
        Algorithm0.doRun(1, 1000);
    }

    private static void doRun(int start, int end){
        if(start >= end){
            return;
        }
        int i = start;
        int j = end;
        for(; i < j; i++){
            if(isDaffodils(i)){
                System.out.println(i);
            }
        }
    }

    /**
     * 核心函数，判断一个数是不是“水仙花数”
     * @param number 待判断的数
     * @return 如果是水仙花数则返回true，否则返回false
     */
    private static boolean isDaffodils(int number) {
        // 用于保存各位数字的立方和，方便和number比较
        int sum = 0;
        // 将值传给num，避免待会操作到元数据
        int num = number;
        while (num != 0) {
            // 依次计算各个位数的立方
            sum += (num % 10) * (num % 10) * (num % 10);
            // 因为参数都是Int，如果计算结果为10.1有小数点的情况，会自动去掉小数点，变为整数
            num /= 10;
        }
        return sum == number;
    }
}
