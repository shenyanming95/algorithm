package com.sym.algorithm.leetcode.list;

/**
 * 给出两个非空的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，并且
 * 它们的每个节点只能存储 一位 数字。如果将这两个数相加起来，则会返回一个新的链表来表示它们的和.
 * 可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 例如：输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * 注意：要考虑 int 类型的溢出, 所以不能简单地进行加减.
 *
 * @author shenyanming
 * {@link <a href="https://leetcode-cn.com/problems/add-two-numbers">两数相加</a>}
 * Created on 2020/5/13 08:38
 */
public class AddTwoNumbers {

    public static void main(String[] args) {
        String s1 = "4,5,2,4,2,2,4,9,2,1";
        String s2 = "5,6,4";
        ListNode listNode = addTwoNumbers(initList(s1), initList(s2));
        System.out.println(listNode);
    }

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

    /**
     * 解题思路就是类似手算加减乘除一样, 按位就算, 最大的数也就 9 + 9 + 1 = 19
     *
     * @param first  链表1
     * @param second 链表2
     * @return 相加后的链表
     */
    public static ListNode addTwoNumbers(ListNode first, ListNode second) {
        ListNode resultNode = null;
        ListNode currentNode = null;
        ListNode n1 = first;
        ListNode n2 = second;
        int carry = 0;
        while (n1 != null || n2 != null) {
            int v1 = n1 == null ? 0 : n1.val;
            int v2 = n2 == null ? 0 : n2.val;
            int sum = v1 + v2 + carry;
            // 表示进位
            carry = (sum / 10);
            // 表示节点值
            int val = (sum % 10);

            // 创建节点
            if (null == resultNode) {
                resultNode = new ListNode(val);
                currentNode = resultNode;
            } else {
                currentNode.next = new ListNode(val);
                currentNode = currentNode.next;
            }
            if (n1 != null) {
                n1 = n1.next;
            }
            if (n2 != null) {
                n2 = n2.next;
            }
        }
        return resultNode;
    }

    public static ListNode initList(String numberString) {
        ListNode resultNode = null;
        ListNode currentNode = null;
        for (String number : numberString.split(",")) {
            int n = Integer.parseInt(number);
            if (null == resultNode) {
                resultNode = new ListNode(n);
                currentNode = resultNode;
            } else {
                currentNode.next = new ListNode(n);
                currentNode = currentNode.next;
            }
        }
        return resultNode;
    }
}
