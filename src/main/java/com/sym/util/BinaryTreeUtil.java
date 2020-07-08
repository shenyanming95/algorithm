package com.sym.util;

import com.sym.structure.tree.avl.AvlTree;
import com.sym.structure.tree.bst.BinarySearchTree;
import com.sym.structure.tree.rbt.RedBlackTree;

/**
 * 二叉树工具类
 *
 * @author shenyanming
 * @date 2020/5/31 15:01.
 */

public class BinaryTreeUtil {

    private final static String SPLIT_CHARACTER = ",";

    /**
     * 创建二叉搜索树
     *
     * @param nodeString 节点串
     */
    public static BinarySearchTree<Integer> newBinarySearchTree(String nodeString) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        String[] nodeArray = nodeString.split(SPLIT_CHARACTER);
        for (String nodeValue : nodeArray) {
            if ("".equals(nodeValue.replace(" ", ""))) {
                continue;
            }
            bst.add(Integer.parseInt(nodeValue));
        }
        return bst;
    }

    /**
     * 创建AVL树
     *
     * @param nodeString 节点串
     */
    public static AvlTree<Integer> newAvlTree(String nodeString) {
        AvlTree<Integer> avl = new AvlTree<>();
        String[] nodeArray = nodeString.split(SPLIT_CHARACTER);
        for (String nodeValue : nodeArray) {
            if ("".equals(nodeValue.replace(" ", ""))) {
                continue;
            }
            avl.add(Integer.parseInt(nodeValue));
        }
        return avl;
    }

    /**
     * 创建红黑树
     *
     * @param nodeString 节点值串
     */
    public static RedBlackTree<Integer> newRedBlackTree(String nodeString) {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        String[] nodeArray = nodeString.split(SPLIT_CHARACTER);
        for (String nodeValue : nodeArray) {
            if ("".equals(nodeValue.replace(" ", ""))) {
                continue;
            }
            rbt.add(Integer.parseInt(nodeValue));
        }
        return rbt;
    }
}
