package com.sym.structure.tree.rbt;

import com.sym.structure.tree.bst.IBinarySearchTree;

/**
 * 红黑树也是平衡二叉树的一种, 所以它也是在二叉搜索树的基础加了其它限制：
 * 1)、根节点为黑色;
 * 2)、任意节点, 要么为红色, 要么为黑色;
 * 3)、红色节点的子节点都为黑色;
 * 4)、任意一个节点到叶子节点的路径, 都包含相同数量的黑色节点;
 * 5)、叶子节点都为黑色, 注意红黑树的叶子节点并不是真正意义上的叶子节点, 是度为0和1的节点扩展出来的nil节点.
 *
 * @author shenyanming
 * @date 2020/6/26 16:28.
 * @see com.sym.structure.tree.bst.IBinarySearchTree
 * @see com.sym.structure.tree.bst.BinarySearchTree
 */
public interface IRedBlackTree<E> extends IBinarySearchTree<E> {
}
