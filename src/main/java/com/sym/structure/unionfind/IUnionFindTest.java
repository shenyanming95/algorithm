package com.sym.structure.unionfind;

import com.sym.structure.unionfind.impl.QuickFind;
import com.sym.structure.unionfind.impl.QuickUnion;
import com.sym.structure.unionfind.level1.UnionFindBaseRank;
import com.sym.structure.unionfind.level1.UnionFindBaseSize;
import com.sym.structure.unionfind.level2.UnionFindWithPathCompression;
import com.sym.structure.unionfind.level2.UnionFindWithPathHalving;
import com.sym.structure.unionfind.level2.UnionFindWithPathSplitting;
import com.sym.util.TimeUtil;
import org.junit.Test;

/**
 * 并查集测试类
 *
 * @author shenyanming
 * Created on 2020/10/20 15:50
 */
public class IUnionFindTest {

    final int count = 10000;

    @Test
    public void test01() {
        testTime(new QuickFind(count));
    }

    @Test
    public void test02() {
        testTime(new QuickUnion(count));
    }

    @Test
    public void test03() {
        testTime(new UnionFindBaseSize(count));
    }

    @Test
    public void test04() {
        testTime(new UnionFindBaseRank(count));
    }

    @Test
    public void test05() {
        testTime(new UnionFindWithPathCompression(count));
    }

    @Test
    public void test06() {
        testTime(new UnionFindWithPathHalving(count));
    }

    @Test
    public void test07() {
        testTime(new UnionFindWithPathSplitting(count));
    }

    private void testTime(IUnionFind uf) {
        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);
        uf.union(6, 7);
        uf.union(8, 10);
        uf.union(9, 10);
        uf.union(9, 11);
        System.out.println(uf.isSame(2, 7));
        uf.union(4, 6);
        System.out.println(uf.isSame(2, 7));

        TimeUtil.execute(uf.getClass().getSimpleName(), () -> {
            for (int i = 0; i < count; i++) {
                uf.union((int) (Math.random() * count),
                        (int) (Math.random() * count));
            }

            for (int i = 0; i < count; i++) {
                uf.isSame((int) (Math.random() * count),
                        (int) (Math.random() * count));
            }
        });
    }
}
