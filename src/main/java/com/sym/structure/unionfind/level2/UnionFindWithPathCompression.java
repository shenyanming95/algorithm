package com.sym.structure.unionfind.level2;

import com.sym.structure.unionfind.level1.UnionFindBaseRank;

/**
 * 并查集二级优化-路径压缩(Path Compression).
 * 虽然基于rank的优化, 可以有效地防止链表现象的发生, 但是随着union操作的进行, 并查集的树高度
 * 还是会变大. 所以可以在find操作时, 将该条路径上的元素直接指向该集合的根节点.
 *
 * @author shenyanming
 * Created on 2020/10/21 09:54
 */
public class UnionFindWithPathCompression extends UnionFindBaseRank {

    public UnionFindWithPathCompression(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        check(v);
        if (array[v] != v) {
            // 通过递归找到最顶层的父节点, 然后这条路径上的每个元素, 都将指向这个父节点.
            array[v] = find(array[v]);
        }
        return array[v];
    }
}
