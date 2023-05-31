package com.sym.algorithm.leetcode;

/**
 * 链接：https://leetcode.cn/problems/roman-to-integer/
 * 罗马数字转整数.
 *
 * @author shenyanming
 * Create on 2023/5/29 下午7:10
 */
public class RomanToInt {

    public static void main(String[] args) {
        RomanToInt obj = new RomanToInt();
        System.out.println(obj.romanToInt("III"));
    }

    public int romanToInt(String s) {
        int sum = 0;
        int preNum = getValue(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int num = getValue(s.charAt(i));
            if (preNum < num) {
                sum -= preNum;
            } else {
                sum += preNum;
            }
            preNum = num;
        }
        sum += preNum;
        return sum;
    }

    private int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

//    public int romanToInt(String s) {
//        if (s == null || s.length() < 1 || s.length() > 15) {
//            return -1;
//        }
//        int sum = 0, len = s.length();
//        for (int i = 0; i < len; ) {
//            Integer v = null;
//            if (i + 2 <= len) {
//                v = getV(s.substring(i, i + 2));
//            }
//            if (v == null) {
//                v = getV(s.substring(i, i + 1));
//                i += 1;
//            } else {
//                i += 2;
//            }
//            sum += v;
//        }
//        return sum;
//    }
//
//    private Integer getV(String s) {
//        switch (s) {
//            case "I":
//                return 1;
//            case "IV":
//                return 4;
//            case "IX":
//                return 9;
//            case "V":
//                return 5;
//            case "X":
//                return 10;
//            case "XL":
//                return 40;
//            case "XC":
//                return 90;
//            case "L":
//                return 50;
//            case "C":
//                return 100;
//            case "CD":
//                return 400;
//            case "CM":
//                return 900;
//            case "D":
//                return 500;
//            case "M":
//                return 1000;
//            default:
//                return null;
//        }
//    }
}
