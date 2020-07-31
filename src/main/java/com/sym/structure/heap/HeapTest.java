package com.sym.structure.heap;

import com.sym.structure.heap.impl.BinaryHeap;
import com.sym.util.printer.BinaryTrees;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 二叉堆测试类
 *
 * @author shenyanming
 * Created on 2020/7/31 16:05
 */
@Slf4j
public class HeapTest {

    /**
     * 创建二叉堆
     */
    @Test
    public void createBinaryHeap(){
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        List<Integer> list = getIntArray(18);
        log.info("构造整数：{}", list);
        list.forEach(heap::add);
        // 打印二叉堆的内容
        BinaryTrees.println(heap);
    }

    @Test
    public void useBinaryHeap(){
        List<Integer> list = getIntArray(50);
        log.info("构造整数：{}", list);
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        list.forEach(heap::add);

        log.info("容量：{}", heap.size());
        log.info("最大值：{}", heap.get());
        log.info("删除堆顶：{}", heap.remove());
        BinaryTrees.println(heap);

        log.info("替换堆顶：{}", heap.replace(500));
        BinaryTrees.println(heap);
    }

    @Test
    public void removeBinaryHeap(){
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(10);
        heap.add(20);
        heap.add(30);
        BinaryTrees.println(heap);

        log.info("删除堆顶：{}", heap.remove());
        BinaryTrees.println(heap);

        log.info("删除堆顶：{}", heap.remove());
        BinaryTrees.println(heap);

        log.info("删除堆顶：{}", heap.remove());
        BinaryTrees.println(heap);

        log.info("删除堆顶：{}", heap.remove());
        BinaryTrees.println(heap);
    }

    private List<Integer> getIntArray(int size){
        List<Integer> retList = new ArrayList<>(size);
        Random random = new Random();
        for(int i = 0; i < size; i++){
            retList.add(random.nextInt(1000));
        }
        return retList;
    }
}
