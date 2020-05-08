package com.sym.datastructure.list.linked;

import com.sym.datastructure.list.IList;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 双向链表，表里的每个数据元素包含：数据项、上一个结点的引用、下一个结点的引用
 * 链式存储，底层不再是数组，而是一个一个结点（意味着内存地址不一定连续），结点指向另一个结点，
 * 实际上就是结点中存在一个引用，该引用指向下一个结点；链表可以设计成带有头结点和不带头结点！
 * 头结点和首结点是不一样的，首结点有算入链表里面，而头结点只是为了更好地指向首结点
 *
 * @author ym.shen
 */
public class DoublyLinkedList<T> implements IList<T> {

    /**
     * 链表头结点指针(也可以理解成头结点的引用)，并不算入链表的长度，头结点的存在为了方便地处理首结点
     */
    private Node<T> head;

    /**
     * 链表尾结点指针，它指向链表的尾节点
     */
    private Node<T> tail;

    /**
     * 链表的长度
     */
    private int length;

    /**
     * 双向链表使用的节点
     */
    @Data
    @Accessors(chain = true)
    static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        Node() {
            this(null, null, null);
        }

        Node(T obj) {
            this(obj, null, null);
        }

        private Node(T obj, Node<T> prevNode, Node<T> nextNode) {
            this.data = obj;
            this.prev = prevNode;
            this.next = nextNode;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }

    public DoublyLinkedList() {
        // 初始化头结点
        head = new Node<>();
        tail = null;
        length = 0;
    }

    @Override
    public void add(T t) {
        // 创建数据结点
        Node<T> dataNode = new Node<>(t);

        if (isEmpty()) {
            // 若链表为空，则当前结点作为首结点
            // 并且将尾节点指向它
            head.setNext(dataNode);
            tail = dataNode;
        } else {
            // 若链表不为空，则获取链表的尾结点
            Node<T> temp = tail;
            // 设置新的尾结点
            temp.setNext(dataNode);
            dataNode.setPrev(temp);
            tail = dataNode;
        }
        length++;
    }

    @Override
    public void add(int i, T t) {
        if ((i < 0 || i > length) || (head.getNext() == null && i != 0)) {
            throw new IllegalArgumentException("错误的插入位置：" + i);
        }
        // 如果插入位置i恰好等于length,则相当于当前结点作为新的尾节点
        if (i == length) {
            add(t);
            return;
        }
        int index = -1;
        Node<T> temp = head;
        /*
         * 要插入到指定位置i，所以就要获取到i-1位置的结点，所以循环条件使用index<i，就只会循环到index-1
         */
        while (temp != null && index < i) {
            temp = temp.getNext();
            index++;
        }

        if (temp == null) {
            // 链表为空，初始化即可
            add(t);
            return;
        }

        // 创建新结点
        Node<T> newNode = new Node<>(t);

        // 如果前驱结点为空，说明此时结点temp为首结点
        if (temp.getPrev() == null) {
            newNode.setNext(temp);
            temp.setPrev(newNode);
            head.setNext(newNode);
        } else {
            // 说明temp是中间结点,获取temp结点的前驱结点
            Node<T> preNode = temp.getPrev();
            // 添加新结点
            newNode.setPrev(preNode);
            newNode.setNext(temp);
            preNode.setNext(newNode);
            temp.setPrev(newNode);
        }
        length++;
    }

    @Override
    public void update(int index, T t) {
        // TODO
    }

    @Override
    public void delete(int i) {
        Node<T> p = head;
        int j = -1;
        while (j < i - 1 && p != null) {
            p = p.getNext();
            j++;
        }
        if (j > i - 1 || p == null) {
            throw new RuntimeException("删除的指标有误");
        }
        p.setNext(p.getNext().getNext());
    }

    @Override
    public T get(int i) {

        if (i < 0 || i > length) {
            throw new RuntimeException("找不到第" + i + "个元素,链表区间应该为：[0," + length + "]");
        }

        // 空链表返回null
        if (isEmpty()) {
            return null;
        }
        // 计数器
        int j = 0;
        // 结点p代表首结点
        Node<T> p = head.getNext();
        while (j < i && p != null) {
            p = p.getNext();
            j++;
        }
        return (T) p.getData();
    }

    @Override
    public int indexOf(T o) {
        int j = 0;
        // 首结点
        Node p = head.getNext();
        while (p != null && !p.getData().equals(o)) {
            p = p.getNext();
            j++;
        }
        if (p == null) {
            return -1;
        } else{
            return j;
        }
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return head.getNext() == null;
    }

    @Override
    public void display() {
        Node p = head.getNext();//取到首结点
        while (p != null) {
            System.out.print(p.getData() + " ");
            p = p.getNext();
        }
        System.out.println();
    }
}
