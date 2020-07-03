package com.sym.structure.tree.rbt;

import com.sym.structure.queue.IQueue;
import com.sym.structure.queue.linked.LinkedQueue;
import com.sym.structure.tree.traversal.IVisitor;

import java.util.Comparator;

/**
 * 红黑树的实现, 它其实可以继承{@link com.sym.structure.tree.bst.BinarySearchTree}做延伸,这里为了实现一套完整的逻辑, 单独实现.
 * 红黑树也是一颗二叉搜索树, 在二叉搜索树的基础上加了5个限制：{@link IRedBlackTree},其中最特色的就是引入了节点颜色的概念.红黑树的出现
 * 会为了补足AVL树在极端情况下的删除操作会触发O(logn)次的旋转, 所以红黑树的平衡操作相较于AVL树可能会复杂一点, 操作更多.
 *
 * @author shenyanming
 * @date 2020/6/27 10:31.
 */
public class RedBlackTree<E> implements IRedBlackTree<E> {

    /**
     * 红黑树节点
     *
     * @param <E> 存储的元素类型
     */
    private static class RbtNode<E> {
        E element;
        int color;
        RbtNode<E> parent;
        RbtNode<E> left;
        RbtNode<E> right;

        RbtNode(E e, RbtNode<E> parent) {
            // 默认创建红色节点, 因为在添加的时候, 用红色节点更容易满足红黑树的性质,
            // 所以统一规定默认的红黑树节点创建出来就是红色的
            this(e, parent, RED);
        }

        RbtNode(E e, RbtNode<E> parent, int color) {
            this.element = e;
            this.parent = parent;
            this.color = color;
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
         * 判断节点是否为黑色
         *
         * @return true-黑色
         */
        public boolean isBlack() {
            return color == BLACK;
        }

        /**
         * 将当前节点标志为黑色
         *
         * @return 被标志的节点
         */
        public RbtNode<E> markBlack() {
            this.color = BLACK;
            return this;
        }

        /**
         * 将当前节点标志位红色
         *
         * @return 被标志的节点
         */
        public RbtNode<E> markRed() {
            this.color = RED;
            return this;
        }

        /**
         * 判断当前节点是否为其父节点的左子节点
         *
         * @return true-是左子节点
         */
        public boolean isParentLeftChild() {
            return parent != null && parent.left == this;
        }

        /**
         * 判断当前节点是否为其父节点的右子节点
         *
         * @return true-是右子节点
         */
        public boolean isParentRightChild() {
            return parent != null && parent.right == this;
        }

        /**
         * 获取当前节点的兄弟节点
         *
         * @return 可能为Null
         */
        public RbtNode<E> sibling() {
            if (isParentLeftChild()) {
                // 说明当前节点是否父节点的左子节点, 那么兄弟节点就是父节点的右子节点
                return parent.right;
            }
            if (isParentRightChild()) {
                // 说明当前节点是否父节点的右子节点, 那么兄弟节点就是父节点的左子节点
                return parent.left;
            }
            // 没有父节点, 即根节点
            return null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // 节点自身的信息
            sb.append(element.toString());
            if (color == RED) {
                sb.append("_R");
            }
            // 父节点的信息
            if (parent == null) {
                sb.append("(null)");
            } else {
                sb.append("(").append(parent.element.toString()).append(")");
            }
            return sb.toString();
        }
    }

    /**
     * 这里统一：红色节点用0表示, 黑色结点用1表示
     */
    private final static int RED = 0;
    private final static int BLACK = 1;

    /**
     * 根节点
     */
    private RbtNode<E> root;

    /**
     * 红黑树的元素数量
     */
    private int size;

    /**
     * 用于元素比较大小
     */
    private Comparator<E> comparator;

    /**
     * 无参构造器, 要求元素E自己必须实现{@link Comparable}
     */
    public RedBlackTree() {

    }

    /**
     * 指定比较器, 元素E可以不用实现{@link Comparable}, 通过指定比较器{@link Comparator}
     *
     * @param comparator 比较器
     */
    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 通过层序遍历的方式计算红黑树的高度
     * @return 高度
     */
    @Override
    public int height() {
        if(isEmpty()){
            return 0;
        }
        IQueue<RbtNode<E>> queue = new LinkedQueue<>();
        queue.offer(root);
        // 每一层需要访问的节点个数, 第一层肯定就只需要访问根节点就行了
        int level = 1;
        // 记录高度
        int height = 0;
        // 层序遍历
        while(!queue.isEmpty()){
            // 取出队列的节点
            RbtNode<E> node = queue.poll();
            // 将它的非空左右子节点入队
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
            // 将当前访问层数减一
            level--;
            // 如果level访问完了, 说明这一层访问完毕, 此时队列的大小就是下一层需要访问的节点个数
            if(level == 0){
                level = queue.size();
                height++;
            }
        }
        return height;
    }

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

