package com.sym.algorithm.leetcode.string;

/**
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M
 * <pre>
 *     字符          数值
 *      I             1
 *      V             5
 *      X             10
 *      L             50
 *      C             100
 *      D             500
 *      M             1000
 * </pre>
 * 例如:
 * 2 写做II，即为两个并列的 1;
 * 12 写做XII，即为X+II;
 * 27 写做XXVII, 即为XX+V+II;
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做IIII，而是IV。
 * 数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I可以放在V(5) 和X(10) 的左边，来表示 4 和 9。
 * X可以放在L(50) 和C(100) 的左边，来表示 40 和90。
 * C可以放在D(500) 和M(1000) 的左边，来表示400 和900。
 * 给定一个罗马数字，将其转换成整数。
 *
 * @author shenyanming
 * {@link <a href="https://leetcode.cn/problems/roman-to-integer/">罗马数字转整数</a>}
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
