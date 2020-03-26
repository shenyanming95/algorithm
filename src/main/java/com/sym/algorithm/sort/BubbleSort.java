package com.sym.algorithm.sort;
import static com.sym.util.ArraySortUtil.*;
/**
 * 冒泡排序
 * 
 * 第一个for循环,循环数组的长度，保证每两个数据之间都可以比较到
 * 第二个for循环,是真正比较两两之间的逻辑，这个循环每循环一遍，就是(数组长度-1)次
 *  
 * 所以呢，冒泡排序的算法复杂度才会是O(n2) 
 * @author user
 *
 */
public class BubbleSort {
	
	/**
	 * 排序
	 * @param arr
	 * @return
	 */
	public static int[] sort(int[] arr){
		int len = arr.length;
		for(int i = 0; i<len-1;i++){
			for( int j = 0;j<len-1;j++ ){
				int left = arr[j];
				int right = arr[j+1];
				if( left > right ){
					arr[j+1] = left;
					arr[j] = right;
				}
			}
		}
		return arr;
	}
	
	/**
	 * 本地测试
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr = {50,45,120,5,4551,12,454,502,16,3,232};
		
		System.out.println("排序前...");
		printArr(arr);
		
		BubbleSort.sort(arr);
		
		System.out.println("排序后...");
		printArr(arr);
	}
}
