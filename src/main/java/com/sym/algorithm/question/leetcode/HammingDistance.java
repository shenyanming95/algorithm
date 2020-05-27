package com.sym.algorithm.question.leetcode;

/**
 * 题目：给出两个整数, 计算它们之间的汉明距离(Hamming distance).
 * 例如：10 和 7，它们的二进制为分别为 00001010、00000111, 所以它们的汉明距离为：3
 *
 * 何为汉明距离？汉明距离是两个字符串对应位置的不同字符的个数,
 * 换句话说，它就是将一个字符串变换成另外一个字符串所需要替换的字符个数.
 * 题目地址：https://leetcode-cn.com/problems/hamming-distance/
 *
 * @author ym.shen
 * Created on 2020/5/8 16:30
 */
public class HammingDistance {

    public static void main(String[] args) {
        int number1 = 9527;
        int number2 = 7;
        int count;

        // 使用方式1
        count = hammingDistance1(number1, number2);
        System.out.println(count);

        // 使用方式2
        count = hammingDistance2(number1, number2);
        System.out.println(count);

        // 使用方式3
        count = hammingDistance3(number1, number2);
        System.out.println(count);
    }

    /**
     * 通过java自带的api就可以计算出汉明距离
     * @param number1 数1
     * @param number2 数2
     * @return 数1和数2的汉明距离
     */
    public static int hammingDistance1(int number1, int number2){
        // 异或运算法则：0⊕0=0，1⊕0=1，0⊕1=1，1⊕1=0（同为0，异为1）
        // 其实两数的异或结果的二进制, 位为1的个数, 就是两数的汉明距离，所以问题就转为计算两数异或结果,
        // 二进制位为1的个数, java自带的Integer.bitCount()方法就可以统计了...
        return Integer.bitCount(number1 ^ number2);
    }

    /**
     * 先计算两数的异或结果, 然后将结果每个位置都右移1位, 每次右移后都对2求余, 统计余数为1的数量, 就是两数的汉明距离
     * @param number1 数1
     * @param number2 数2
     * @return 数1和数2的汉明距离
     */
    public static int hammingDistance2(int number1, int number2){
        int xor = number1 ^ number2;
        int count = 0;
        // 每次右移1位, 其实就是将原值除以2, 所以循环终止条件就是原值为0
        while(xor != 0){
            if(xor % 2 == 1){
                count++;
            }
            xor = xor >> 1;
        }
        return count;
    }

    /**
     * 这个算法思想是对方式2的改进, 方式2需要一位一位比较, 但是如果中间隔了很多个0其实是没必要比较,
     * 所以可以让原值减1后与原值进行与运算, 不断执行这个流程直至运算结果为0, 执行了多少次就表示原值
     * 有多个二进制位为1的位数, 即为两数的汉明结果
     *
     * @param number1 数1
     * @param number2 数2
     * @return 数1和数2的汉明距离
     */
    public static int hammingDistance3(int number1, int number2){
        // 仍然需要计算两数之间的异或结果
        int xor = number1 ^ number2;
        // 统计次数
        int count = 0;

        // 例如异或结果为10, 它的二进制为0000 1010, 将其减一后得到9, 它的二进制为0000 1001
        // 对它们进行与运算得到 0000 1000, 这样就消掉了10的一个1; 接着再将 0000 1000 减一, 得到0000 0111,
        // 再对它们进行与运算得到 0000 0000, 这样又消掉了一个1, 整个过程执行了两次, 说明了10的二进制位为1的个数为2.
        while(xor != 0){
            xor = xor & xor -1;
            count++;
        }
        return count;
    }
}
