package com.sym.structure.tree.rbt;

import com.sym.structure.stack.IStack;
import com.sym.structure.stack.linked.LinkedStack;
import com.sym.structure.tree.ITree;
import com.sym.structure.tree.bst.BinarySearchTree;

import java.util.Comparator;

/**
 * 红黑树也是平衡二叉树的一种, 所以它也是在二叉搜索树的基础加了其它限制：
 * 1)、根节点为黑色;
 * 2)、任意节点, 要么为红色, 要么为黑色;
 * 3)、红色节点的子节点都为黑色;
 * 4)、任意一个节点到叶子节点的路径, 都包含相同数量的黑色节点;
 * 5)、叶子节点都为黑色, 注意红黑树的叶子节点并不是真正意义上的叶子节点, 是度为0和1的节点扩展出来的nil节点.
 *
 *
 * 红黑树的实现, 它其实可以继承{@link com.sym.structure.tree.bst.BinarySearchTree}做延伸,这里为了实现一套完整的逻辑, 单独实现.
 * 红黑树也是一颗二叉搜索树, 在二叉搜索树的基础上加了5个限制, 其中最特色的就是引入了节点颜色的概念.红黑树的出现
 * 会为了补足AVL树在极端情况下的删除操作会触发O(logn)次的旋转, 所以红黑树的平衡操作相较于AVL树可能会复杂一点, 操作更多.
 *
 * @author shenyanming
 * @date 2020/6/27 10:31.
 */
public class RedBlackTree<E> extends BinarySearchTree<E> {

    public RedBlackTree(){
        super();
    }

    public RedBlackTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 红黑树节点
     *
     * @param <E> 存储的元素类型
     */
    private static class RbtNode<E> extends BstNode<E>{
        /**
         * 这个属性是红黑树维护平衡的关键
         */
        int color;

        RbtNode(E e, RbtNode<E> parent) {
            // 默认创建红色节点, 因为在添加的时候, 用红色节点更容易满足红黑树的性质,
            // 所以统一规定默认的红黑树节点创建出来就是红色的
            this(e, parent, RED);
        }

        RbtNode(E e, RbtNode<E> parent, int color) {
            super(e, parent);
            this.color = color;
        }

        @Override
        public RbtNode<E> left() {
            return (RbtNode<E>)super.left();
        }

        @Override
        public RbtNode<E> right() {
            return (RbtNode<E>)super.right();
        }

        @Override
        public RbtNode<E> parent() {
            return (RbtNode<E>)super.parent();
        }

        public void setLeft(RbtNode<E> node){
            super.left = node;
        }

        public void setRight(RbtNode<E> node){
            super.right = node;
        }

        public void setParent(RbtNode<E> node){
            super.parent = node;
        }

        /**
         * 判断当前节点是否为其父节点的左子节点
         *
         * @return true-是左子节点
         */
        public boolean isParentLeftChild() {
            RbtNode<E> parent = parent();
            return parent != null && parent.left == this;
        }

        /**
         * 判断当前节点是否为其父节点的右子节点
         *
         * @return true-是右子节点
         */
        public boolean isParentRightChild() {
            RbtNode<E> parent = parent();
            return parent != null && parent.right == this;
        }

        /**
         * 获取当前节点的兄弟节点
         *
         * @return 可能为Null
         */
        public RbtNode<E> sibling() {
            RbtNode<E> parent = parent();
            if (isParentLeftChild()) {
                // 说明当前节点是否父节点的左子节点, 那么兄弟节点就是父节点的右子节点
                return parent.right();
            }
            if (isParentRightChild()) {
                // 说明当前节点是否父节点的右子节点, 那么兄弟节点就是父节点的左子节点
                return parent.left();
            }
            // 没有父节点, 即根节点
            return null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // 节点自身的信息
            sb.append(element.toString());
            // 父节点的信息
            if (parent == null) {
                sb.append("(null)");
            } else {
                sb.append("(").append(parent().element.toString()).append(")");
            }
            if (color == RED) {
                sb.append("_R");
            }
            return sb.toString();
        }
    }

    /**
     * 这里统一：红色节点用0表示, 黑色结点用1表示
     */
    private final static int RED = 0;
    private final static int BLACK = 1;

    @Override
    protected RbtNode<E> node(E e, BstNode<E> pNode) {
        return new RbtNode<>(e, (RbtNode<E>)pNode);
    }

