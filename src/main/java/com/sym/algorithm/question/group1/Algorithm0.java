package com.sym.algorithm.question.group1;

/**
 * 题目： 输入两个数，判断这两个数之间有多少个素数，并输出所有素数
 *
 * @author shenym
 * @date 2020/3/26 19:04
 */

public class Algorithm0 {
    
    public static void main(String[] args) {
        Algorithm0.doRun(1, 9999);
        //TimeUtil.execute("判断素数", ()-> Algorithm0.doRun(1, 9999));
    }

    public static void doRun(int start, int end){
        if(end <= start){
            return;
        }
        int i = start;
        int j = end;
        for(; i < j; i++){
            if(isPrime(i)){
                System.out.println(i);
            }
        }
    }

    /**
     * 核心函数，判断一个数是不是素数
     *
     * @param number 判断此数是不是素数
     * @return 如果number是素数返回true, 否则返回false
     */
    public static boolean isPrime(int number) {
        // 素数的求法是除了1和他本身，不可以在整除
        for (int i = 2; i <= number - 1; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
