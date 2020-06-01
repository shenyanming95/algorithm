package com.sym.util;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author shenyanming
 * Created on 2020/5/30 14:10
 */
@ToString
@EqualsAndHashCode
public class Pair<L,R> {
    private L left;
    private R right;

    private Pair(L left, R right){
        this.left = left;
        this.right = right;
    }

    public static <L,R> Pair<L,R> of(L left, R right){
        return new Pair<>(left, right);
    }

    public L getFirst(){
        return this.left;
    }

    public R getSecond(){
        return this.right;
    }
}
