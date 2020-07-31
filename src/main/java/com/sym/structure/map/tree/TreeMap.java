package com.sym.structure.map.tree;

import com.sym.structure.map.IMap;
import com.sym.structure.queue.IQueue;
import com.sym.structure.queue.linked.LinkedQueue;
import com.sym.structure.tree.ITree;
import java.util.Comparator;
import java.util.Objects;

/**
 * 自己实现的{@link java.util.TreeMap}, 可以让元素按照一定顺序保存(默认按照key升序.)
 * 所以TreeMap要求key具备可比较性, 基于这一性质, TreeMap可以使用红黑树实现.
 *
 * @author shenyanming
 * @date 2020/7/19 12:33.
 */
public class TreeMap<K, V> implements IMap<K, V> {

    /**
     * 这里统一：红色节点用0表示, 黑色结点用1表示
     */
    private final static int RED = 0;
    private final static int BLACK = 1;

    public TreeMap() {
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    /**
     * TreeMap节点, 其实就是红黑树节点
     *
     * @param <K> 键
     * @param <V> 值
     */
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        int color;

        public Node(K key, V value, Node<K, V> pNode) {
            // 默认创建红色节点, 因为在添加的时候, 用红色节点更容易满足红黑树的性质,
            // 所以统一规定默认的红黑树节点创建出来就是红色的
            this(key, value, pNode, RED);
        }

        public Node(K key, V value, Node<K, V> pNode, int color) {
            this.key = key;
            this.value = value;
            this.parent = pNode;
            this.color = color;
        }

        /**
         * 判断当前节点是否为, 其父节点的左子节点
         *
         * @return true-左子节点
         */
        public boolean isParentLeftChild() {
            return parent != null && parent.left == this;
        }

        /**
         * 判断当前节点是否为, 其父节点的右子节点
         *
         * @return true-右子节点
         */
        public boolean isParentRightChild() {
            return parent != null && parent.right == this;
        }

        /**
         * 获取当前节点的兄弟结点
         *
         * @return 可能为null
         */
        public Node<K, V> sibling() {
            if (isParentLeftChild()) {
                return parent.right;
            } else if (isParentRightChild()) {
                return parent.left;
            } else {
                return null;
            }
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
    }

    /**
     * 根节点
     */
    private Node<K, V> root;

    /**
     * TreeMap的容量
     */
    private int size;

    /**
     * 比较器
     */
    private Comparator<K> comparator;


    @Override
    public V put(K key, V value) {
        if(root == null){
            root = new Node<>(key, value, null, BLACK);
        }else{
            int compare = 0;
            Node<K, V> n = root;
            Node<K, V> p = root;
            while(n != null){
                p = n;
                compare = doCompare(key, n.key);
                if(compare > 0){
                    n = n.right;
                }else if(compare < 0){
                    n = n.left;
                }else{
                    V oldValue = n.value;
                    n.value = value;
                    return oldValue;
                }
            }
            Node<K, V> newNode = new Node<>(key, value, p, RED);
            if(compare > 0){
                p.right = newNode;
            }else{
                p.left = newNode;
            }
            // reBalance
            afterAdd(newNode);
        }
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = doSearch(key);
        return node == null ? null : node.value;
    }

    @Override
    public void remove(K key) {
        Node<K, V> node = doSearch(key);
        if(node == null){
            return;
        }
        size --;
        int degree = node.degree();
        if(degree == ITree.DEGREE_TWO){
            Node<K, V> successor = successor(node);
            node.value = successor.value;
            node = successor;
        }
        if(degree == ITree.DEGREE_ZERO && node == root){
            root = null;
            return;
        }
        Node<K, V> child = node.left != null ? node.left : node.right;
        if(child == null){
            // 度为0
            if(node.parent.left == node){
                node.parent.left = null;
            }else{
                node.parent.right = null;
            }
        }else{
            // 度为1
            child.parent = node.parent;
            if(child.parent == null){
                // 只剩2个节点, 并且删除的就是根节点
                root = child;
            }else if(node.parent.left == node){
                node.parent.left = child;
            }else{
                node.parent.right = child;
            }
        }
        // re Balance
        afterRemove(node);
    }

    @Override
    public int size() {
        return size;
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
    public boolean containKey(K key) {
        return doSearch(key) != null;
    }

    @Override
    public boolean containValue(V value) {
        if(isEmpty()){
            return false;
        }
        // 查value跟查key不一样, 只能靠遍历, 这里采用层序遍历
        IQueue<Node<K, V>> queue = new LinkedQueue<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node<K, V> n = queue.poll();
            if(Objects.equals(value, n.value)){
                return true;
            }
            if(n.left != null){
                queue.offer(n.left);
            }
            if(n.right != null){
                queue.offer(n.right);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if(isEmpty()){
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        inOrder(root, sb);
        return sb.substring(0, sb.length() - 1) + "}";
    }

    private void inOrder(Node<K, V> node, StringBuilder sb){
        if(node == null){
            return;
        }
        // 访问左子树
        inOrder(node.left, sb);
        // 处理根节点
        sb.append(node.key).append("=").append(node.value).append(",");
        // 访问右子树
        inOrder(node.right, sb);
    }

    private void afterAdd(Node<K,V> newNode) {
        Node<K, V> parentNode = newNode.parent;
        if(parentNode == null){
            // 红黑树上溢持续到根节点
            black(newNode);
            return;
        }
        if(isBlack(parentNode)){
            // 父节点为黑色, 直接添加, 不影响红黑树平衡
            return;
        }
        // 获取祖父节点
        Node<K, V> grandParentNode = parentNode.parent;
        // 获取叔父节点(可能为null)
        Node<K, V> uncleNode = parentNode.sibling();

        if(isBlack(uncleNode)){
            // 叔父节点为黑色
            if(parentNode.isParentLeftChild()){
                if(newNode.isParentLeftChild()){
                    // LL分布
                    black(parentNode);
                    red(grandParentNode);
                }else{
                    // LR分布
                    black(newNode);
                    red(grandParentNode);
                    rotateLeft(parentNode);
                }
                rotateRight(grandParentNode);
            }else{
                if(newNode.isParentLeftChild()){
                    // RL分布
                    black(newNode);
                    red(grandParentNode);
                    rotateRight(parentNode);
                }else{
                    // RR分布
                    black(parentNode);
                    red(grandParentNode);
                }
                rotateLeft(grandParentNode);
            }
        }else{
            // 叔父节点为红色
            black(parentNode);
            black(uncleNode);
            red(grandParentNode);
            afterAdd(grandParentNode);
        }
    }

    private void afterRemove(Node<K, V> node){
        Node<K, V> parent = node.parent;
        if(parent == null){
            black(root);
            return;
        }
        Node<K, V> sibling = parent.left == null ? parent.right : parent.left;
        afterRemove(node, sibling);
    }

    private void afterRemove(Node<K, V> deleteNode, Node<K, V> sibling){
        if(isRed(deleteNode)){
            return;
        }
        Node<K, V> parent = deleteNode.parent;
        if(parent == null){
            // 下溢持续到根节点
            return;
        }
        if(parent.right == null){
            // 先考虑被删除节点位于父节点右子树部分
            if(isBlack(sibling)){
                if(sibling.degree() == ITree.DEGREE_ZERO){
                    if(isRed(parent)){
                        black(parent);
                        red(sibling);
                    }else{
                        red(sibling);
                        afterRemove(parent, parent.sibling());
                    }
                }else{
                    if(sibling.left != null){
                        sibling.color = parent.color;
                        black(sibling.left);
                        black(parent);
                        rotateRight(parent);
                    }
                }
            }else{
                // 兄弟节点为红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                afterRemove(deleteNode, parent.left);
            }
        }else{
            // 再考虑被删除节点位于父节点左子树部分
            if(isBlack(sibling)){
                if(sibling.degree() == ITree.DEGREE_ZERO){
                    if(isRed(parent)){
                        black(parent);
                        red(sibling);
                    }else{
                        red(sibling);
                        afterRemove(parent, parent.sibling());
                    }
                }else{
                    if(sibling.right != null){
                        sibling.color = parent.color;
                        black(sibling.right);
                        black(parent);
                        rotateLeft(parent);
                    }
                }
            }else{
                // 兄弟节点为红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                afterRemove(deleteNode, parent.right);
            }
        }
    }

    /**
     * 右旋转
     * @param node 节点
     */
    private void rotateRight(Node<K, V> node){
        Node<K, V> pNode = node.parent, leftChildNode = node.left;

        node.left = leftChildNode.right;
        leftChildNode.right = node;

        if(node.isParentLeftChild()){
            pNode.left = leftChildNode;
        }else if(node.isParentRightChild()){
            pNode.right = leftChildNode;
        }else{
            // 根节点
            root = leftChildNode;
        }

        node.parent = leftChildNode;
        leftChildNode.parent = pNode;
        if(node.left != null){
            node.left.parent = node;
        }
    }

    /**
     * 左旋转
     * @param node 节点
     */
    private void rotateLeft(Node<K, V> node){
        Node<K, V> pNode = node.parent, rightChildNode = node.right;

        node.right = rightChildNode.left;
        rightChildNode.left = node;

        if(node.isParentLeftChild()){
            pNode.left = rightChildNode;
        }else if(node.isParentRightChild()){
            pNode.right = rightChildNode;
        }else{
            root = rightChildNode;
        }

        node.parent = rightChildNode;
        rightChildNode.parent = pNode;
        if(node.right != null){
            node.right.parent = node;
        }
    }

    /**
     * 比较key的大小
     */
    @SuppressWarnings("unchecked")
    private int doCompare(K k1, K k2){
        // 如果有指定比较器, 通过比较器比较大小;
        // 如果没有指定比较器, 默认E可比较(即实现Comparable接口)
        return null != this.comparator ? comparator.compare(k1, k2) :
                ((Comparable<K>) k1).compareTo(k2);
    }

    /**
     * 搜索逻辑, 一个简单的二分查找
     * @param key 键
     * @return 可能为null
     */
    private Node<K, V> doSearch(K key){
        Node<K, V> n = root;
        while(n != null){
            int r = doCompare(key, n.key);
            if(r > 0){
                n = n.right;
            }else if(r < 0){
                n = n.left;
            }else{
                return n;
            }
        }
        return null;
    }

    /**
     * 获取指定节点的后继节点
     * @param node 节点
     * @return 后继节点
     */
    private Node<K, V> successor(Node<K, V> node){
        if(node == null){
            return null;
        }
        // 其右子树不为空, 则从右子树一直找最左边节点
        if(node.right != null){
            Node<K, V> n = node.right;
            while(n.left != null){
                n = n.left;
            }
            return n;
        }
        // 若右子树为空, 从祖先节点找, 一直找到最大父节点(位于祖先节点的左子树部部分)
        Node<K, V> n = node;
        while(n.isParentRightChild()){
            n = n.parent;
        }
        return n;
    }

    /**
     * 节点标黑
     * @param node 节点
     */
    private void black(Node<K, V> node){
        if(node != null){
            node.color = BLACK;
        }
    }

    /**
     * 节点标红
     * @param node 节点
     */
    private void red(Node<K, V> node){
        if(node != null){
            node.color = RED;
        }
    }

    /**
     * 判断节点是否为黑色
     * @param node 节点
     * @return true-黑色
     */
    private boolean isBlack(Node<K, V> node){
        return node == null || node.color == BLACK;
    }

    /**
     * 判断节点是否为红色
     * @param node 节点
     * @return true-红色
     */
    private boolean isRed(Node<K, V> node){
        return node != null && node.color == RED;
    }
}
