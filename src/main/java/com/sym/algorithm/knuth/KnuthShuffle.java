package com.sym.algorithm.knuth;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Knuth-Shuffle洗牌算法, 是一种公平的随机算法. 给定一个数组, 这个算法可以让这个数组的
 * 元素等概率地出现在每一个位置上, 即公平地排列. 它的发明者是一个大神-Donald Knuth, 高德納.
 * 它的算法思想是这样子的：
 * 假设一个数组为：[1, 2, 3, 4, 5], 它有5个元素, 从元素最后一个位置向前遍历, 定义遍历下标i,
 * 第一次, i=5, 在[0~i]也就是[0-5]之间取出一个随机值j, 让arr[i]和arr[j]互换位置;
 * 第二次, i=4, 在[0~i]也就是[0-4]之间取出一个随机值j, 让arr[i]和arr[j]互换位置;
 * ...
 * 以此类推, 直至i=0不需要交换位置.
 * <p>
 *     算法实现很简单, 主要是要理解它的原理, 为啥这样处理以后, 每个元素落在数组位置上的几率就是相等的.
 *     首先, 第一次遍历从[0~i]随机选取一个元素, 所以这个元素被选取到的概率就是1/5;
 *     其次, 第二次遍历从[0~(i-1)]随机选取一个元素, 这个元素由于第一次遍历未被选取, 被落选的概率为4/5, 然后这个元素在本次遍历被选取, 此时元素个数已变为4个, 所以被选取的概率就为1/4, 总的概率就为 4/5 * 1/4 = 1/5,这个概率就是第一次遍历的概率;
 *     接着, 第三次遍历从[0~(i-2)]随机选取一个元素, 这个元素由于第一次、第二次都未被选择, 落选的概率就为 4/5 * 3/4, 此时元素个数变为3个, 被选中的概率就为1/3, 所以它在这次遍历被选取的概率就为 4/5 * 3/4 * 1/3 = 1/5;
 *     然后, 第四次遍历元素被选择的概率就是 4/5 * 3/4 * 2/3 * 1/2 = 1/5;(第一次落选的概率 * 第二次遍历落选的概率 * 第三次落选的概率 * 第四次被选中的概率)
 *     最后, 第五次遍历元素被选择的概率就是 4/5 * 3/4 * 2/3 * 1/2 * 1 = 1/5;(第一次落选的概率 * 第二次遍历落选的概率 * 第三次落选的概率 * 第四次落选的概率 * 第五次选中的概率)
 * </p>
 *
 * @author shenyanming
 * Created on 2020/7/2 15:38
 */
public class KnuthShuffle {
    private static SecureRandom random = new SecureRandom();

    public static void main(String[] args) {
        List<Integer> list;
        for(int i = 0; i < 100; i++){
            // 每次遍历都, 重置排列
            list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
            knuthShuffle(list);
            System.out.println(list);
        }
    }

    /**
     * knuth洗牌算法
     * @param list 待随机的集合
     * @param <T> 集合中的元素
     */
    public static <T> void knuthShuffle(List<T> list){
        if(Objects.isNull(list) || list.isEmpty()){
            return;
        }
        for(int i = list.size() - 1; i >= 0; i--){
            if(i == 0){
                break;
            }
            // 每次遍历都从[0~i]中选择一个随机数, nextInt()可以指定一个上限, 不会包括它
            int j = random.nextInt(i);
            // 将 i 和 j 互换位置
            T t = list.get(i);
            list.set(i, list.get(j));
            list.set(j, t);
        }
    }
}
