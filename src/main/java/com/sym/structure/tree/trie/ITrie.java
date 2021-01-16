package com.sym.structure.tree.trie;

import com.sym.structure.tree.ITree;

/**
 * 字典树, 也称为前缀树, 它会将字符串按字符分开, 依次保存在内部的节点上,
 * 每次搜索字符串的时候, 可以根据字符确定字符串前缀是否依次匹配. 所以
 * Trie, 适用于需要使用字符串前缀搜索的场景.
 *
 * @author shenyanming
 * @date 2020/8/1 15:57.
 */

public interface ITrie<E> extends ITree<E> {

    /**
     * 判断字典树是否包含某个字符串
     *
     * @param key 待匹配的字符串
     * @return true-包含
     */
    boolean contains(String key);

    /**
     * 添加元素到字典树中
     *
     * @param key   字符串
     * @param value 具体指
     * @return 旧值, 可能为null
     */
    E add(String key, E value);

    /**
     * 删除在字典树上的字符串
     *
     * @param key 待匹配的字符串
     */
    void remove(String key);

    /**
     * 给定一个字符串前缀, 判断此前缀是否存在于字典树中
     *
     * @param prefix 字符串前缀
     * @return true-存在
     */
    boolean startWith(String prefix);
}
