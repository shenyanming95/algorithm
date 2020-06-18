package com.sym.structure.tree.avl;

import com.sym.structure.tree.bst.BinarySearchTree;
import com.sym.structure.tree.traversal.Visitor;

/**
 * AVL树的实现, 理论上可以继承二叉搜索树{@link BinarySearchTree}实现,
 * 但是我们在这里重新实现一版, 就不去继承二叉搜索树实现了!
 *
 * @author shenyanming
 * @date 2020/6/16 22:25.
 */
public class AvlTree<E> implements IAvlTree<E> {

    /**
     * AVL树的节点实体
     *
     * @param <E> 类型
     */
    private static class AvlNode<E> {
        E element;
        AvlNode<E> left;
        AvlNode<E> right;
        AvlNode<E> parent;
        // 用于维护AVL树的平衡因子
        int height = 1;

        public AvlNode(E e, AvlNode<E> p) {
            this.element = e;
            this.parent = p;
        }

        /**
         * 获取节点的度
         *
         * @return 0 or 1 or 2
         */
        public int degree() {
            int result = 0;
            if (left != null) {
                result++;
            }
            if (right != null) {
                result++;
            }
            return result;
        }

        /**
         * 获取节点的平衡因子
         *
         * @return 平衡因子
         */
        public int balanceFactor() {
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            // 平衡因子 = 左子树高度 - 右子树高度
            return Math.abs(leftHeight - rightHeight);
        }

        /**
         * 判断节点是否处于平衡
         *
         * @return true-平衡
         */
        public boolean isBalance() {
            // 节点平衡的条件就是它的平衡因子绝对值小于等于1
            return Math.abs(this.balanceFactor()) <= 1;
        }

        /**
         * 更新当前节点的高度
         */
        public void updateHeight() {
            // 一个节点的高度, 等于它左右子树高度的最大值 + 它本身的高度(即1)
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        /**
         * 判断节点是否为其父节点的左子树
         *
         * @return true-左子树
         */
        public boolean isParentLeft() {
            return parent != null && this == parent.left;
        }

        /**
         * 判断节点是否为其父节点的右子树
         *
         * @return true-右子树
         */
        public boolean isParentRight() {
            return parent != null && this == parent.right;
        }

        /**
         * 获取当前节点左右子树中高度最高的节点,
         *
         * @return
         */
        public AvlNode<E> taller() {
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            if (leftHeight > rightHeight) {
                return left;
            } else if (rightHeight > leftHeight) {
                return right;
            } else {
                return isParentLeft() ? left : right;
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(element.toString());
            if (parent == null) {
                sb.append("(null)");
            } else {
                sb.append("(").append(parent.element.toString()).append(")");
            }
            return sb.toString();
        }
    }

    /**
     * 根节点
     */
    private AvlNode<E> root;

    @Override
    public int height() {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public void add(E e) {
    }

    @Override
    public void remove(E e) {
    }

    @Override
    public boolean contains(E e) {
        return false;
    }

    @Override
    public void preorder(Visitor<E> visitor) {
    }

    @Override
    public void inorder(Visitor<E> visitor) {
    }

    @Override
    public void postorder(Visitor<E> visitor) {
    }

    @Override
    public void levelorder(Visitor<E> visitor) {
    }

    /**
     * 判断再新节点添加后, 整棵AVL树是否还处于平衡
     *
     * @param node 新添加的节点
     */
    private void determineBalance(AvlNode<E> node) {
        // 当添加一个节点后, 如果导致了AVL失衡, 即存在若干个平衡因子绝对值大于1的节点
        // (注意：这些失衡节点只可能是新添加节点的父节点或祖父节点), 只要解决了最近的父节点
        // 的失衡问题, 往上的祖父节点也就重新平衡了; 同时, 还需要在这个循环中维护节点的高度,
        // 否则每次判断一个节点是否平衡, 都需要使用递归, 效率反而变低！
        while ((node = (node.parent)) != null) {
            if (node.isBalance()) {
                // 当前节点是平衡的, 更新其高度(因为已经为它添加了一个新的子节点了)
                node.updateHeight();
            } else {
                // 当前节点不平衡, 重新维护平衡, 即【旋转】操作
                this.reBalance(node);
                break;
            }
        }
    }

    /**
     * 旋转使其平衡, AVL树节点失衡的条件只有4种：
     * 1)、LL失衡, 失衡节点右旋转;
     * 2)、RR失衡, 失衡节点左旋转;
     * 3)、LR失衡, 失衡节点左子树先左旋, 失衡节点再右旋;
     * 4)、RL失衡, 失衡节点右子树先右旋, 失衡节点再左旋;
     *
     * @param node 当前失衡节点
     */
    private void reBalance(AvlNode<E> node) {
        // 这边需要留意, 因为现在已经知道node失衡, 但是不知道它是属于哪种失衡.
        // 所以通过获取它左右子树中高度最高的那个节点(肯定高度较高的那部分子树才会失衡).
        // 同理, 通过这种方式再找到失衡节点的孙子节点, 结合子节点和孙子节点就能判断出是属于哪种失衡.
        AvlNode<E> childNode = node.taller();
        AvlNode<E> grandsonNode = childNode.taller();

        if (childNode.isParentLeft()) {
            // 如果是LL, 只要将失衡节点右旋即可;
            // 如果是LR, 先将失衡节点的左子节点左旋再将失衡节点右旋
            if (grandsonNode.isParentRight()) {
                rotateLeft(childNode);
            }
            rotateRight(node);
        } else {
            // 如果是RR, 失衡节点左旋即可;
            // 如果是RL, 先将失衡节点的右子节点右旋再将失衡节点左旋
            if (grandsonNode.isParentLeft()) {
                rotateRight(childNode);
            }
            rotateLeft(node);
        }
    }

    /**
     * 将节点进行坐旋转
     *
     * @param node 节点
     */
    private void rotateLeft(AvlNode<E> node) {
    }

    /**
     * 将节点进行右旋转
     *
     * @param node 节点
     */
    private void rotateRight(AvlNode<E> node) {
    }

    /* 借助外部工具类, 打印二叉树的结构图 - start*/
    @Override
    public Object root() {
        return root;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object left(Object node) {
        return ((AvlNode<E>) node).left;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object right(Object node) {
        return ((AvlNode<E>) node).right;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object string(Object node) {
        return ((AvlNode<E>) node).toString();
    }
    /* 借助外部工具类, 打印二叉树的结构图 - end*/
}
