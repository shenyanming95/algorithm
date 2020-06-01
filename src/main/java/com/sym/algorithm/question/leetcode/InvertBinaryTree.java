package com.sym.algorithm.question.leetcode;

/**
 * 翻转一棵二叉树, 即任意一个节点的左右子节点互换位置, 例如：
 * 输入：
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 *
 * 输出：
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/invert-binary-tree
 *
 * @author shenyanming
 * Created on 2020/5/31 20:59
 */
public class InvertBinaryTree {

    public static void main(String[] args) {

    }

    /**
     * 每个节点都要互换左右子节点, 则需要对二叉树进行遍历, 就可以需要前序遍历、中序遍历、后序遍历和层次遍历
     * 都可以， 但要注意子节点互换后, 进行递归的时候子节点的取值方向
     */
    private static TreeNode invertBinaryTree(TreeNode root){
        if(root == null){
            return null;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertBinaryTree(root.left);
        invertBinaryTree(root.right);
        return root;
    }

    /**
     * 二叉树节点
     */
    static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }


}
