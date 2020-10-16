package com.sym.structure.map.linked;

import com.sym.structure.map.hash.HashMap;

import java.util.Objects;

/**
 * LinkedHashMap的节点继承自{@link com.sym.structure.map.hash.HashMap}的节点, 然后它加入了两个
 * 链表指针prev和next, 通过这两个指针维护元素的添加顺序, 其它情况就和哈希表一样.
 *
 * @author shenyanming
 * @date 2020/7/26 8:40.
 */
public class LinkedHashMap<K, V> extends HashMap<K, V> {

    private LinkedNode<K, V> first;
    private LinkedNode<K, V> last;

    private static class LinkedNode<K, V> extends Entry<K, V> {
        LinkedNode<K, V> prev;
        LinkedNode<K, V> next;

        public LinkedNode(K key, V value, Entry<K, V> parent) {
            super(key, value, parent);
        }
    }

    public void traversal() {
        LinkedNode<K, V> node = first;
        while (node != null) {
            System.out.println(node.getKey() + "\t" + node.getValue());
            node = node.next;
        }
    }

    @Override
    public boolean containValue(V value) {
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (Objects.equals(value, node.getValue())) return true;
            node = node.next;
        }
        return false;
    }

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    protected void EntryAfterRemoval(Entry<K, V> willNode, Entry<K, V> removedNode) {
        LinkedNode<K, V> node1 = (LinkedNode<K, V>) willNode;
        LinkedNode<K, V> node2 = (LinkedNode<K, V>) removedNode;

        if (node1 != node2) {
            // 交换linkedWillNode和linkedRemovedNode在链表中的位置
            // 交换prev
            LinkedNode<K, V> tmp = node1.prev;
            node1.prev = node2.prev;
            node2.prev = tmp;
            if (node1.prev == null) {
                first = node1;
            } else {
                node1.prev.next = node1;
            }
            if (node2.prev == null) {
                first = node2;
            } else {
                node2.prev.next = node2;
            }

            // 交换next
            tmp = node1.next;
            node1.next = node2.next;
            node2.next = tmp;
            if (node1.next == null) {
                last = node1;
            } else {
                node1.next.prev = node1;
            }
            if (node2.next == null) {
                last = node2;
            } else {
                node2.next.prev = node2;
            }
        }

        LinkedNode<K, V> prev = node2.prev;
        LinkedNode<K, V> next = node2.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }

    @Override
    protected Entry<K, V> createEntry(K key, V value, Entry<K, V> parent) {
        LinkedNode<K, V> node = new LinkedNode<>(key, value, parent);
        if (first == null) {
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        return node;
    }
}
