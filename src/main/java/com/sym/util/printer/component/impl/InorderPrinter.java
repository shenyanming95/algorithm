package com.sym.util.printer.component.impl;

import com.sym.util.printer.BinaryTreeInfo;
import com.sym.util.printer.component.AbstractPrinter;

/**

             ┌──800
         ┌──760
         │   └──600
     ┌──540
     │   └──476
     │       └──445
 ┌──410
 │   └──394
381
 │     ┌──190
 │     │   └──146
 │  ┌──40
 │  │  └──35
 └──12
    └──9
 
 * @author MJ Lee
 *
 */
public class InorderPrinter extends AbstractPrinter {
	private static String rightAppend;
	private static String leftAppend;
	private static String blankAppend;
	private static String lineAppend;
	static {
		int length = 2;
		rightAppend = "┌" + repeat("─", length);
		leftAppend = "└" + repeat("─", length);
		blankAppend = blank(length + 1);
		lineAppend = "│" + blank(length);
	}

	public InorderPrinter(BinaryTreeInfo tree) {
		super(tree);
	}

	@Override
	public String printString() {
		StringBuilder string = new StringBuilder(
				printString(tree.printRoot(), "", "", ""));
		string.deleteCharAt(string.length() - 1);
		return string.toString();
	}
	
	/**
	 * 生成node节点的字符串
	 * @param nodePrefix node那一行的前缀字符串
	 * @param leftPrefix node整棵左子树的前缀字符串
	 * @param rightPrefix node整棵右子树的前缀字符串
	 * @return
	 */
	private String printString(
			Object node, 
			String nodePrefix,
			String leftPrefix, 
			String rightPrefix) {
		Object left = tree.printLeft(node);
		Object right = tree.printRight(node);
		String string = tree.printNodeString(node).toString();
		
		int length = string.length();
		if (length % 2 == 0) {
			length--;
		}
		length >>= 1;
		
		String nodeString = "";
		if (right != null) {
			rightPrefix += blank(length);
			nodeString += printString(right, 
					rightPrefix + rightAppend, 
					rightPrefix + lineAppend, 
					rightPrefix + blankAppend);
		}
		nodeString += nodePrefix + string + "\n";
		if (left != null) {
			leftPrefix += blank(length);
			nodeString += printString(left, 
					leftPrefix + leftAppend, 
					leftPrefix + blankAppend, 
					leftPrefix + lineAppend);
		}
		return nodeString;
	}
}
