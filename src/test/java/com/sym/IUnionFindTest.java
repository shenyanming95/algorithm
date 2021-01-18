package com.sym;

import com.sym.structure.unionfind.GenericUnionFind;
import com.sym.structure.unionfind.IUnionFind;
import com.sym.structure.unionfind.impl.QuickFind;
import com.sym.structure.unionfind.impl.QuickUnion;
import com.sym.structure.unionfind.impl.level1.UnionFindBaseRank;
import com.sym.structure.unionfind.impl.level1.UnionFindBaseSize;
import com.sym.structure.unionfind.impl.level2.UnionFindWithPathCompression;
import com.sym.structure.unionfind.impl.level2.UnionFindWithPathHalving;
import com.sym.structure.unionfind.impl.level2.UnionFindWithPathSplitting;
import com.sym.util.TimeUtil;
import lombok.AllArgsConstructor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    @Test
    public void test08() {
        Student s1 = new Student(1, "张三");
        Student s2 = new Student(2, "李四");
        Student s3 = new Student(3, "王五");
        Student s4 = new Student(4, "赵六");
        Student s5 = new Student(5, "燕七");
        List<Student> list = Arrays.asList(s1, s2, s3, s4, s5);
        GenericUnionFind<Student> uf = new GenericUnionFind<>(list);
        // 先判断 - false
        System.out.println(uf.isSame(s1, s2));
        // 合并张三和燕七
        uf.union(s1, s5);
        // 合并李四和赵六
        uf.union(s2, s4);
        // 判断 - false
        System.out.println(uf.isSame(s1, s2));
        // 合并赵六和燕七
        uf.union(s4, s5);
        // 判断 -true
        System.out.println(uf.isSame(s1, s2));
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

    @AllArgsConstructor
    private static class Student {
        private int id;
        private String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return id == student.id &&
                    Objects.equals(name, student.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }
}
