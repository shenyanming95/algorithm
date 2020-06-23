package com.sym.structure.tree.avl;

import com.sym.structure.queue.IQueue;
import com.sym.structure.queue.linked.LinkedQueue;
import com.sym.structure.tree.ITree;
import com.sym.structure.tree.bst.BinarySearchTree;
import com.sym.structure.tree.traversal.IVisitor;

import java.util.Comparator;
import java.util.Objects;

/**
 * AVL树的实现, 理论上可以继承二叉搜索树{@link BinarySearchTree}实现, 这边没用继承, 直接重新完整地码一遍实现逻辑.
 * AVL树在 Binary Search Tree的基础上多出了另一个平衡因子的概念, 即任意一个节点, 它的平衡因子等于它的【左子树高度 - 右子树高度】.
 * 如果一颗二叉搜索树的任意一个节点其平衡因子p, 满足【-1 <= p <= 1】, 则这棵二叉搜索树就称为AVL树.
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
        /**
         * 这个属性很重要, 用于维护AVL树的平衡因子, 解释一下这边为啥让height默认值为1,
         * 这是因为一个节点创建的时候, 就指定它的高度为1, 便于计算平衡因子. 并且, 该属性
         * 的值会在每次变动节点的时候进行更新.
         */
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
         * 获取当前节点左右子树中高度最高的节点n, 在当前节点失衡的时候,
         * 节点n就是准备用来旋转的节点
         *
         * @return 高度最大的子节点
         */
        public AvlNode<E> taller() {
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            if (leftHeight > rightHeight) {
                // 左子树高, 就取左子节点
                return left;
            } else if (rightHeight > leftHeight) {
                // 右子树高, 就取右子节点
                return right;
            } else {
                // 如果左右子树一样高, 则判断当前节点是位于父节点的左边还是右边, 跟它取一样方向的子节点即可
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

    /**
     * 元素数量
     */
    private int size;

    /**
     * 元素比较器
     */
    private Comparator<E> comparator;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return null == root;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 添加逻辑, 明确一点, AVL的自平衡操作是在添加新节点后,
     * 才来判断是否有使父节点或祖先节点失衡, 有的话做旋转调整.
     * 所以它还是需要先完成二叉搜索树的添加逻辑.
     *
     * @param e 元素
     */
    @Override
    public void add(E e) {
        /*
         * 先完成二叉搜索树的添加逻辑
         */
        if (root == null) {
            root = new AvlNode<>(e, null);
            root.updateHeight();
            return;
        } else {
            // 比较结果
            int result = 0;
            // 当前遍历节点
            AvlNode<E> cur = root;
            // 当前遍历节点cur的未来父节点
            AvlNode<E> p = root;
            // 通过遍历来找到元素e的位置,
            while (cur != null) {
                p = cur;
                result = doCompare(e, cur.element);
                if (result > 0) {
                    // 说明比当前节点的值大, 则遍历右子树部分
                    cur = cur.right;
                } else if (result < 0) {
                    // 说明比当前节点的值小, 则遍历左子树部分
                    cur = cur.left;
                } else {
                    // 相等的话, 将值替换, 方法直接返回
                    cur.element = e;
                    return;
                }
            }
            // 当循环终止后, 说明新元素e的位置找到了, 而且它的父节点就为p
            AvlNode<E> newNode = new AvlNode<>(e, p);
            if (result > 0) {
                // 说明比父节点大, 放在它的右边
                p.right = newNode;
            } else {
                // 比父节点小, 放在它的左边
                p.left = newNode;
            }
            // AVL树区别于Binary Tree的地方, 这里需要做平衡调整
            this.determineBalance(newNode, true);
        }
        // 元素数量加1
        size++;
    }

    /**
     * 删除节点, 分为三种情况：
     * 1.度为0, 直接删除;
     * 2.度为1, 取它的子节点替代它的位置;
     * 3.度为2, 将它前驱节点或者后继节点的值替换它现在的值, 然后将前驱节点或者后继节点删除
     *
     * @param e 待删除元素
     */
    @Override
    public void remove(E e) {
        // 若元素不存在于当前AVL树中, 则直接返回
        AvlNode<E> node;
        if ((node = doSearch(e)) == null) {
            return;
        }
        // 获取节点的度
        int degree = node.degree();

        if (degree == ITree.DEGREE_TWO) {
            // 节点的度为2, 用它的后继节点的值来替换当前节点的值, 然后再后继节点删掉！
            AvlNode<E> successor = successor(node);
            node.element = successor.element;
            node = successor;
        }
        // 整棵AVL只剩根节点, 并且删除的元素就是根节点
        if (degree == 0 && node == root) {
            this.clear();
            return;
        }
        // 剩下的情况, 只有度为0或1的普通节点(必定有父节点). 它们都需要将自己从父节点的引用剔除掉,
        // 然后如果度为1的节点, 将它的子节点的父引用指针指向它的父节点.
        AvlNode<E> child = Objects.nonNull(node.left) ? node.left : node.right;
        if (node.isParentLeft()) {
            node.parent.left = child;
        } else {
            node.parent.right = child;
        }
        if (Objects.nonNull(child)) {
            child.parent = node.parent;
        }
        size--;
        // 自平衡处理. 删除节点与添加节点不同, 添加节点遇到不平衡的节点将其重新旋转后, 其祖先节点也随之平衡;
        // 但是删除节点, 如果旋转后子树高度变化了, 可能会导致其上的祖先节点全都失衡, 所以需要一个一个判断.
        this.determineBalance(node, false);
    }

    @Override
    public boolean contains(E e) {
        return doSearch(e) != null;
    }

    @Override
    public int height() {
        if(Objects.isNull(root)){
            return 0;
        }
        // 通过类似层序遍历的方式, 统计一颗AVL树的高度, 创建一个队列, 先将根节点入队
        IQueue<AvlNode<E>> queue = new LinkedQueue<>();
        queue.offer(root);

        // 每一层需要访问到的节点个数, 当然根节点所在层, 只需要访问根节点它自己, 所以只需要访问1个
        int needVisitEveryLevel = 1;
        // 最终的高度
        int height = 0;

        while(!queue.isEmpty()){
            // 取出队列中的节点, 同时将这一层需要访问的次数-1
            AvlNode<E> cur = queue.poll();
            needVisitEveryLevel--;

            // 放入不为空的左子节点
            if(cur.left != null){
                queue.offer(cur.left);
            }
            // 放入不为空的右子节点
            if(cur.right != null){
                queue.offer(cur.right);
            }
            // 当needVisitEveryLevel等于0, 说明这一层的节点访问完了, 将height加1,
            // 同时队列内的元素, 便是下一层需要访问的节点个数
            if(needVisitEveryLevel == 0){
                height++;
                needVisitEveryLevel = queue.size();
            }
        }

        return height;
    }

    @Override
    public void preorder(IVisitor<E> visitor) {
        if(Objects.isNull(root) || Objects.isNull(visitor)){
            return;
        }
        this.preorder(root, visitor);
    }

    @Override
    public void inorder(IVisitor<E> visitor) {
        if(Objects.isNull(root) || Objects.isNull(visitor)){
            return;
        }
        this.inorder(root, visitor);
    }

    @Override
    public void postorder(IVisitor<E> visitor) {
        if(Objects.isNull(root) || Objects.isNull(visitor)){
            return;
        }
        this.postorder(root, visitor);
    }

    /**
     * 通过队列的方式, 实现AVL树的层序访问
     *
     * @param visitor 访问者
     */
    @Override
    public void levelorder(IVisitor<E> visitor) {
        if(Objects.isNull(root) || Objects.isNull(visitor)){
            return;
        }
        // 创建一个队列, 每当这个节点访问完, 就将它的非空子节点按序添加到队列中.
        // 首先先将根节点入队
        IQueue<AvlNode<E>> queue = new LinkedQueue<>();
        queue.offer(root);

        // 如果队列不为空, 那就一直循环
        while(!queue.isEmpty()){
            // 取出队列的元素, 访问它
            AvlNode<E> cur = queue.poll();
            visitor.visit(cur.element);
            // 非空左子节点入队
            if(Objects.nonNull(cur.left)){
                queue.offer(cur.left);
            }
            // 非空右子节点入队
            if(Objects.nonNull(cur.right)){
                queue.offer(cur.right);
            }
        }
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
        while ((node = (node.parent)) != null) {
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
        AvlNode<E> nodeParent = node.parent, nodeRightChild = node.right;

        // nodeRightChild的左子树, 移动到node的右子树
        node.right = nodeRightChild.left;
        // node作为nodeRightChild的左子树
        nodeRightChild.left = node;

        // 由于nodeRightChild现在作为该子树的新根节点, 所以它要作为nodeParent的左子树或右子树
        if (node.isParentLeft()) {
            // 失衡节点node是原先父节点nodeParent的左子树, 同理nodeRightChild也作为nodeParent的左子树
            nodeParent.left = nodeRightChild;
        } else if (node.isParentRight()) {
            // 失衡节点node是原先父节点nodeParent的右子树， 同理nodeRightChild也作为nodeParent的右子树
            nodeParent.right = nodeRightChild;
        } else {
            // 失衡节点就是根节点, 则nodeRightChild作为新的根节点
            root = nodeRightChild;
        }

        // node和nodeRightChild的父引用指针各发生了变化, nodeRightChild的父引用指针变为nodeParent;
        // 而node就作为nodeRightChild的子树, 所以它的父引用指针变为nodeRightChild
        nodeRightChild.parent = nodeParent;
        node.parent = nodeRightChild;
        // 同时, 父引用指针变化的还有之前从nodeRightChild移到node右子树位置的子树, 它的父引用指针原先是nodeRightChild,
        // 现在要变为node, 前提是这个子树不为null才需要修改.
        if (node.right != null) {
            // 这边容易忘记导致npe异常, 有可能原先的而nodeRightChild就作为node的父节点的左子树就是空的;
            // 只有在它不为空的情况才需要维护父引用
            node.right.parent = node;
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
        AvlNode<E> nodeParent = node.parent, nodeLeftChild = node.left;

        // nodeLeftChild的右子树, 移动到node的左子树
        node.left = nodeLeftChild.right;
        // node作为nodeLeftChild的左子树
        nodeLeftChild.right = node;

        // 由于nodeLeftChild现在作为该子树的新根节点, 所以它要作为nodeParent的左子树或右子树
        if (node.isParentLeft()) {
            // 失衡节点node是原先父节点nodeParent的左子树, 同理nodeLeftChild也作为nodeParent的左子树
            nodeParent.left = nodeLeftChild;
        } else if (node.isParentRight()) {
            // 失衡节点node是原先父节点nodeParent的右子树， 同理nodeLeftChild也作为nodeParent的右子树
            nodeParent.right = nodeLeftChild;
        } else {
            // 失衡节点就是根节点, 则nodeLeftChild作为新的根节点
            root = nodeLeftChild;
        }
        // 原先的node的父节点就变为nodeLeftChild的父节点, 而nodeLeftChild就作为node的父节点
        nodeLeftChild.parent = nodeParent;
        node.parent = nodeLeftChild;
        if (node.left != null) {
            // 这边容易忘记导致npe异常, 有可能原先的而nodeLeftChild就作为node的父节点的右子树就是空的;
            // 只有在它不为空的情况才需要维护父引用
            node.left.parent = node;
        }
        // 更新节点高度
        node.updateHeight();
        nodeLeftChild.updateHeight();
    }

    /**
     * 判断元素是否存在当前的AVL树中
     *
     * @param e 待查找元素
     * @return null表示不存在当前元素
     */
    private AvlNode<E> doSearch(E e) {
        if (isEmpty()) {
            // 空树返回null
            return null;
        }
        // 从根节点开始, 类似二分查找
        AvlNode<E> cur = root;
        while (cur != null) {
            int result = doCompare(e, cur.element);
            if (result > 0) {
                // 比当前节点大, 则从右子树再找
                cur = cur.right;
            } else if (result < 0) {
                // 比当前节点小, 则从左子树找
                cur = cur.left;
            } else {
                // 相等说明找到了, 直接返回
                return cur;
            }
        }
        // 循环完了还没有找到, 就是avl树不存在当前这个元素
        return null;
    }

    /**
     * 元素比较
     *
     * @param first  元素1
     * @param second 元素2
     * @return 返回1, 0,-1分别表示元素1大于元素2, 元素1等于元素2, 元素1小于元素2
     */
    @SuppressWarnings("unchecked")
    private int doCompare(E first, E second) {
        return null != this.comparator ? comparator.compare(first, second) : ((Comparable<E>) first).compareTo(second);
    }

    /**
     * 获取指定节点的前驱节点, 因为是中序遍历的前一个节点, 所以：
     * 1.左子树不为空, 就取左子树的最右节点 (只有等到最右节点访问完了, 左子树才访问完, 才会轮到根节点)
     * 2.左子树为空, 那就要找它最小的父节点或者祖先节点, 换句话说就是找到当前节点在其父节点的右子树中;
     * 3.左子树为空, 父节点也为空, 那就是根节点
     *
     * @param node 节点
     * @return 可能为null
     */
    private AvlNode<E> predecessor(AvlNode<E> node) {
        if (node.left != null) {
            // 找到左子树的最右边(也即最大)的节点
            AvlNode<E> cur = node.left;
            while (cur.right != null) {
                cur = cur.right;
            }
            return cur;
        }
        // 如果左子树为空, 但是父节点不为空,
        // 找到最小的父节点或祖先节点, 也就是能位于它的右部分
        AvlNode<E> cur = node;
        while (cur.parent != null && cur.parent.left == cur) {
            // 当处于左部分时, 就一直找
            cur = cur.parent;
        }
        return cur.parent;
    }

    /**
     * 获取指定节点的后继节点, 因为是中序遍历的后一个节点, 所以：
     * 1.右子树不为空, 就取右子树的最左节点 (访问完根节点后, 就会访问右子树, 而右子树第一个访问的就是最左那个节点)
     * 2.右子树为空, 那就要找它最大的父节点或者祖先节点, 换句话说就是找到当前节点在其父节点的左子树中;
     * 3.右子树为空, 父节点也为空, 那就是根节点
     *
     * @param node 指定节点
     * @return 指定节点的后继节点
     */
    private AvlNode<E> successor(AvlNode<E> node) {
        if (node.right != null) {
            AvlNode<E> cur = node;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur;
        }
        AvlNode<E> cur = node;
        while (cur.parent != null && cur.parent.right == cur) {
            // 当处于左部分时, 就一直找
            cur = cur.parent;
        }
        return cur.parent;
    }

    /**
     * 递归实现的前序遍历
     *
     * @param node    节点
     * @param visitor 访问者
     */
    private void preorder(AvlNode<E> node, IVisitor<E> visitor) {
        // 递归终止条件, 节点为null
        if (Objects.isNull(node)) {
            return;
        }
        // 先访问根节点
        visitor.visit(node.element);
        // 再访问左子树
        preorder(node.left, visitor);
        // 最后访问右子树
        preorder(node.right, visitor);
    }

    /**
     * 递归实现的中序遍历
     *
     * @param node    节点
     * @param visitor 访问者
     */
    private void inorder(AvlNode<E> node, IVisitor<E> visitor) {
        // 递归终止条件, 节点为null
        if (Objects.isNull(node)) {
            return;
        }
        // 先访问左子树
        inorder(node.left, visitor);
        // 再访问根节点
        visitor.visit(node.element);
        // 最后访问右子树
        inorder(node.right, visitor);
    }

    /**
     * 递归实现的后序遍历
     *
     * @param node    节点
     * @param visitor 访问者
     */
    private void postorder(AvlNode<E> node, IVisitor<E> visitor) {
        // 递归终止条件, 节点为null
        if (Objects.isNull(node)) {
            return;
        }
        // 先访问左子树
        postorder(node.left, visitor);
        // 再访问右子树
        postorder(node.right, visitor);
        // 最后访问根节点
        visitor.visit(node.element);
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
