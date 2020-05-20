package com.sym.datastructure.array;

/**
 * 稀疏数组, 用来对数组进行压缩处理, 规定：
 * 1)、稀疏数组固定只有3列,  如：
 * a   b   e
 * *   *   *
 * ...
 * ...
 * 首行三列的含义分别是：a-原数组总行数; b=原数组总列数; c-原数组有效元素的个数
 * <p>
 * 2)、稀疏数组首行外的其它行, 表示原数组有效元素的下标以及值, 规定：
 * a  b  c
 * 0  2  110
 * ...
 * 原二维数组[0][2]的值为110, 即原二维数组第一行第三列的值为110
 *
 * @author shenym
 * @date 2019/9/3
 */
public class SparseArray {

    /**
     * 将一个整形数组转换为对应的稀疏数组
     */
    public static int[][] toSparseArray(int[][] srcArray) {
        if (null == srcArray || srcArray.length == 0) {
            return null;
        }
        // 先取原数组的行数、列数和有效值数 内部
        int srcRows = srcArray.length;
        int srcColumns = srcArray[0].length;
        int validValues = 0;
        for (int[] internalArray : srcArray) {
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
            int[] internalArray = srcArray[i];
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
    public static int[][] fromSparseArray(int[][] sparseArray) {
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
