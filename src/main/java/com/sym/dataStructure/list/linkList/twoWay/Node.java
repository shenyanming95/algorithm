package com.sym.dataStructure.list.linkList.twoWay;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 双向链表使用的节点
 *
 * @author shenym
 */
@Data
@Accessors(chain = true)
public class Node<T> {

    private T data;// 该结点包含的数据项
    private Node<T> next;// 表示此结点的上一个结点
    private Node<T> prev;// 表示此结点的下一个结点

    Node() {
        this(null, null,null);
    }

    Node(T obj) {
        this(obj, null,null);
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