    @Override
    public void add(E e) {
        if (root == null) {
            // 红黑树的根节点必定是黑色的.
            root = new RbtNode<>(e, null, BLACK);
        } else {
            // 添加其它非根节点
            this.doAdd(e);
        }
        size++;
    }

    @Override
    public void remove(E e) {
    }

    @Override
    public boolean contains(E e) {
        return this.doSearch(e) != null;
    }

    @Override
    public void preorder(IVisitor<E> visitor) {
    }

    @Override
    public void inorder(IVisitor<E> visitor) {
    }

    @Override
    public void postorder(IVisitor<E> visitor) {
    }

    @Override
    public void levelorder(IVisitor<E> visitor) {
    }

    /**
     * 添加非根节点
     *
     * @param e 元素
     */
    private void doAdd(E e) {
        // 记录比较结果
        int result = 0;
        // 当前遍历节点
        RbtNode<E> cur = root;
        // 当前遍历节点的父节点
        RbtNode<E> par = root;
        // 从根节点开始, 进行二分查找, 直到找到为null的节点, 那里就是新节点的位置.
        // 这一步是二叉搜索树的添加逻辑, 红黑树跟AVL数一样, 都得先添加再平衡.
        while (cur != null) {
            // 每次遍历前, 先记录父节点, 供下一次遍历使用
            par = cur;
            // 比较元素大小
            result = this.doCompare(e, cur.element);
            if (result > 0) {
                // 说明新添加的元素比当前节点大, 从当前节点右边再遍历
                cur = cur.right;
            } else if (result < 0) {
                // 说明新添加的元素比当前节点小, 从当期节点左边再遍历
                cur = cur.left;
            } else {
                // 元素值相等, 直接覆盖, 方法返回
                cur.element = e;
                return;
            }
        }
        // 循环终止, 说明找到一个节点为null, 此时父节点为par, 元素值比较结果为result.
        // 创建这个新结点, 默认让它为红色
        RbtNode<E> newNode = new RbtNode<>(e, par);
        if (result > 0) {
            // 说明比父节点大, 放在父节点的右边
            par.right = newNode;
        } else {
            // 说明比父节点小, 放在父节点的左边
            par.left = newNode;
        }
        // 红黑树自平衡处理, 传入新添加的节点
        this.doReBalance(newNode);
    }

