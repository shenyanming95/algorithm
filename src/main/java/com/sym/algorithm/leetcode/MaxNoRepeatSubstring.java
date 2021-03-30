package com.sym.algorithm.leetcode;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串, 找出其中不含有重复字符的<b>最长子串</b>的长度.例如：
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 *
 * @author shenyanming
 * Created on 2020/5/27 18:27
 */
public class MaxNoRepeatSubstring {

    public static void main(String[] args){
        // 自定义实现
        String string = "lengthOfLongestSubstring";
        Pair<Integer, String> pair = getMaxSubstring(string);
        System.out.println(pair);

        // leetcode官方
        int i = lengthOfLongestSubstring(string);
        System.out.println(i);
    }

    /**
     * 选择字符串中的第k个字符作为起始位置，并且得到了不包含重复字符的最长子串的结束位置为r
     * ​那么当我们选择第 k+1 个字符作为起始位置时，首先从 k+1 到 r
     * 的字符显然是不重复的，并且由于少了原本的第 k 个字符，我们可以尝试继续增大 r 直至再次出现重复字符
     */
    private static Pair<Integer/*长度*/, String/*子串内容*/> getMaxSubstring(String string){
        // 用于判断重复字符
        Set<Character> existSet = new HashSet<>();
        // 最大不重复子串
        String result = "";
        // 滑动窗口的前后指针
        int front = 0, back = 0, len = string.length();
        // 遍历字符串的每一个字符, 每次都以它为起点, 统计最大子串长度
        for(int i = 0; i < len; i++){
            while(back < len && !existSet.contains(string.charAt(back))){
                // 如果不存在重复字符, 就将当前字符添加到set集合中
                existSet.add(string.charAt(back));
                // 移动back指针
                back++;
            }
            // 如果跳出循环, 要么是出现重复字符了, 要么是back已到len-1位置,
            // 此时front所在的字符就要换掉了, 因为它已经出现重复字符了, 将其增一处理原字符串的下一个字符
            String substring = string.substring(front, back);
            if(substring.length() > result.length()){
                result = substring;
            }
            // 移除原先的front所在的字符, 然后将其自增
            existSet.remove(string.charAt(front++));
        }
        return Pair.of(result.length(), result);
    }



    /**
     * leetcode官方题解
     */
    private static int lengthOfLongestSubstring(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

}
