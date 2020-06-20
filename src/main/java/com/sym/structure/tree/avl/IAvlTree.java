package com.sym.structure.tree.avl;

import com.sym.structure.tree.bst.IBinarySearchTree;

/**
 * AVL树接口, 它是一种自平衡的二叉搜索树.AVL树得名于它的发明者G. M. Adelson-Velsky和Evgenii Landis,
 * 所以用他们名字的首字母组成AVL, 这也导致了AVL树没有一个全称, 可以用他们的名字作为全称不够习惯上就称为AVL.
 *
 * @author shenyanming
 * @date 2020/6/14 15:25.
 * @see com.sym.structure.tree.bst.IBinarySearchTree
 * @see com.sym.structure.tree.bst.BinarySearchTree
 */
public interface IAvlTree<E> extends IBinarySearchTree<E> {
    // AVL树, 首先得保证是一颗二叉搜索树
}
