package com.sym.structure.tree.avl;

import com.sym.structure.tree.bst.IBinarySearchTree;

/**
 * AVL树是一种自平衡的二叉搜索树, 因此它满足二叉搜索树的所有性质. 并且, 它由于是自平衡的, 所以增加另一个平衡因子的概念,
 * 即任意一个节点, 它的平衡因子等于它的【左子树高度 - 右子树高度】. 如果一颗二叉搜索树的任意一个节点其平衡因子p, 满足
 * 【-1 <= p <= 1】, 则这棵二叉搜索树就称为AVL树.
 *
 * AVL树得名于它的发明者G. M. Adelson-Velsky和Evgenii Landis, 所以用他们名字的首字母组成AVL, 这也导致了AVL树没有
 * 一个全称, 可以用他们的名字作为全称不够习惯上就称为AVL.
 *
 * @author shenyanming
 * @date 2020/6/14 15:25.
 */
public interface IAvlTree<E> extends IBinarySearchTree<E> {
    // AVL树, 首先得保证是一颗二叉搜索树
}
