package com.sym.algorithm.question.math;

/**
 * 题目：将一个正整数分解质因数。例如：输入90,打印出90=2*3*3*5。
 *
 * @author shenym
 * @date 2020/3/26 19:20
 */

public class Question3 {
    
    public static void main(String[] args) {
        Question3.resolvePrimeFactor(99);
    }

    /**
     * 将整数分解质因子
     * @param number  待分解数字
     */
    public static void resolvePrimeFactor(int number) {
        // 将10000以内的素数保存在数组中（共1299个），以备作为因数去判断
        int[] primeNum = new int[1300];
        int len_prime = 0;
        for (int i = 2; i <= 10000; i++) {
            if (isPrime(i)) {
                primeNum[len_prime++] = i;
            }
        }
        // 为真，表示number还能继续分解，存在质因数
        while (number != 1) {
            // 对于每次分解从第一个质数开始判断
            for (int i = 0; i < len_prime; i++) {
                // 为真，表示这个质数恰等于number，
                // 输出该质数，并令number = 1表示分解因数过程结束
                if (number == primeNum[i]) {
                    System.out.println(primeNum[i]);
                    number = 1;
                    break;
                }
                // 为真，表示number大于当前质数，判断是否为该数的质因子
                else if (number > primeNum[i]) {
                    // 为真，表示该质数是number的质因子
                    // 输出该质数，并用number除以该质数的商作为新的number
                    if (number % primeNum[i] == 0) {
                        System.out.print(primeNum[i] + " * ");
                        number /= primeNum[i];
                        break;
                    }
                } else {
                    System.out.println("ERROR!");
                }
            }
        }
    }

    /**
     * 判断一个数字是否为素数
     *
     * @param number 待判断数字
     * @return boolean类型, true表示是素数，false表示不是素数
     */
    public static boolean isPrime(int number) {
        // 从2~number的平方根，如果有数字能整除number，说明不是素数，返回false
        for (int i = 2; i <= (int) Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        // 如果2~number的平方根，所有的数都不能整除number，说明是素数，返回true
        return true;
    }
}