    @Override
    protected void afterAdd(BstNode<E> bstNode) {
        RbtNode<E> newNode = (RbtNode<E>)bstNode;
        if(newNode == root){
            // 红黑树的根节点必定是黑色的.
            newNode.color = BLACK;
            return;
        }
        // 红黑树自平衡处理, 传入新添加的节点
        this.reBalanceAfterAdd(newNode);
    }

    @Override
    protected void afterRemove(BstNode<E> bstNode) {
        RbtNode<E> deleteNode = (RbtNode<E>)bstNode;
        // 因为红黑树的删除逻辑需要借助兄弟来判断, 所以先获取它的兄弟结点.
        // 但是这时父节点跟deleteNode的线已经断了(父引用指针还在), 所以要判断父节点的左右子节点哪个为空
        RbtNode<E> parent = deleteNode.parent();
        if(parent == null){
            // 如果被删除的节点父节点为null, 说明这个节点之前是根节点, 而且度为1或0,
            // 但是度为0的情况在二叉搜索树上层已经处理, 所以这边只剩删除的是原先度为1的根节点,
            // 意味着此时的红黑树只剩一个节点了, 那么将其染黑即可.
            black((RbtNode<E>) root);
            return;
        }
        RbtNode<E> sibling = parent.left() == null ? parent.right() : parent.left();
        // 自平衡处理, 红黑树的删除逻辑绝对是超级复杂的一种, 但是它相比较于AVL树, 删除导致的旋转操作
        // 不会达到O(logn)级别, 有学者统计红黑树删除节点导致的旋转操作, 最多不超过3次, 所以是O(1)复杂度
        this.reBalanceAfterRemove(deleteNode, sibling);
    }

    /**
     * 红黑树添加节点后的自平衡处理.
     * 红黑树可以等价于一颗4阶B树, 变换逻辑是：红黑树的一个黑色节点连同它的左右红色节点(如果有), 可以组成一个4阶B树节点.
     * 因此再判断红黑树添加失衡情况时结合4阶B树的性质, 节点有4种排列分布式：红黑红、黑红、红黑、黑, 这样新节点插入的时候
     * 就会有12种情况(具体要画图演示), 在这12种情况中：
     * - 有4种不需要平衡, 那就是父节点为黑色, 因为直接并过去还是可以保持4阶B树节点的性质;
     * - 有4种只需要染色, 那就是父节点为红色且叔父节点为红色, 因为发生上溢了, 将祖父节点向上合并到更高层的父节点
     * - 有4中既要染色也要旋转, 那就是父节点为红色且叔父节点为黑色, 需要旋转变色, 以满足[红-黑-红]分布
     *
     * @param newNode 新加入的节点
     */
    private void reBalanceAfterAdd(RbtNode<E> newNode) {
        // 获取父结点
        RbtNode<E> parent = newNode.parent();
        if (parent == null) {
            // 说明红黑树上溢持续到根节点, 将其染成黑色即可
            black(newNode);
            return;
        }
        if (isBlack(parent)) {
            // 父节点为黑色, 不需要平衡, 直接返回即可
            return;
        }
        // 获取祖父节点
        RbtNode<E> grandParent = parent.parent();

        // 父节点为红色, 需要判断叔父节点的颜色
        boolean isUncleBlack = isBlack(parent.sibling());
        if (isUncleBlack) {
            //【父节点为红色-叔父节点为黑色】
            // 判断新添加节点相较于祖父节点是处于何种分布：LL、RR、LR、RL
            if (parent.isParentLeftChild()) {
                if (newNode.isParentLeftChild()) {
                    // LL分布, 父节点染成黑色, 祖父节点染成红色, 同时祖父节点右旋转
                    black(parent);
                    red(grandParent);
                    rotateRight(grandParent);
                } else {
                    // LR分布, 自己染成黑色, 祖父节点染成红色, 同时父节点左旋转, 再让祖父节点右旋转
                    black(newNode);
                    red(grandParent);
                    rotateLeft(parent);
                    rotateRight(grandParent);
                }
            } else {
                if (newNode.isParentLeftChild()) {
                    // RL分布, 自己染成黑色, 祖父节点染成红色, 同时父节点右旋转, 再让祖父节点左旋
                    black(newNode);
                    red(grandParent);
                    rotateRight(parent);
                    rotateLeft(grandParent);
                } else {
                    // RR分布, 父节点染成黑色, 祖父节点染成红色, 祖父节点左旋转
                    black(parent);
                    red(grandParent);
                    rotateLeft(grandParent);
                }
            }
        } else {
            //【父节点为红色-叔父节点为红色】
            // 让父节点和叔父节点变为黑色, 独立为一个4阶B树节点
            black(parent);
            black(parent.sibling());
            // 让祖父节点变红, 然后向上层父节点合并, 即把祖父节点重新当成一个新添加的节点, 再调用一次doReBalance()方法
            this.reBalanceAfterAdd(red(grandParent));
        }
    }

