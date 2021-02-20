package com.sym.structure.list.linked;

import com.sym.structure.list.IList;

import java.util.Objects;

/**
 * 单向链表
 *
 * @author ym.shen
 * @date 2019/9/20
 */
public class SingleLinkedList<T> implements IList<T> {

    /**
     * 单链表的头结点，不计入链表中
     */
    private Node<T> head;

    /**
     * 元素数量
     */
    private int size;

    /**
     * 单向链表结点
     */
    static class Node<T> {
        T data;
        Node<T> next;

        Node() {
            this(null);
        }

        Node(T t) {
            this(t, null);
        }

        Node(T t, Node<T> next) {
            this.data = t;
            this.next = next;
        }

        @Override
        public String toString() {
            return "{" + data + "}";
        }
    }

    public SingleLinkedList() {
        head = new Node<>();
    }

    @Override
    public void add(T t) {
        Node<T> newNode = new Node<>(t);
        size++;
        if (isEmpty()) {
            head.next = newNode;
            return;
        }
        Node<T> temp = head;
        while (Objects.nonNull(temp.next)) {
            temp = temp.next;
        }
        // 此时temp就是当前链表的尾节点
        temp.next = newNode;
    }

    @Override
    public void add(int index, T t) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("illegal index " + index);
        }
        Node<T> newNode = new Node<>(t);
        Node<T> temp = head;
        // 要插入到index这个位置, 就需要找到index-1这个位置
        int i = 0;
        while (Objects.nonNull(temp.next) && i < index) {
            temp = temp.next;
            i++;
        }
        // 插入到链表中间, 此时temp就作为前驱节点.
        if (Objects.nonNull(temp.next)) {
            newNode.next = temp.next;
        }
        temp.next = newNode;
        size++;
    }

    @Override
    public void update(int index, T t) {
        checkIndex(index).findNode(index).data = t;
    }

    @Override
    public void delete(int index) {
        checkIndex(index);
        int i = 0;
        // 删除index位置的节点, 就需要找到index-1位置的节点
        Node<T> temp = head;
        while (i < index) {
            temp = temp.next;
            i++;
        }
        temp.next = temp.next.next;
    }

    @Override
    public T get(int index) {
        return findNode(index).data;
    }

    @Override
    public int indexOf(T t) {
        Node<T> temp = head;
        int i = -1;
        while (Objects.nonNull(temp.next)) {
            temp = temp.next;
            i++;
            if (Objects.equals(temp.data, t)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return Objects.isNull(head.next);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> p = head.next;
        while (p != null) {
            sb.append(p.data).append(", ");
            p = p.next;
        }
        int length = sb.length();
        if (length > 1) {
            sb.delete(length - 2, length);
        }
        return sb.append("]").toString();
    }

    /**
     * 单链表反转, 可以通过临时节点来实现反转操作, 假设单链表为: [1 → 2 → 3 → 4], 整个过程类似：
     * <pre>
     *     反转链表:[p → 1], 原链表:[2 → 3 → 4]
     *     反转链表:[p → 2 → 1], 原链表:[3 → 4]
     *     反转链表:[p → 3 → 2 → 1], 原链表:[4]
     *     反转链表:[p → 4 → 3 → 2 → 1], 原链表:[]
     * </pre>
     * 上面演绎链表节点的动态变化, 最大的关键就是这个 p 节点, 从旧链表一个一个遍历的节点都会放到它的 next 指针上,
     * 这样类似栈一样, 最晚来的排在最前面...
     */
    public void reverse() {
        if (null == head.next || null == head.next.next) {
            return;
        }
        //保存每次遍历旧链表的节点
        Node<T> cur = head.next;
        //保存每次遍历时, 当前节点cur的next节点
        Node<T> next;
        //反转节点
        Node<T> reverseNode = new Node<>();

        while (null != cur) {
            //先保存下一个需要遍历的节点
            next = cur.next;
            //把反转节点旧的next, 保存到当前遍历节点cur的next上
            cur.next = reverseNode.next;
            //将当前遍历节点cur设置为反转节点新的next, 这样子反转节点的新旧next才会关联起来
            reverseNode.next = cur;
            //遍历旧链表的下一个节点
            cur = next;
        }
        head = reverseNode;
    }

    private Node<T> findNode(int index) {
        Node<T> temp = head;
        // 如果i从-1开始, 那么i和节点的位置就是一一对应的;
        // 如果i从0开始, 那么i就比节点位置大1.
        int i = -1;
        while (i < index) {
            temp = temp.next;
            i++;
        }
        return temp;
    }

    private SingleLinkedList checkIndex(int index) {
        if (isEmpty()) {
            throw new IllegalArgumentException("list is empty");
        }
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("illegal index " + index);
        }
        return this;
    }
}
