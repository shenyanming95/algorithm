package com.sym.util.printer;

public interface BinaryTreeInfo {
	/**
	 * who is the root node
	 */
	Object printRoot();
	/**
	 * how to get the left child of the node
	 */
	Object printLeft(Object node);
	/**
	 * how to get the right child of the node
	 */
	Object printRight(Object node);
	/**
	 * how to print the node
	 */
	Object printNodeString(Object node);
}
