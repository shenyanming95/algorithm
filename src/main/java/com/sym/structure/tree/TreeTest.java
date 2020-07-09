package com.sym.structure.tree;

import com.sym.structure.tree.avl.AvlTree;
import com.sym.structure.tree.bst.BinarySearchTree;
import com.sym.structure.tree.bst.IBinarySearchTree;
import com.sym.structure.tree.rbt.RedBlackTree;
import com.sym.util.BinaryTreeUtil;
import com.sym.util.TimeUtil;
import com.sym.util.printer.BinaryTrees;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author shenyanming
 * @date 2020/5/22 23:09.
 */
@Slf4j
public class TreeTest {

    private Random random = new Random();

    /**
     * 创建一颗二叉搜索树
     */
    @Test
    public void newBinarySearchTree() {
        IBinarySearchTree<Integer> tree = new BinarySearchTree<>();
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
    public void binarySearchTraverse() {
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
    public void deleteBinarySearch() {
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
     * 创建一颗AVL树, 并且比较AVL树与二叉搜索树在极端情况下的元素分布情况
     */
    @Test
    public void newAvlTree(){
        String nodeString = "10,20,30,40,50,60,70,80,90,100";
        // 极端情况下, 二叉搜索树变为链表
        BinarySearchTree<Integer> bst = BinaryTreeUtil.newBinarySearchTree(nodeString);
        BinaryTrees.println(bst);

        // 即使极端情况下, avl也能通过自平衡操作维持二叉树特性
        AvlTree<Integer> avl = BinaryTreeUtil.newAvlTree(nodeString);
        BinaryTrees.println(avl);
    }

    /**
     *  AVL树的删除逻辑测试
     */
    @Test
    public void deleteAvlTree(){
        String nodeValues = "56,12,4,6,32,787,110,23,120,89,543,778,15,19,3,9";
        AvlTree<Integer> avlTree = BinaryTreeUtil.newAvlTree(nodeValues);
        // 初始化二叉搜索树的节点分布情况
        BinaryTrees.println(avlTree);

        // 删除【节点3】, 让节点【4】失衡
        avlTree.remove(3);
        System.out.println("删除节点【3】：");
        BinaryTrees.println(avlTree);

        // 删除【节点110】, AVL树仍处于平衡
        avlTree.remove(110);
        System.out.println("删除节点【110】：");
        BinaryTrees.println(avlTree);

        // 删除【节点89】，导致节点【120】失衡
        avlTree.remove(89);
        System.out.println("删除节点【89】：");
        BinaryTrees.println(avlTree);
    }

    /**
     * 二叉搜索树和AVL树在极端情况下的查询性能
     */
    @Test
    public void queryPerformance(){
        int size = 50000;
        List<Integer> list = new ArrayList<>(size);
        for(int i = 0; i < size; i++){
            list.add(i);
        }
        String nodeString = list.toString().replace("[", "").replace("]", "").replaceAll(" ","");
        // 创建二叉搜索树
        BinarySearchTree<Integer> bst = BinaryTreeUtil.newBinarySearchTree(nodeString);
        // 创建AVL树
        AvlTree<Integer> avl = BinaryTreeUtil.newAvlTree(nodeString);

        // 查找49999是否存在
        int i = 49999;
        TimeUtil.execute("二叉搜索树", () -> bst.contains(i));
        TimeUtil.execute("avl树", () -> avl.contains(i));
    }

    /**
     * 创建一颗红黑树
     */
    @Test
    public void newRedBlackTree(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(random.nextInt(1000));
        }
        System.out.println(list);
        RedBlackTree<Integer> redBlackTree = BinaryTreeUtil.newRedBlackTree(list);

        // 打印二叉树
        BinaryTrees.println(redBlackTree);
        System.out.println();

        log.info("非空判断：{}", redBlackTree.isEmpty());
        log.info("树的高度：{}", redBlackTree.height());
        log.info("树的大小：{}", redBlackTree.size());
        log.info("是否包含520：{}", redBlackTree.contains(520));
    }

    /**
     * 删除红黑树
     */
    @Test
    public void deleteRedBlackTree(){
        // 先创建红黑树
        String nodeString = "781, 996, 271, 718, 132, 535, 715, 816, 567, 228, 754, 115, 442, 582, 560, 435, 558, 535, 522, 294";
        RedBlackTree<Integer> rbt = BinaryTreeUtil.newRedBlackTree(nodeString);
        BinaryTrees.println(rbt);

        // 删除【816】
        rbt.remove(816);
        System.out.println("删除节点【816】：");
        BinaryTrees.println(rbt);

        // 删除【271】
        rbt.remove(271);
        System.out.println("删除节点【271】：");
        BinaryTrees.println(rbt);

        // 删除【535】
        rbt.remove(535);
        System.out.println("删除节点【535】：");
        BinaryTrees.println(rbt);

    }
}