    /**
     * 红黑树删除节点后的自平衡处理.
     * 删除度为2或者度为1的节点, 实际上会拿其它节点的值来替换它的值, 然后把其它节点删除, 因此红黑树节点删除也是发生在叶子节点上, 所以删除逻辑先分为两种：
     * - 如果删除的是红色节点, 直接删除, 红黑树的性质可以得到保证;
     * - 如果删除的是黑色节点, 那就复杂了, 因为黑色节点一删除, 红黑树"任意节点到其子节点路径上的黑色节点个数一致"这条性质无法保证,
     *   若以B树角度来看, 黑色节点被删除, 意味着B树节点被删除, 那就会发生"下溢", 所以黑色节点删除又分为三种：
     *   ◦ 如果兄弟结点为黑色, 并且有红色子节点, 那可以向兄弟结点借元素;
     *   ◦ 如果兄弟结点为黑色, 但没有红色子节点, 那只能父节点向下合并;
     *   ◦ 如果兄弟结点为红色, 那说明必有黑色侄子结点, 让黑色侄子节点变为兄弟结点, 然后重复上面两个判断逻辑.
     * 另外地, 由于被删除节点可能是右子节点, 也可能是左子节点, 所以在判断左右和处理旋转时, 要考虑两种情况, 而且这两种情况是完美对称的,
     * 这里先暂时考虑被删除节点都位于它父节点的右子树, 最后将代码复制一份进行左右互换, 以满足被删除节点在左边的情况
     *
     * @param deleteNode 实际被删除的节点
     * @param sibling 实际被删节点的兄弟结点
     */
    private void reBalanceAfterRemove(RbtNode<E> deleteNode, RbtNode<E> sibling) {
        if(isRed(deleteNode)){
            // 实际被删除的节点是红色, 直接删除, 不需要再做平衡.
            return;
        }
        // 获取父节点信息
        RbtNode<E> parent = deleteNode.parent();
        if(parent == null) {
            // 红黑树删除节点的下溢持续到了根节点, 直接返回
            return;
        }
        /*
         * 先考虑被删除节点位于其父节点的右子树中.(因为父节点 → 删除节点之间的引用已经断开了, 所以父节点右边为空, 说明被删除节点原先就位于父节点右子树中)
         * 注意不要通过deleteNode.isParentRight()来判断, 此时父引用指针已经断了
         */
        if(parent.right() == null){
            // 兄弟节点为黑色 (注意因为当前被删节点已经保证是黑色了, 所以它必定会有兄弟节点, 不然红黑树性质保证不了!!! )
            if(isBlack(sibling)){
                if(sibling.degree() == ITree.DEGREE_ZERO){
                    // 兄弟结点没有子节点, 只能让父节点下来合并, 所以判断关键转到了父节点：
                    // - 若父节点为红色, 那么父节点下来以后, 父节点原先那一层还有节点, 就不会发生下溢;
                    // - 若父节点为黑色, 那么父节点下来以后, 父节点原先那一层没节点了, 也会发生下溢.
                    if(isRed(parent)){
                        // 父节点红色, 将它染黑, 弥补当前下溢情况, 同时兄弟节点染红.
                        black(parent);
                        red(sibling);
                    }else{
                        // 父节点自己就是黑色, 那它下来合并弥补下溢后, 它自己原来那一层也会发生下溢, 递归调用当前方法, 让它重新平衡.
                        // 注意兄弟还是要染成红色的.
                        red(sibling);
                        this.reBalanceAfterRemove(parent, parent.sibling());
                    }
                }else{
                    // 一旦兄弟(黑色)节点有子节点, 那么必定为红色(因为当前被删除节点肯定是叶子节点, 如果它兄弟节点还有黑色子节点, 红黑树的性质根本保证不了).
                    // 这时候要做的就是类似B树下溢时的“借元素”, 让父节点旋转下来替代被删节点的位置. 注意当前if语句块考虑的是被删除节点位于其父节点的右边,
                    // 最好自己画图理清.
                    if(sibling.left() != null){
                        // 如果兄弟节点有左子树(可能是只有左子树即度为1, 也可能有左右子树即度为2, 当兄弟节点度为2时, 可以有两种处理, 这边采用简单点的).
                        // 直接让父节点右旋转, 同时兄弟节点继承父节点的颜色(可能红可能黑), 然后父节点和兄弟节点的子节点都变黑
                        sibling.color = parent.color;
                        black(sibling.left());
                        black(parent);
                        rotateRight(parent);
                    }else{
                        // 如果兄弟节点只有右子树, 那就是LR分布了, 需要让兄弟节点先左旋转, 再让父节点右旋转, 其结果就是让兄弟节点的子节点(侄子节点)成为这棵子树新的根节点.
                        // 同时兄弟节点的子节点继承原先父节点的颜色, 父节点和兄弟节点各自变黑
                        sibling.right().color = parent.color;
                        black(sibling);
                        black(parent);
                        rotateLeft(sibling);
                        rotateRight(parent);
                    }
                }
            // 兄弟结点为红色, 要让侄子节点变为兄弟结点, 所以需要对父节点右旋转
            }else{
                // 兄弟节点为红色, 意味着父节点为黑色, 那就意味着兄弟节点必定拥有2个红色子节点(否则父节点到其叶子节点的路径上黑色节点的个数就不符合了).
                // 此时让兄弟节点变为黑色, 父节点变为红色, 同时父节点右旋转, 让侄子节点升级为兄弟节点
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 最后就回到兄弟节点为黑色, 父节点为红色的平衡逻辑, 递归调用当前方法重新平衡即可
                this.reBalanceAfterRemove(deleteNode, parent.left());
            }
        /*
         * 再考虑被删除节点位于其父节点的左子树中. (与上面的思路完全一样, 只不过旋转方向变了)
         */
        }else{
            // 兄弟节点为黑色 (注意因为当前被删节点已经保证是黑色了, 所以它必定会有兄弟节点, 不然红黑树性质保证不了!!! )
            if(isBlack(sibling)){
                if(sibling.degree() == ITree.DEGREE_ZERO){
                    // 兄弟结点没有子节点, 只能让父节点下来合并, 所以判断关键转到了父节点：
                    // - 若父节点为红色, 那么父节点下来以后, 父节点原先那一层还有节点, 就不会发生下溢;
                    // - 若父节点为黑色, 那么父节点下来以后, 父节点原先那一层没节点了, 也会发生下溢.
                    if(isRed(parent)){
                        // 父节点红色, 将它染黑, 弥补当前下溢情况, 同时兄弟节点染红.
                        black(parent);
                        red(sibling);
                    }else{
                        // 父节点自己就是黑色, 那它下来合并弥补下溢后, 它自己原来那一层也会发生下溢, 递归调用当前方法, 让它重新平衡.
                        // 注意兄弟还是要染成红色的.
                        red(sibling);
                        this.reBalanceAfterRemove(parent, parent.sibling());
                    }
                }else{
                    // 一旦兄弟(黑色)节点有子节点, 那么必定为红色(因为当前被删除节点肯定是叶子节点, 如果它兄弟节点还有黑色子节点, 红黑树的性质根本保证不了).
                    // 这时候要做的就是类似B树下溢时的“借元素”, 让父节点旋转下来替代被删节点的位置. 注意当前if语句块考虑的是被删除节点位于其父节点的左边,
                    // 最好自己画图理清.
                    if(sibling.right() != null){
                        // 如果兄弟节点有右子树(可能是只有右子树即度为1, 也可能有左右子树即度为2, 当兄弟节点度为2时, 可以有两种处理, 这边采用简单点的).
                        // 直接让父节点左旋转, 同时兄弟节点继承父节点的颜色(可能红可能黑), 然后父节点和兄弟节点的子节点都变黑
                        sibling.color = parent.color;
                        black(sibling.right());
                        black(parent);
                        rotateLeft(parent);
                    }else{
                        // 如果兄弟节点只有左子树, 那就是RL分布了, 需要让兄弟节点先右旋转, 再让父节点左旋转, 其结果就是让兄弟节点的子节点(侄子节点)成为这棵子树新的根节点.
                        // 同时兄弟节点的子节点继承原先父节点的颜色, 父节点和兄弟节点各自变黑
                        sibling.left().color = parent.color;
                        black(sibling);
                        black(parent);
                        rotateRight(sibling);
                        rotateLeft(parent);
                    }
                }
            // 兄弟结点为红色, 要让侄子节点变为兄弟结点, 所以需要对父节点左旋转
            }else{
                // 兄弟节点为红色, 意味着父节点为黑色, 那就意味着兄弟节点必定拥有2个红色子节点(否则父节点到其叶子节点的路径上黑色节点的个数就不符合了).
                // 此时让兄弟节点变为黑色, 父节点变为红色, 同时父节点左旋转, 让侄子节点升级为兄弟节点
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 最后就回到兄弟节点为黑色, 父节点为红色的平衡逻辑, 递归调用当前方法重新平衡即可
                this.reBalanceAfterRemove(deleteNode, parent.right());
            }
        }
    }

