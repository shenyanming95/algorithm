package com.sym.algorithm.question.group2;

/**
 * 题目：输入一行字符，分别统计出其中英文字母、空格、数字和其它字符的个数
 *
 * @author shenym
 * @date 2020/3/26 19:29
 */

public class Algorithm1 {
    
    public static void main(String[] args) {
        Algorithm1.display("321 12312sadseqwez$$#34");
    }

    private static void display(String param) {
        int len = param.length();
        int index = 0;
        int tmp = 0;
        int sum_str = 0;
        int sum_num = 0;
        int sum_space = 0;
        int sum_other = 0;
        while (index < len) {
            tmp = param.charAt(index++);
            if (97 <= tmp && tmp <= 122)
                sum_str++;
            else if (65 < tmp && tmp <= 90)
                sum_str++;
            else if (48 <= tmp && tmp <= 57)
                sum_num++;
            else if (tmp == 32)
                sum_space++;
            else
                sum_other++;
        }
        System.out.println("中英文字母有：" + sum_str);
        System.out.println("空格有：" + sum_space);
        System.out.println("数字有：" + sum_num);
        System.out.println("其他字符有：" + sum_other);
        System.out.println();
    }
}
