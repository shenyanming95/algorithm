package com.sym.algorithm.question.group1;

import com.sym.datastructure.stack.IStack;
import com.sym.datastructure.stack.array.SymArrayStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 分隔符匹配问题，编写判断Java语句中分隔符是否匹配的程序
 *
 * @author ym.shen
 * Created on 2020/5/8 15:22
 */
public class Algorithm3 {
    public static void main(String[] args) {
        DelimiterMatcher matcher = new DelimiterMatcher();
        String string = "(/*1**/)";
        boolean match = matcher.match(string);
        System.out.println("检查结果：" + match);
    }

    static class DelimiterMatcher {
        /*
         * 栈结构，用于保存左分隔符并匹配右分隔符
         */
        private IStack<String> stack = new SymArrayStack<>(100);

        /*
         * 左分隔符返回1，右分隔符返回2，其他分隔符返回3
         */
        private final static int IS_LEAF = 1;
        private final static int IS_RIGHT = 2;
        private final static int OTHER = 3;

        /*
         * 左字符
         */
        private Set<String> leafCharSet = new HashSet<>(Arrays.asList("(", "{", "[", "/*"));

        /*
         * 右字符
         */
        private Set<String> rightCharSet = new HashSet<>(Arrays.asList(")", "}", "]", "*/"));

        /**
         * 匹配检查
         */
        public boolean match(String str) {
            return isLegal(str);
        }

        /**
         * 核心方法，遍历字符串，如果是左边符放到栈中，如果是右边符，取出栈顶元素，如果不匹配返回false，如果匹配继续循环
         */
        private boolean isLegal(String str) {
            for (int i = 0; i < str.length(); i++) {
                String temp = str.charAt(i) + "";
                if (i != str.length() - 1) {
                    boolean flag = ("/".equals(temp) && "*".equals("" + str.charAt(i + 1)))
                            || ("*".equals(temp) && "/".equals("" + str.charAt(i + 1)));
                    if (flag) {
                        temp = temp.concat(str.charAt(++i) + "");
                    }
                }
                int o = determineChar(temp);
                if (o == IS_LEAF) {
                    stack.push(temp);
                } else if (o == IS_RIGHT) {
                    String left = stack.pop();
                    if (stack.isEmpty() || !matchDelimiterChar(left, temp)) {
                        return false;
                    }
                }
            }
            return stack.isEmpty();
        }

        /**
         * 判断指定字符属于哪个分隔符
         *
         * @param str 字符
         */
        private int determineChar(String str) {
            if (leafCharSet.contains(str)) {
                return IS_LEAF;
            } else if (rightCharSet.contains(str)) {
                return IS_RIGHT;
            } else {
                return OTHER;
            }
        }

        /**
         * 匹配分隔符，即"("要与")"匹配，返回true，不匹配返回false
         */
        private boolean matchDelimiterChar(String left, String right) {
            return (("(".equals(left) && ")".equals(right))
                    || ("{".equals(left) && "}".equals(right))
                    || ("[".equals(left) && "]".equals(right))
                    || ("/*".equals(left) && "*/".equals(right)));
        }
    }
}
