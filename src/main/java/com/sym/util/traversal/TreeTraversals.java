package com.sym.util.traversal;

import com.sym.datastructure.queue.IQueue;
import com.sym.datastructure.queue.linked.LinkedQueue;
import com.sym.datastructure.tree.ITree;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 二叉树{@link ITree}的遍历方式
 *
 * @author shenyanming
 * @date 2020/5/24 17:37.
 */
@Slf4j
public class TreeTraversals {

    /**
     * 前序遍历
     *
     * @param tree 二叉树
     */
    public static void preOrder(ITree.TreeTraversal tree) {
        StringBuilder sb = new StringBuilder("[");
        preorder(tree, sb);
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        log.info("前序遍历：{}", sb.append("]").toString());
    }

    /**
     * 中序遍历
     *
     * @param tree 二叉树
     */
    public static void inOrder(ITree.TreeTraversal tree) {
        StringBuilder sb = new StringBuilder("[");
        inorder(tree, sb);
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        log.info("中序遍历：{}", sb.append("]").toString());
    }

    /**
     * 后序遍历
     *
     * @param tree 二叉树
     */
    public static void postOrder(ITree.TreeTraversal tree) {
        StringBuilder sb = new StringBuilder("[");
        postorder(tree, sb);
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        log.info("后序遍历：{}", sb.append("]").toString());
    }

    /**
     * 层序遍历
     *
     * @param tree 二叉树
     */
    public static void levelOrder(ITree.TreeTraversal tree) {
        StringBuilder sb = new StringBuilder("[");
        IQueue<ITree.TreeTraversal> queue = new LinkedQueue<>();
        queue.offer(tree);
        while(!queue.isEmpty()){
            ITree.TreeTraversal currentNode = queue.poll();
            sb.append(currentNode.root()).append(", ");
            ITree.TreeTraversal left = currentNode.left();
            ITree.TreeTraversal right = currentNode.right();
            if(null != left){
                queue.offer(left);
            }
            if(null != right){
                queue.offer(right);
            }
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        log.info("层序遍历：{}", sb.append("]").toString());
    }

    /**
     * 通过递归的方式前序遍历一棵树
     */
    private static void preorder(ITree.TreeTraversal tree, StringBuilder sb) {
        if (null == tree) {
            return;
        }
        sb.append(tree.root()).append(", ");
        preorder(tree.left(), sb);
        preorder(tree.right(), sb);
    }

    /**
     * 通过递归的方式中序遍历一棵树
     */
    private static void inorder(ITree.TreeTraversal tree, StringBuilder sb) {
        if (null == tree) {
            return;
        }
        preorder(tree.left(), sb);
        sb.append(tree.root()).append(", ");
        preorder(tree.right(), sb);
    }

    /**
     * 通过递归的方式后序遍历一棵树
     */
    private static void postorder(ITree.TreeTraversal tree, StringBuilder sb) {
        if (null == tree) {
            return;
        }
        preorder(tree.left(), sb);
        preorder(tree.right(), sb);
        sb.append(tree.root()).append(", ");
    }
}
