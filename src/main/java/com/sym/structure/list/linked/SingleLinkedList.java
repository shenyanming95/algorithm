package com.sym.structure.list.linked;

import com.sym.structure.list.IList;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * 单向链表
 *
 * @author ym.shen
 * @date 2019/9/20
 */
public class SingleLinkedList<T> implements IList<T> {

    /**
     * 单链表的头结点，不计入链表的内容中
     */
    private Node<T> head;

    /**
     * 单向链表结点
     */
    @Data
    @Accessors(chain = true)
    static class Node<T> {
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

    public SingleLinkedList() {
        head = new Node<>();
    }

    @Override
    public void add(T t) {
        Node<T> newNode = new Node<>(t);
        if (isEmpty()) {
            head.setNext(newNode);
        } else {
            Node<T> temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            // 此时temp就是当前链表的尾节点
            temp.setNext(newNode);
        }
    }

    @Override
    public void add(int index, T t) {
        if (index < 0) {
            throw new IllegalArgumentException("插入数组下标位置有误：" + index);
        }
        Node<T> newNode = new Node<>(t);
        Node<T> temp = head;
        int i = 0;
        while (null != temp && i < index) {
            temp = temp.getNext();
            i++;
        }
        if (null == temp) {
            throw new IllegalArgumentException("插入数组下标位置有误：" + index);
        }
        /*
         * 等遍历完，如果temp说明已经到链表最后，如果此时index = i,说明这时相当于插到链表尾巴
         * 如果i < index 说明插入链表中间
         */
        if (i == index) {
            // 插入到链表尾巴，此时temp就是尾节点
        } else {
            // 插入到链表中间，此时temp就是待插入位置的上一个结点
            newNode.setNext(temp.getNext());
        }
        temp.setNext(newNode);
    }

    @Override
    public void update(int index, T t) {
        getByIndex(index).setData(t);
    }

    @Override
    public void delete(int index) {
        if (index < 0 || isEmpty()) {
            throw new IllegalArgumentException("数组下标位置有误：" + index);
        }
        int i = 0;
        Node<T> temp = head;
        while (null != temp && i < index) {
            temp = temp.getNext();
            i++;
        }
        if (null == temp) {
            throw new IllegalArgumentException("数组下标位置有误：" + index);
        }
        // 此时temp就是待删除结点的上一个结点
        temp.setNext(temp.getNext().getNext());
    }

    @Override
    public T get(int index) {
        return getByIndex(index).getData();
    }

    @Override
    public int indexOf(T t) {
        Node<T> temp = head;
        int i = -1;
        while (null != temp.getNext()) {
            temp = temp.getNext();
            i++;
            if (Objects.equals(temp.getData(), t)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        int i = 0;
        Node<T> temp = head;
        while (null != temp.getNext()) {
            temp = temp.getNext();
            i++;
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return null == head.getNext();
    }


    @Override
    public void display() {
        Node<T> p = head.getNext();
        while (p != null) {
            System.out.print(p.getData() + " ");
            p = p.getNext();
        }
        System.out.println();
    }


    /**
     * 单链表的反转操作, 算法思想是这样：
     * <p>
     * 假设有一个单链表是这样：[1 → 2 → 3 → 4], 通过一个临时节点 reverseNode ,由它来保存新链表的最新头结点, 整个过程类似：
     * 1)、新：[reverseNode → 1], 旧：[2 → 3 → 4]
     * 2)、新：[reverseNode → 2 → 1], 旧：[3 → 4]
     * 3)、新：[reverseNode → 3 → 2 → 1], 旧：[4]
     * 4)、新：[reverseNode → 4 → 3 → 2 → 1], 旧：[]
     * 当然上面的新旧并不是创建了新的链表, 而为了显示演绎链表节点的动态变化, 最大的关键就是这个 reverseNode 节点, 我们从旧链表一个一个遍历的节点
     * 都会放到它的 next 指针上, 这样类似栈一样, 最晚来的排在最前面...
     */
    public void reverse() {
        if (null == head.getNext() || null == head.getNext().getNext()) {
            System.out.println("单链表为空, 或只有一个节点, 无法反转");
            return;
        }
        //保存每次遍历旧链表的节点
        Node<T> cur = head.getNext();
        //保存每次遍历时, 当前节点cur的next节点
        Node<T> next;
        //反转节点
        Node<T> reverseNode = new Node<>();

        while (null != cur) {
            //先保存下一个需要遍历的节点
            next = cur.getNext();
            //把反转节点旧的next, 保存到当前遍历节点cur的next上
            cur.setNext(reverseNode.getNext());
            //将当前遍历节点cur设置为反转节点新的next, 这样子反转节点的新旧next才会关联起来
            reverseNode.setNext(cur);
            //遍历旧链表的下一个节点
            cur = next;
        }
        head = reverseNode;

//        下面这种方式其实就是上面的详细解释, 只不过可以省略成上面那个样子
//        Node<T> cur = head.getNext(); //保存每次遍历旧链表的节点
//        Node<T> next = null; //保存每次遍历时, 当前节点cur的next节点
//        Node<T> reverseNode = new Node<>(); //反转节点
//        Node<T> reverseNextNode = new Node<>(); //反转节点的下一个节点
//
//        while( null != cur ){
//            next = cur.getNext(); //保存当前遍历节点的下一个节点
//            reverseNextNode = reverseNode.getNext(); //保存反转节点的下一个节点
//            reverseNode.setNext(cur); //将当前遍历节点cur设置为反转节点的新的next节点
//            cur.setNext(reverseNextNode); //把反转节点的旧的next节点与cur关联, 它应该放在cur的后面, 所以是next节点
//            cur = next; //继续遍历, 取原链表的下一个节点
//        }
    }


    private Node<T> getByIndex(int index) {
        if (isEmpty() || index < 0) {
            throw new IllegalArgumentException("数组下标位置有误：" + index);
        }
        Node<T> temp = head;
        int i = -1;
        while (temp != null && i < index) {
            temp = temp.getNext();
            i++;
        }
        if (null == temp) {
            throw new IllegalArgumentException("数组下标位置有误：" + index);
        }
        return temp;
    }
}
