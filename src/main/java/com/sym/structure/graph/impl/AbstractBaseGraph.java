package com.sym.structure.graph.impl;

import com.sym.structure.graph.IGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 图基本实现的抽象父类, 满足图的基本操作
 *
 * @author shenyanming
 * Created on 2021/2/5 10:31
 */
public abstract class AbstractBaseGraph<V, E> implements IGraph<V, E> {

    /**
     * 初始化set集合
     *
     * @param <T> 任意泛型
     * @return HashSet
     */
    protected static <T> Set<T> newSet() {
        return new HashSet<>();
    }

    /**
     * 初始化map映射
     *
     * @param <K> 任意泛型
     * @param <V> 任意泛型
     * @return HashMap
     */
    protected static <K, V> Map<K, V> newMap() {
        return new HashMap<>();
    }

}
