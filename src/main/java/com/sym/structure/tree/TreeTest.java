package com.sym.structure.tree;

import com.sym.structure.tree.avl.AvlTree;
import com.sym.structure.tree.bst.BinarySearchTree;
import com.sym.structure.tree.bst.IBinarySearchTree;
import com.sym.util.BinaryTreeUtil;
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
     * 创建一颗二叉搜索树
     */
    @Test
    public void test01() {
        IBinarySearchTree<Integer> tree = new BinarySearchTree<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            tree.add(random.nextInt(1000));
        }
        // 打印二叉树
        BinaryTrees.println(tree);

        log.info("非空判断：{}", tree.isEmpty());
        log.info("树的高度：{}", tree.height());
        log.info("树的大小：{}", tree.size());
        log.info("是否包含520：{}", tree.contains(520));

        // 清空二叉树
        tree.clear();
        BinaryTrees.println(tree);

        // 创建使用比较器的二叉树
        IBinarySearchTree<String> tree2 = new BinarySearchTree<>(String::compareTo);
        BinaryTrees.println(tree2);
    }

    /**
     * 二叉搜索树遍历测试
     */
    @Test
    public void test02() {
        String nodeValues = "56,12,4,6,32,787,110,23,120,89,543,778,15,19,3,9";
        BinarySearchTree<Integer> bst = BinaryTreeUtil.newBinarySearchTree(nodeValues);

        // 打印二叉树的节点分布情况
        BinaryTrees.println(bst);
        System.out.println();

        // 前序遍历
        System.out.print("前序遍历：");
        bst.preorder((e) -> {
            System.out.print(e + " ");
        });
        System.out.println();

        // 中序遍历
        System.out.print("中序遍历：");
        bst.inorder((e) -> {
            System.out.print(e + " ");
        });
        System.out.println();

        // 后序遍历
        System.out.print("后序遍历：");
        bst.postorder((e) -> {
            System.out.print(e + " ");
        });
        System.out.println();

        // 层序遍历
        System.out.print("层序遍历：");
        bst.levelorder((e) -> {
            System.out.print(e + " ");
        });
        System.out.println();
    }

    /**
     * 二叉搜索树删除测试
     */
    @Test
    public void test03() {
        String nodeValues = "56,12,4,6,32,787,110,23,120,89,543,778,15,19,3,9";
        BinarySearchTree<Integer> bst = BinaryTreeUtil.newBinarySearchTree(nodeValues);
        // 初始化二叉搜索树的节点分布情况
        BinaryTrees.println(bst);

        // 删除度为0的节点：19
        bst.remove(19);
        System.out.println("删除度为0的节点【19】：");
        BinaryTrees.println(bst);

        // 删除度为1的节点：787
        bst.remove(787);
        System.out.println("删除度为1的节点【787】：");
        BinaryTrees.println(bst);

        // 删除度为2的节点：12
        bst.remove(12);
        System.out.println("删除度为2的节点【12】：");
        BinaryTrees.println(bst);

        // 删除根节点：56
        bst.remove(56);
        System.out.println("删除根节点【56】：");
        BinaryTrees.println(bst);
    }

    /**
     * AVL树与二叉搜索树在极端情况下的元素分布情况
     */
    @Test
    public void test04(){
        String nodeString = "10,20,30,40,50,60";
        // 极端情况下, 二叉搜索树变为链表
        BinarySearchTree<Integer> bst = BinaryTreeUtil.newBinarySearchTree(nodeString);
        BinaryTrees.println(bst);

        // 即使极端情况下, avl也能通过自平衡操作维持二叉树特性
        AvlTree<Integer> avl = BinaryTreeUtil.newAvlTree(nodeString);
        BinaryTrees.println(avl);
    }
}
