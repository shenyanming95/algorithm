package com.sym;

import com.sym.structure.array.SparseArray;
import com.sym.util.ArrayUtil;
import org.junit.Test;

/**
 * 数组测试
 *
 * @author shenYm
 * @date 2019/9/3
 */
public class ArrayTest {

    /**
     * 打印一维数组
     */
    @Test
    public void testOne() {
        // 打印一维数组
        int[] array = new int[]{12, 3, 43543, 534, 67};
        ArrayUtil.print(array);
    }


    /**
     * 测试稀疏数组
     */
    @Test
    public void testTwo() {
        // 创建二维数组
        int[][] array = new int[10][6];
        array[1][2] = 110;
        array[5][0] = 119;
        array[9][3] = 120;
        array[0][3] = 777;
        array[7][5] = 666;

        //打印原数组
        System.out.println("原数组：");
        ArrayUtil.print(array);
        System.out.println();

        // 转换为稀疏数组后
        System.out.println();
        System.out.println("转换为稀疏数组：");
        int[][] sparseArray = SparseArray.to(array);
        ArrayUtil.print(sparseArray);

        // 稀疏数组还原回原数组
        System.out.println();
        System.out.println("还原为原数组：");
        int[][] srcArray = SparseArray.from(sparseArray);
        ArrayUtil.print(srcArray);
    }

}
