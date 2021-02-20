package com.sym.structure.array;

/**
 * 稀疏数组, 用来对数组进行压缩处理, 它尽可能地保存有效元素, 规定：
 * <pre>
 *     1.稀疏数组固定只有3列, 分别代表：原数组总行数、原数组总列数、原数组有效元素个数;
 *     2.稀疏数组首行外的其它行, 表示原数组有效元素的下标以及对应值, 例如：
 *       a b c
 *       0 2 10
 *       表示原二维数组[0][2]=10, 即原数组第一行第三列的值为10
 * </pre>
 *
 * @author shenym
 * @date 2019/9/3
 */
public class SparseArray {

    /**
     * 将一个整形数组转换为对应的稀疏数组
     */
    public static int[][] to(int[][] array) {
        if (null == array || array.length == 0) {
            return null;
        }
        // 先取原数组的行数、列数和有效值数
        int srcRows = array.length;
        int srcColumns = array[0].length;
        int validValues = 0;
        for (int[] internalArray : array) {
            for (int i : internalArray) {
                if (0 != i) {
                    validValues++;
                }
            }
        }
        // 创建稀疏数组的结构，并且设置它的首行数据
        int[][] sparseArray = new int[validValues + 1][3];
        //首行首列表示原数组的行数
        sparseArray[0][0] = srcRows;
        //首行第二列表示原数组的列数
        sparseArray[0][1] = srcColumns;
        //首行第三列表示原数组的有效值数
        sparseArray[0][2] = validValues;

        // 将原数组的的有效值记录到稀疏数组上
        int m = 1;
        for (int i = 0; i < srcRows; i++) {
            int[] internalArray = array[i];
            for (int j = 0, len = internalArray.length; j < len; j++) {
                if (0 != internalArray[j]) {
                    //保存这个非零值在原数组的位置信息和它相应的值
                    sparseArray[m][0] = i;
                    sparseArray[m][1] = j;
                    sparseArray[m][2] = internalArray[j];
                    m++;
                }
            }
        }
        return sparseArray;
    }


    /**
     * 将稀疏数组还原成原整型数组
     */
    public static int[][] from(int[][] sparseArray) {
        if (null == sparseArray || sparseArray.length == 0) {
            return null;
        }
        // 根据稀疏数组首行，先构建原数组模型
        int rows = sparseArray[0][0];
        int columns = sparseArray[0][1];
        int[][] srcArray = new int[rows][columns];

        //遍历稀疏数组，将值还原回去到原数组
        for (int i = 1, len = sparseArray.length; i < len; i++) {
            srcArray[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        return srcArray;
    }
}