    /**
     * 让节点执行左旋转
     *        |
     *        o  <---- 待旋转节点node
     *      /   \
     *     o     o
     *         /   \
     *        o     o
     *
     * @param node 待旋转节点
     */
    private void rotateLeft(RbtNode<E> node) {
        //node的父节点、右子节点
        RbtNode<E> parentNode = node.parent(), rightChildNode = node.right();

        // rightChildNode的左子树, 移动到node的右子节点上
        node.setRight(rightChildNode.left());
        // node 作为 rightChildNode 的左子树
        rightChildNode.setLeft(node);

        // 维护 parentNode 的子节点指针, rightChildNode 要作为这个子树的新根节点
        if (node.isParentLeftChild()) {
            parentNode.setLeft(rightChildNode);
        } else if (node.isParentRightChild()) {
            parentNode.setRight(rightChildNode);
        } else {
            // // 说明 node 此时是根节点, 那么 rightChildNode 作为新的根节点
            root = rightChildNode;
        }
        node.setParent(rightChildNode);
        rightChildNode.setParent(parentNode);
        if (node.right() != null) {
            node.right().setParent(node);
        }
    }

    /**
     * 让节点执行右旋转
     *          |
     *          o  <--- 待旋转节点node
     *        /   \
     *       o     o
     *      / \
     *     o   o
     *
     * @param node 待旋转节点
     */
    private void rotateRight(RbtNode<E> node) {
        // 待旋转节点的父节点和左子节点
        RbtNode<E> parentNode = node.parent(), leftChildNode = node.left();

        // leftChildNode 的右子树变为 node 的左子树
        node.setLeft(leftChildNode.right());
        // node 作为 leftChildNode 的右子树
        leftChildNode.setRight(node);

        // 维护 parentNode 的子节点指针, leftChildNode 要作为这个子树的新根节点
        if (node.isParentLeftChild()) {
            parentNode.setLeft(leftChildNode);
        } else if (node.isParentRightChild()) {
            parentNode.setRight(leftChildNode);
        } else {
            // 说明 node 此时是根节点, 那么 leftChildNode 作为新的根节点
            root = leftChildNode;
        }

        // 维护移动节点的父引用
        node.setParent(leftChildNode);
        leftChildNode.setParent(parentNode);
        if (node.left() != null) {
            node.left().setParent(node);
        }
    }

