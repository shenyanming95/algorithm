package com.sym.dataStructure.list.linkList.oneWay;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 单向链表结点
 *
 * Created by shenym on 2019/9/20.
 */
@Data
@Accessors(chain = true)
public class Node<T> {

    private T data;
    private Node<T> next;

    Node(){
        this(null);
    }

    Node(T t){
        this(t,null);
    }

    Node(T t,Node<T> next){
        this.data = t;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{data="+data+"}";
    }
}
