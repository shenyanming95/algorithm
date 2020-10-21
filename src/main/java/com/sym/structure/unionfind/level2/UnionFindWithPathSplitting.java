package com.sym.structure.unionfind.level2;

import com.sym.structure.unionfind.level1.UnionFindBaseRank;

/**
 * 并查集二级优化-路径分裂(Path Splitting).
 * 虽然基于rank的优化, 可以有效地防止链表现象的发生, 但是随着union操作的进行, 并查集的树高度
 * 还是会变大. 所以可以在find操作时, 让该条路径上的每个节点都指向其祖父节点.
 *
 * @author shenyanming
 * Created on 2020/10/21 09:56
 */
public class UnionFindWithPathSplitting extends UnionFindBaseRank {

    public UnionFindWithPathSplitting(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        check(v);
        while (v != array[v]) {
            // 先保存父节点的数组下标
            int p = array[v];
            // 让节点指向它的祖父节点下标
            array[v] = array[p];
            // 父节点重复执行上面两步
            v = p;
        }
        return v;
    }
}