    /**
     * 判断节点是否为黑色
     *
     * @return true-黑色
     */
    private boolean isBlack(RbtNode<?> node){
        return node == null || node.color == BLACK;
    }

    /**
     * 判断节点是否为红色
     *
     * @return true-红色
     */
    private boolean isRed(RbtNode<?> node){
        return node != null && node.color == RED;
    }

    private RbtNode<E> red(RbtNode<E> node){
        return color(node, RED);
    }

    private RbtNode<E> black(RbtNode<E> node){
        return color(node, BLACK);
    }

    private RbtNode<E> color(RbtNode<E> node, int color){
        if(node != null){
            node.color = color;
        }
        return node;
    }

    /**
     * 父类{@link BinarySearchTree#preOrder(IVisitor)}是通过递归实现的前序遍历,
     * 这里采用循环的方式来实现, 思路是：
     * 首先明确, 前序遍历是, 先访问根节点, 再访问左子树, 后访问右子树.
     *        o  <-- 根节点
     *      /  \
     *     o    o <-- 访问左节点, 右节点入栈
     *   /  \
     *  o    o <-- 访问左节点, 右节点入栈
     *  ↑
     *  ↑
     * 从这边开始结束访问, 从栈弹出元素, 继续按照上边逻辑访问
     * ---------------------------------------------------------------------------------------
     *  其实有个规律, 我们从根节点开始遍历, 首先一定会先访问根节点, 然后访问左子树, 左子树访问完才会访问右子树;
     *  左子树又会先访问它的根节点, 然后访问它的左子树...以此类推, 直至一颗子树n, 它的左子节点为null;
     *  然后就会访问n的右子树, 其中n的右子树也满足上面两行的规律;
     *  子树n的右子树访问完以后, 整颗子树n就已经访问完, 这样n的父节点p, p的左子树就访问完了, 就访问p的右子树;
     *  ...以此类推, 可以使用栈, 每访问一个根节点时, 就存入它的右节点, 这样处理后, 栈顶元素最终就是n的右子树.
     *
     * @param visitor 访问者
     */
    @Override
    public void preOrder(IVisitor<E> visitor) {
        if(isEmpty() || visitor == null)  return;
        IStack<BstNode<E>> stack = new LinkedStack<>();
        BstNode<E> node = root;
        for(;;){
            if(node != null){
                // 若子树的根节点不为null, 那么根据前序遍历规则, 优先访问根节点
                visitor.visit(node.element());
                // 右子树需要等待左子树访问完, 所以将其入栈, 上层的右子树最晚访问, 最早入栈, 越近栈底.
                if(node.right() != null){
                    stack.push(node.right());
                }
                // 然后一直往左找, 类似递归的思想, 左子树访问完才能访问右子树;
                // 而左子树也要等它的左子树访问完, 直到没有左子树了, 这就是递归的终止条件~
                node = node.left();
            }else if(stack.isEmpty()){
                // 栈为空, 所有元素都访问完, 跳出循环
                break;
            }else{
                // 这边就找到了上面所说的递归终止条件, 也即左子树为空, 类似这样子：
                //     o
                //   /
                //  o    <-- 向左找的终止条件.
                //    \
                //      o <-- 若右子树存在, 那么这里就是栈顶.
                //
                // 将栈顶元素取出来, 让它重复前序遍历的操作
                node = stack.pop();
            }
        }
    }

