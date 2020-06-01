package com.sym.structure.tree;

import com.sym.structure.tree.bst.BinarySearchTree;
import com.sym.util.BinaryTreeUtil;
import com.sym.util.traversal.TreeTraversals;
import com.sym.util.printer.BinaryTrees;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;

/**
 * @author shenyanming
 * @date 2020/5/22 23:09.
 */
@Slf4j
public class TreeTest {

    /**
     * 二叉搜索树测试
     */
    @Test
    public void test01(){
        ITree<Integer> tree = new BinarySearchTree<>();
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            tree.add(random.nextInt(100));
        }
        BinaryTrees.println(tree);
        log.info("元素是否存在? {}", tree.contains(49));

        ITree.TreeTraversal traversal = tree.traversal();

        // 前序遍历
        TreeTraversals.preOrder(traversal);

        // 中序遍历
        TreeTraversals.inOrder(traversal);

        // 后序遍历
        TreeTraversals.postOrder(traversal);

        // 层序遍历
        TreeTraversals.levelOrder(traversal);
    }


    /**
     * 二叉搜索树测试
     */
    @Test
    public void test02(){
        String nodeValues = "56,12,4,6,32,787,110,23,120,89,543,778,15,19,3,9";
        BinarySearchTree<Integer> bst = BinaryTreeUtil.newBinarySearchTree(nodeValues);
        // 打印二叉树的节点分布情况
        BinaryTrees.println(bst);

        // 计算二叉树的高度
        System.out.println(bst.height());
    }
}