    /**
     * 红黑树的自平衡处理.
     * 红黑树可以等价于一颗4阶B树, 变换逻辑是：红黑树的一个黑色节点连同它的左右红色节点(如果有), 可以组成一个4阶B树节点.
     * 因此再判断红黑树添加失衡情况时结合4阶B树的性质, 节点有4种排列分布式：红黑红、黑红、红黑、黑, 这样新节点插入的时候
     * 就会有12种情况(具体要画图演示), 在这12种情况中：
     * - 有4种不需要平衡, 那就是父节点为黑色, 因为直接并过去还是可以保持4阶B树节点的性质;
     * - 有4种只需要染色, 那就是父节点为红色且叔父节点为红色, 因为发生上溢了, 将祖父节点向上合并到更高层的父节点
     * - 有4中既要染色也要旋转, 那就是父节点为红色且叔父节点为黑色, 需要旋转变色, 以满足[红-黑-红]分布
     *
     * @param newNode 新加入的节点
     */
    private void doReBalance(RbtNode<E> newNode) {
        if (newNode.parent == null) {
            // 说明红黑树上溢持续到根节点, 将其染成黑色即可
            newNode.markBlack();
            return;
        }
        // 父节点为黑色, 不需要平衡, 直接返回即可
        if (newNode.parent.isBlack()) {
            return;
        }
        // 父节点为红色, 需要判断叔父节点的颜色(注意此时叔父节点可能为null, 在红黑树中null即nil节点, 所以它是黑色的)
        RbtNode<E> uncle = newNode.parent.sibling();
        boolean isUncleBlack = uncle == null || uncle.isBlack();
        if (isUncleBlack) {
            //【父节点为红色-叔父节点为黑色】
            // 判断新添加节点相较于祖父节点是处于何种分布：LL、RR、LR、RL
            if (newNode.parent.isParentLeftChild()) {
                if (newNode.isParentLeftChild()) {
                    // LL分布, 父节点染成黑色, 祖父节点染成红色, 同时祖父节点右旋转
                    rotateRight(newNode.parent.markBlack().parent.markRed());
                } else {
                    // LR分布, 自己染成黑色, 祖父节点染成红色, 同时父节点左旋转, 再让祖父节点右旋
                    newNode.markBlack();
                    rotateLeft(newNode.parent);
                    rotateRight(newNode.parent.parent.markRed());
                }
            } else {
                if (newNode.isParentLeftChild()) {
                    // RL分布, 自己染成黑色, 祖父节点染成红色, 同时父节点右旋转, 再让祖父节点左旋
                    newNode.markBlack();
                    rotateRight(newNode.parent);
                    rotateLeft(newNode.parent.parent.markRed());
                } else {
                    // RR分布, 父节点染成黑色, 祖父节点染成红色, 祖父节点左旋转
                    rotateLeft(newNode.parent.markBlack().parent.markRed());
                }
            }
        } else {
            //【父节点为红色-叔父节点为红色】
            // 让父节点和叔父节点变为黑色, 独立为一个4阶B树节点
            newNode.parent.markBlack().sibling().markBlack();
            // 让祖父节点变红, 然后向上层父节点合并, 即把祖父节点重新当成一个新添加的节点, 再调用一次doReBalance()方法
            this.doReBalance(newNode.parent.parent.markRed());
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
        RbtNode<E> parentNode = node.parent, rightChildNode = node.right;

        // rightChildNode的左子树, 移动到node的右子节点上
        node.right = rightChildNode.left;
        // node 作为 rightChildNode 的左子树
        rightChildNode.left = node;

        // 维护 parentNode 的子节点指针, rightChildNode 要作为这个子树的新根节点
        if (node.isParentLeftChild()) {
            parentNode.left = rightChildNode;
        } else if (node.isParentRightChild()) {
            parentNode.right = rightChildNode;
        } else {
            // // 说明 node 此时是根节点, 那么 rightChildNode 作为新的根节点
            root = rightChildNode;
        }
        node.parent = rightChildNode;
        rightChildNode.parent = parentNode;
        if (node.right != null) {
            node.right.parent = node;
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
        RbtNode<E> parentNode = node.parent, leftChildNode = node.left;

        // leftChildNode 的右子树变为 node 的左子树
        node.left = leftChildNode.right;
        // node 作为 leftChildNode 的右子树
        leftChildNode.right = node;

        // 维护 parentNode 的子节点指针, leftChildNode 要作为这个子树的新根节点
        if (node.isParentLeftChild()) {
            parentNode.left = leftChildNode;
        } else if (node.isParentRightChild()) {
            parentNode.right = leftChildNode;
        } else {
            // 说明 node 此时是根节点, 那么 leftChildNode 作为新的根节点
            root = leftChildNode;
        }

        // 维护移动节点的父引用
        node.parent = leftChildNode;
        leftChildNode.parent = parentNode;
        if (node.left != null) {
            node.left.parent = node;
        }
    }

    /**
     * 查找某个元素是否存在于红黑树中
     * @param e 元素
     * @return 元素所在的节点, 可能为null
     */
    private RbtNode<E> doSearch(E e){
        RbtNode<E> cur = root;
        while(cur != null){
            int result = doCompare(e, cur.element);
            if(result > 0){
                cur = cur.right;
            }else if(result < 0){
                cur = cur.left;
            }else{
                return cur;
            }
        }
        return null;
    }

    /**
     * 比较两个元素的大小
     *
     * @param e1 第一个元素
     * @param e2 第二个元素
     * @return 若>0说明e1>e2, 若<0说明e1<e2, 若=0说明e1==e2
     */
    @SuppressWarnings("unchecked")
    private int doCompare(E e1, E e2) {
        return comparator != null ? comparator.compare(e1, e2) :
                ((Comparable<E>) e1).compareTo(e2);
    }

    /* 借助外部工具类, 打印二叉树的结构图 - start*/
    @Override
    public Object root() {
        return root;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object left(Object node) {
        return ((RbtNode<E>) node).left;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object right(Object node) {
        return ((RbtNode<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return node.toString();
    }
    /* 借助外部工具类, 打印二叉树的结构图 - end*/
}
