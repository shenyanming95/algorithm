package com.sym.algorithm.question.string;

/**
 * 题目：给定一个字符串例如word, 判断另一个子串是否可以通配. 例如：
 *      子串"4"可以匹配"word", 子串"w3"也可以匹配"word", 子串"w3d"不可以匹配, 因为它要求w和d之间隔3个字符
 *
 * @author shenym
 * @date 2020/3/26 19:37
 */

public class Question1 {

    public static void main(String[] args) {
        // s = “internationalization"， abbr = "i12iz4n
        String s1 = "internationalization";
        String s2 = "i12iz4n1";
        System.out.println(valid(s1,s2));
    }

    static boolean valid(String word, String abbr){
        int m = word.length(), n = abbr.length(), p = 0, cnt = 0;
        for (int i = 0; i < n; ++i) {
            if (abbr.charAt(i) >= '0' && abbr.charAt(i) <= '9') {
                if (cnt == 0 && abbr.charAt(i) == '0') return false;
                cnt = 10 * cnt + abbr.charAt(i) - '0';
            } else {
                p += cnt;
                if (p >= m || word.charAt(p++) != abbr.charAt(i)) return false;
                cnt = 0;
            }
        }
        return p + cnt == m;
    }

    static boolean valid1(String word, String abbr){
        Integer number = isOnlyNumber(abbr);
        if(null != number){
            // 如果abbr只是数字, 判断word是不是符合它的大小
            return word.length() == number;
        }else{
            // abbr不是单纯数字
            int abbrLength = abbr.length();
            // 保存abbr的一个数字的大小, 因为字符串数字是一个一个字符, 需要合并处理
            int abbrNumber = 0;
            // 当abbr的数字获取到后, 就需要跟word子串隔开addrNumber后的字符比较, 所以定义一个变量保存word的原字符的位置
            int oldIndex = 0;
            // 遍历子串abbr
            for(int i = 0; i < abbrLength; i++){
                char temp = abbr.charAt(i);
                if(temp >= '0' && temp <= '9'){
                    // 如果当前字符是数字, 记录它的数字大小
                    abbrNumber = 10 * abbrNumber + temp - '0';
                }else{
                    // 如果当前字符串不是数字, 比较
                    oldIndex += abbrNumber;
                    if(oldIndex > word.length() || word.charAt(oldIndex) != abbr.charAt(i)){
                        return false;
                    }
                    oldIndex ++;
                    abbrNumber = 0;
                }
            }
            // 如果abbr子串的后面都是数字, 则需要比较
            return oldIndex + abbrNumber == word.length();
        }
    }

    static Integer isOnlyNumber(String abbr){
        try{
            return Integer.valueOf(abbr);
        }catch(Exception e){
            return null;
        }
    }
}
