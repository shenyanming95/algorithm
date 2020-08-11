package com.sym.structure.tree.avl;

import com.sym.structure.tree.bst.BinarySearchTree;

import java.util.Comparator;

/**
 * AVL树接口, 它是一种自平衡的二叉搜索树. AVL树得名于它的发明者G. M. Adelson-Velsky和Evgenii Landis,
 * 所以用他们名字的首字母组成AVL, 这也导致了AVL树没有一个全称, 可以用他们的名字作为全称, 不过习惯上就称为AVL.
 *
 * AVL树的实现, 可以继承二叉搜索树{@link BinarySearchTree}, 因为它的平衡逻辑都是在二叉搜索树执行完添加或删除后触发的.
 * AVL树在 Binary Search Tree的基础上多出了另一个平衡因子的概念, 即任意一个节点, 它的平衡因子等于它的【左子树高度 - 右子树高度】.
 * 如果一颗二叉搜索树的任意一个节点其平衡因子p, 满足【-1 <= p <= 1】, 则这棵二叉搜索树就称为AVL树.
 *
 * @author shenyanming
 * @date 2020/6/16 22:25.
 */
public class AvlTree<E> extends BinarySearchTree<E>{

    public AvlTree(){
        super();
    }

    public AvlTree(Comparator<E> comparator){
        super(comparator);
    }

    /**
     * AVL树的节点实体
     *
     * @param <E> 类型
     */
    private static class AvlNode<E> extends BstNode<E>{
        /**
         * 这个属性很重要, 用于维护AVL树的平衡因子, 解释一下这边为啥让height默认值为1,
         * 这是因为一个节点创建的时候, 就指定它的高度为1, 便于计算平衡因子. 并且, 该属性
         * 的值会在每次变动节点的时候进行更新.
         */
        int height = 1;

        public AvlNode(E e, AvlNode<E> p) {
            super(e, p);
        }

        @Override
        public AvlNode<E> left() {
            return (AvlNode<E>)super.left();
        }

        @Override
        public AvlNode<E> right() {
            return (AvlNode<E>)super.right();
        }

        @Override
        public AvlNode<E> parent() {
            return (AvlNode<E>)super.parent();
        }

        public void setLeft(AvlNode<E> node){
            super.left = node;
        }

        public void setRight(AvlNode<E> node){
            super.right = node;
        }

        public void setParent(AvlNode<E> node){
            super.parent = node;
        }

        /**
         * 获取节点的平衡因子
         * @return 平衡因子
         */
        public int balanceFactor() {
            int leftHeight = left() == null ? 0 : left().height;
            int rightHeight = right() == null ? 0 : right().height;
            // 平衡因子 = 左子树高度 - 右子树高度
            return Math.abs(leftHeight - rightHeight);
        }

        /**
         * 判断节点是否处于平衡
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
            int leftHeight = left() == null ? 0 : left().height;
            int rightHeight = right() == null ? 0 : right().height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        /**
         * 判断节点是否为其父节点的左子树
         * @return true-左子树
         */
        public boolean isParentLeft() {
            AvlNode<E> parent = parent();
            return parent != null && this == parent.left;
        }

        /**
         * 判断节点是否为其父节点的右子树
         * @return true-右子树
         */
        public boolean isParentRight() {
            AvlNode<E> parent = parent();
            return parent != null && this == parent.right;
        }

        /**
         * 获取当前节点左右子树中高度最高的节点n, 在当前节点失衡的时候,
         * 节点n就是准备用来旋转的节点
         *
         * @return 高度最大的子节点
         */
        public AvlNode<E> taller() {
            int leftHeight = left() == null ? 0 : left().height;
            int rightHeight = right() == null ? 0 : right().height;
            if (leftHeight > rightHeight) {
                // 左子树高, 就取左子节点
                return left();
            } else if (rightHeight > leftHeight) {
                // 右子树高, 就取右子节点
                return right();
            } else {
                // 如果左右子树一样高, 则判断当前节点是位于父节点的左边还是右边, 跟它取一样方向的子节点即可
                return isParentLeft() ? left() : right();
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(element.toString()).append("[").append(balanceFactor()).append("]");
            if (parent() == null) {
                sb.append("(null)");
            } else {
                sb.append("(").append(parent().element.toString()).append(")");
            }
            return sb.toString();
        }
    }

    @Override
    protected BstNode<E> node(E e, BstNode<E> pNode) {
        return new AvlNode<>(e, (AvlNode<E>) pNode);
    }

    /**
     * 明确一点, AVL的自平衡操作是在添加新节点后,
     * 才来判断是否有使父节点或祖先节点失衡, 有的话做旋转调整.
     * 所以它还是需要先完成二叉搜索树的添加逻辑.
     */
    @Override
    protected void afterAdd(BstNode<E> bstNode) {
        // 新添加的节点
        AvlNode<E> newNode = (AvlNode<E>) bstNode;
        if(newNode == root){
            newNode.updateHeight();
            return;
        }
        // AVL树区别于Binary Tree的地方, 这里需要做平衡调整
        this.determineBalance(newNode, true);
    }

    /**
     * 删除节点的平衡处理
     * @param bstNode 实际被删除节点
     */
    @Override
    protected void afterRemove(BstNode<E> bstNode) {
        AvlNode<E> deleteNode = (AvlNode<E>)bstNode;
        // 自平衡处理. 删除节点与添加节点不同, 添加节点遇到不平衡的节点将其重新旋转后, 其祖先节点也随之平衡;
        // 但是删除节点, 如果旋转后子树高度变化了, 可能会导致其上的祖先节点全都失衡, 所以需要一个一个判断.
        this.determineBalance(deleteNode, false);
    }

