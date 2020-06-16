package com.sym.structure.tree.traversal;

/**
 * 通过【访问者模式】设计树{@link com.sym.structure.tree.ITree}的遍历方式
 *
 * @author shenyanming
 * @date 2020/6/16 21:34.
 */
@FunctionalInterface
public interface Visitor<E> {
    /**
     * 访问一个元素
     *
     * @param element 具体元素
     */
    void visit(E element);
}
