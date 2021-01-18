package com.sym.structure.unionfind.impl.level2;

import com.sym.structure.unionfind.impl.level1.UnionFindBaseRank;

/**
 * 并查集二级优化-路径减半(Path Halving).
 * 虽然基于rank的优化, 可以有效地防止链表现象的发生, 但是随着union操作的进行, 并查集的树高度
 * 还是会变大. 所以可以在find操作时, 在该条路径上, 每隔一个节点就指向它的祖父节点.
 *
 * @author shenyanming
 * Created on 2020/10/21 09:57
 */
public class UnionFindWithPathHalving extends UnionFindBaseRank {

    public UnionFindWithPathHalving(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        check(v);
        while (v != array[v]) {
            // 指向祖父节点
            array[v] = array[array[v]];
            // 每隔一个节点, 其实就是每次跳过父节点
            v = array[v];
        }
        return v;
    }
}