    /**
     * 从指定节点的父节点开始, 判断其是否平衡, 如果有节点不平衡, 对其执行旋转操作.
     * 添加导致的失衡：只要处理掉失衡节点, 其上的祖先节点必定平衡;
     * 删除导致的失衡：处理掉失衡节点后, 有可能导致其所在的子树高度发生变化, 进而导致其上的祖先节点失衡, 所以需要一直循环向上判断
     *
     * @param node        从这个节点开始判断
     * @param shouldBreak 当处理完一个失衡节点后, 是否需要跳出循环
     */
    private void determineBalance(AvlNode<E> node, boolean shouldBreak) {
        // 当(添加 or 删除)一个节点后, 如果导致了AVL失衡, 即存在若干个平衡因子绝对值大于1的节点
        // (注意：这些失衡节点只可能是该节点的父节点或祖先节点)。对于添加导致的失衡, 只要解决了最近的父节点
        // 的失衡问题, 往上的祖先节点也就重新平衡了; 对于删除导致的失衡, 需要一直向上判断。同时, 还需要在这个循环中维护节点的高度,
        // 否则每次判断一个节点是否平衡, 都需要使用递归, 效率反而变低！
        while ((node = (node.parent())) != null) {
            if (node.isBalance()) {
                // 当前节点是平衡的, 更新其高度(因为已经为它添加了一个新的子节点了)
                node.updateHeight();
            } else {
                // 当前节点不平衡, 重新维护平衡, 即【旋转】操作
                this.reBalance(node);
                if (shouldBreak) {
                    break;
                }
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
        // 所以通过获取它左右子树中高度最高的那个节点(肯定高度较高的那部分子树导致的失衡).
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
     * 将节点进行左旋转. 这边最好画图理清, 不然容易犯错
     *        |
     *        o  <----失衡节点
     *      /   \
     *     o     o
     *         /   \
     *        o     o
     *               \
     *                o
     * @param node 失衡节点
     */
    private void rotateLeft(AvlNode<E> node) {
        // 失衡节点的父节点、右子节点
        AvlNode<E> nodeParent = node.parent(), nodeRightChild = node.right();

        // nodeRightChild的左子树, 移动到node的右子树
        node.setRight(nodeRightChild.left());
        // node作为nodeRightChild的左子树
        nodeRightChild.setLeft(node);

        // 由于nodeRightChild现在作为该子树的新根节点, 所以它要作为nodeParent的左子树或右子树
        if (node.isParentLeft()) {
            // 失衡节点node是原先父节点nodeParent的左子树, 同理nodeRightChild也作为nodeParent的左子树
            nodeParent.setLeft(nodeRightChild);
        } else if (node.isParentRight()) {
            // 失衡节点node是原先父节点nodeParent的右子树，同理nodeRightChild也作为nodeParent的右子树
            nodeParent.setRight(nodeRightChild);
        } else {
            // 失衡节点就是根节点, 则nodeRightChild作为新的根节点
            root = nodeRightChild;
        }

        // node和nodeRightChild的父引用指针各发生了变化, nodeRightChild的父引用指针变为nodeParent;
        // 而node就作为nodeRightChild的子树, 所以它的父引用指针变为nodeRightChild
        nodeRightChild.setParent(nodeParent);
        node.setParent(nodeRightChild);
        // 同时, 父引用指针变化的还有之前从nodeRightChild移到node右子树位置的子树, 它的父引用指针原先是nodeRightChild,
        // 现在要变为node, 前提是这个子树不为null才需要修改.
        if (node.right() != null) {
            // 这边容易忘记导致npe异常, 有可能原先的而nodeRightChild就作为node的父节点的左子树就是空的;
            // 只有在它不为空的情况才需要维护父引用
            node.right().setParent(node);
        }
        // 移动过子树的node和nodeRightChild, 都需要更新高度
        node.updateHeight();
        nodeRightChild.updateHeight();
    }

    /**
     * 将节点进行右旋转. 这边最好画图理清, 不然容易犯错
     *          |
     *          o  <--- 失衡节点
     *        /   \
     *       o     o
     *      / \
     *     o   o
     *    /
     *   o
     * @param node 失衡节点
     */
    private void rotateRight(AvlNode<E> node) {
        // 失衡节点的父节点、左子节点
        AvlNode<E> nodeParent = node.parent(), nodeLeftChild = node.left();

        // nodeLeftChild的右子树, 移动到node的左子树
        node.setLeft(nodeLeftChild.right());
        // node作为nodeLeftChild的左子树
        nodeLeftChild.setRight(node);

        // 由于nodeLeftChild现在作为该子树的新根节点, 所以它要作为nodeParent的左子树或右子树
        if (node.isParentLeft()) {
            // 失衡节点node是原先父节点nodeParent的左子树, 同理nodeLeftChild也作为nodeParent的左子树
            nodeParent.setLeft(nodeLeftChild);
        } else if (node.isParentRight()) {
            // 失衡节点node是原先父节点nodeParent的右子树， 同理nodeLeftChild也作为nodeParent的右子树
            nodeParent.setRight(nodeLeftChild);
        } else {
            // 失衡节点就是根节点, 则nodeLeftChild作为新的根节点
            root = nodeLeftChild;
        }
        // 原先的node的父节点就变为nodeLeftChild的父节点, 而nodeLeftChild就作为node的父节点
        nodeLeftChild.setParent(nodeParent);
        node.setParent(nodeLeftChild);
        if (node.left() != null) {
            // 这边容易忘记导致npe异常, 有可能原先的而nodeLeftChild就作为node的父节点的右子树就是空的;
            // 只有在它不为空的情况才需要维护父引用
            node.left().setParent(node);
        }
        // 更新节点高度
        node.updateHeight();
        nodeLeftChild.updateHeight();
    }
}
