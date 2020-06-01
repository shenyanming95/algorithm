package com.sym.structure.tree.bst;

import com.sym.structure.queue.IQueue;
import com.sym.structure.queue.linked.LinkedQueue;
import com.sym.structure.tree.ITree;

import java.util.Comparator;
import java.util.Objects;

/**
 * 二叉搜索树的链表实现
 *
 * @param <E> 要么通过{@link java.util.Comparator}比较, 要么E需要实现{@link Comparable}
 * @author shenyanming
 * @date 2020/5/21 22:51.
 */
@SuppressWarnings("unchecked")
public class BinarySearchTree<E> implements IBinarySearchTree<E> {

    /**
     * 没有指定比较器, 则需要泛型E实现{@link Comparable}
     */
    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 二叉搜索树的节点实体
     *
     * @param <E> 类型
     */
    private static class BstNode<E> implements ITree.TreeTraversal {
        E element;
        BstNode<E> left;
        BstNode<E> right;
        BstNode<E> parent;

        public BstNode(E e, BstNode<E> p) {
            this.element = e;
            this.parent = p;
        }

        @Override
        public BstNode<E> root() {
            return this;
        }

        @Override
        public BstNode<E> left() {
            return left;
        }

        @Override
        public BstNode<E> right() {
            return right;
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }

    /**
     * 根结点
     */
    private BstNode<E> root;

    /**
     * 数量大小
     */
    private int size;

    /**
     * true-当元素值一样, 新元素会替代旧元素
     */
    private boolean replaceIfEquals;

    /**
     * 元素比较器
     */
    private Comparator<E> comparator;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public void add(E e) {
        Objects.requireNonNull(e);
        if (null == root) {
            // 优先初始化根节点
            root = new BstNode<>(e, null);
        } else {
            // 当比较结束后, 需要知道节点e放在父节点的左边还是右边, 所以需要记录最后一次比较的值
            int result = 0;
            // 原二叉树的当前比较节点, 由它从root开始, 一层一层地比较
            BstNode<E> temp = root;
            // 当比较结束后, 需要知道节点e的父节点是谁, 所以需要记录当前比较节点temp的父节点
            BstNode<E> currentParentNode = root;
            while (temp != null) {
                // 每次循环开启, 就记录父节点
                currentParentNode = temp;
                // 进行比较
                result = doCompare(e, temp.element);
                // 根据二叉搜索树性质, 若比根节点值大, 则取右节点比较; 若比根节点值小, 则取左节点比较; 若相等, 则覆盖原节点的值
                if (result > 0) {
                    temp = temp.right;
                } else if (result < 0) {
                    temp = temp.left;
                } else {
                    temp.element = e;
                    return;
                }
            }
            // 当循环退出, 表示已经找到一个合适的位置, 通过判断result正负形, 来决定位于 父节点 的左边还是右边
            BstNode<E> newNode = new BstNode<>(e, currentParentNode);
            if (result > 0) {
                currentParentNode.right = newNode;
            } else {
                currentParentNode.left = newNode;
            }
        }
        size++;
    }

    /**
     * 找到对应的元素, 将它删除, 然后取它的左节点来替换它
     *
     * @param e 待删除元素
     */
    @Override
    public void remove(E e) {
        if (isEmpty()) {
            return;
        }
        BstNode<E> target = doSearch(e);
        if (null != target) {
            // 若删除的是根节点
            if (target == root) {

            } else {
                // 删除非根节点
            }
        }
    }

    /**
     * 查找某个元素是否存在于二叉搜索树中
     *
     * @param e 元素
     * @return true-exist
     */
    @Override
    public boolean contains(E e) {
        Objects.requireNonNull(e);
        if (isEmpty()) {
            return false;
        }
        return null != doSearch(e);
    }

    /**
     * 二叉搜索树的遍历
     *
     * @return {@link TreeTraversal}
     */
    @Override
    public TreeTraversal traversal() {
        return root;
    }

    /**
     * 返回二叉搜索树的高度
     *
     * @return 高度
     */
    @Override
    public int height() {
        // 递归的方式
        // return doComputeHeightV2(root);

        // 遍历的方式
        return doComputeHeight(root);
    }

    /**
     * 二叉搜索树的元素比较
     *
     * @param first  元素1
     * @param second 元素2
     * @return 返回1, 0,-1分别表示元素1大于元素2, 元素1等于元素2, 元素1小于元素2
     */
    private int doCompare(E first, E second) {
        return null != this.comparator ? comparator.compare(first, second) : ((Comparable<E>) first).compareTo(second);
    }

    /**
     * 二叉搜素树的搜索逻辑
     *
     * @param e 元素
     * @return null-不存在, 反之返回元素所在的节点
     */
    private BstNode<E> doSearch(E e) {
        BstNode<E> temp = root;
        while (null != temp) {
            int result = doCompare(e, temp.element);
            if (result > 0) {
                temp = temp.right;
            } else if (result < 0) {
                temp = temp.left;
            } else {
                return temp;
            }
        }
        return null;
    }

    /**
     * 通过遍历（层序遍历）的方式计算高度
     *
     * @return 高度
     */
    private int doComputeHeight(BstNode<E> root) {
        if (null == root) {
            return 0;
        }
        IQueue<BstNode<E>> queue = new LinkedQueue<>();
        queue.offer(root);
        int levelCount = 1;
        int height = 0;
        while (!queue.isEmpty()) {
            BstNode<E> node = queue.poll();
            levelCount--;
            if (null != node.left) {
                queue.offer(node.left);
            }
            if (null != node.right) {
                queue.offer(node.right);
            }
            if (levelCount == 0) {
                height++;
                levelCount = queue.size();
            }
        }
        return height;
    }

    /**
     * 通过递归的方式计算二叉搜索树的高度
     *
     * @param root 根节点
     * @return 高度
     */
    private int doComputeHeightV2(BstNode<E> root) {
        if (null == root) {
            return 0;
        }
        // 一旦能递归调用, 说明这一层的节点存在, 说明树的高度就要加1;
        return 1 + Math.max(doComputeHeightV2(root.left), doComputeHeightV2(root.right));
    }

    /* 借助外部工具类, 打印二叉树的结构图 - start*/
    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((BstNode<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((BstNode<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((BstNode<E>) node).element;
    }
    /* 借助外部工具类, 打印二叉树的结构图 - end*/
}
