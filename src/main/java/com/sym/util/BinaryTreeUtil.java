package com.sym.util;

import com.sym.structure.tree.avl.AvlTree;
import com.sym.structure.tree.bst.BinarySearchTree;

/**
 * 二叉树工具类
 *
 * @author shenyanming
 * @date 2020/5/31 15:01.
 */

public class BinaryTreeUtil {

    /**
     * 创建二叉搜索树
     * @param nodeString 节点串
     * @return
     */
    public static BinarySearchTree<Integer> newBinarySearchTree(String nodeString) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        String[] nodeArray = nodeString.split(",");
        for (String nodeValue : nodeArray) {
            if ("".equals(nodeValue)) {
                continue;
            }
            bst.add(Integer.parseInt(nodeValue.replace(" ", "")));
        }
        return bst;
    }

    /**
     * 创建AVL树
     * @param nodeString 节点串
     * @return
     */
    public static AvlTree<Integer> newAvlTree(String nodeString) {
        AvlTree<Integer> avl = new AvlTree<>();
        String[] nodeArray = nodeString.split(",");
        for (String nodeValue : nodeArray) {
            if ("".equals(nodeValue)) {
                continue;
            }
            avl.add(Integer.parseInt(nodeValue.replace(" ", "")));
        }
        return avl;
    }
}
