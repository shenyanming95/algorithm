package com.sym.util;

import com.sym.structure.tree.bst.BinarySearchTree;

/**
 * @author shenyanming
 * @date 2020/5/31 15:01.
 */

public class BinaryTreeUtil {

    @SuppressWarnings("unchecked")
    public static <T> BinarySearchTree<T> newBinarySearchTree(String nodeString){
        BinarySearchTree<T> bst = new BinarySearchTree<>();
        String[] nodeArray = nodeString.split(",");
        for (String nodeValue : nodeArray) {
            if(!"".equals(nodeValue)){
                continue;
            }
            bst.add((T)nodeValue);
        }
        return bst;
    }
}