    /**
     * 父类{@link BinarySearchTree#inOrder(IVisitor)} 是通过递归实现的中序遍历,
     * 这里采用循环的方式来实现, 思路是：
     * 首先明确, 中序遍历是, 先访问左子树, 再访问根节点, 后访问右子树.
     *        o  <-- 根节点
     *      /  \
     *     o    o <-- 根节点入栈, 访问左子树
     *   /  \
     *  o    o <-- 根节点入栈, 访问左子树
     *  ↑
     *  ↑
     * 从这边开始结束向左访问(它的左子树为null, 等价于访问完它的左子树), 此时就需要访问它(访问子树根节点),
     * 最后就访问右子树, 按照上边逻辑对右子树继续中序遍历.
     *
     * @param visitor 访问者
     */
    @Override
    public void inOrder(IVisitor<E> visitor) {
        if(isEmpty() || visitor == null) return;
        BstNode<E> node = root;
        IStack<BstNode<E>> stack = new LinkedStack<>();
        for(;;){
            if(node != null){
                // 子树根节点入栈
                stack.push(node);
                // 向左一直找
                node = node.left();
            }else if(stack.isEmpty()){
                // 栈为空, 没有任何元素
                break;
            }else{
                // 根节点为空, 意味着找到最左边元素了, 此时它位于栈顶.
                // 要明白一件事, 栈顶这个元素类似这样子:
                //        o
                //      /
                //     o  <-- 当期栈顶, 整个遍历过程中, 最早访问的节点.
                //       \
                //         o <-- 可能有右子树, 也可能么有.
                //
                // 所以将它取出来, 访问, 然后取出它的右子树, 如果右子树不为空, 就进入中序遍历的逻辑;
                // 如果右子树为空, 则再取出上一个根节点
                BstNode<E> pop = stack.pop();
                visitor.visit(pop.element());
                node = pop.right();
            }
        }
    }

    /**
     * 父类{@link BinarySearchTree#postOrder(IVisitor)} 是通过递归实现的后序遍历,
     * 这里采用循环的方式来实现, 思路是：
     * 首先明确, 后序遍历是, 先访问左子树, 再访问右子树, 后访问根节点.
     *        o  <-- 根节点
     *      /  \
     *     o    o <--
     *   /  \
     *  o    o <--
     *  ↑
     *  ↑
     *
     * @param visitor 访问者
     */
    @Override
    public void postOrder(IVisitor<E> visitor) {
        if(isEmpty() || visitor == null) return;
        BstNode<E> prev = null;
        BstNode<E> node = root;
        IStack<BstNode<E>> stack = new LinkedStack<>();
        for(;;){
        }
    }
}
