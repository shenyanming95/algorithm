package com.sym.datastructure.tree;

import com.sym.datastructure.tree.bst.BinarySearchTree;
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
    public void binarySearchTreeTest(){
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


}
