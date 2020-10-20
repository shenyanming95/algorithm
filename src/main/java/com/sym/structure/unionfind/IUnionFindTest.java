package com.sym.structure.unionfind;

import com.sym.structure.unionfind.ordinary.QuickFind;
import com.sym.structure.unionfind.ordinary.QuickUnion;
import org.junit.Test;

/**
 * 并查集测试类
 *
 * @author shenyanming
 * Created on 2020/10/20 15:50
 */
public class IUnionFindTest {

    /**
     * 10个集合, 关联情况:
     * 1 ← 5
     * 5 ← 2
     * 2 ← 6,7
     * 9 ← 3,4,8
     * 那么, 1和7是有关联的吗? 1和3呢?
     */
    @Test
    public void test01() {
        IUnionFind uf1 = new QuickFind(10);
        doUnion(uf1);
        System.out.println(uf1.isSame(1, 7));
        System.out.println(uf1.isSame(1, 3));

        IUnionFind uf2 = new QuickUnion(10);
        doUnion(uf2);
        System.out.println(uf2.isSame(1, 7));
        System.out.println(uf2.isSame(1, 3));
    }

    private void doUnion(IUnionFind uf) {
        uf.union(1, 5);
        uf.union(5, 2);
        uf.union(2, 6);
        uf.union(2, 7);
        uf.union(9, 3);
        uf.union(9, 4);
        uf.union(9, 8);
    }
}
