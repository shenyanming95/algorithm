package com.sym.algorithm.question.group1;

/**
 * 给出两个非空的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，
 * 并且它们的每个节点只能存储 一位 数字。如果将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 例如：输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 *      输出：7 -> 0 -> 8
 *      原因：342 + 465 = 807
 * 注意：要考虑 int 类型的溢出, 所以不能简单地进行加减
 * 题目地址：https://leetcode-cn.com/problems/add-two-numbers
 *
 * @author shenyanming
 * Created on 2020/5/13 08:38
 */
public class Algorithm4 {


    /**
     * 链表节点
     */
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[").append(val).append(", ");
            ListNode tempNext = next;
            while (tempNext != null) {
                sb.append(tempNext.val).append(", ");
                tempNext = tempNext.next;
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("]");
            return sb.toString();
        }
    }
}
