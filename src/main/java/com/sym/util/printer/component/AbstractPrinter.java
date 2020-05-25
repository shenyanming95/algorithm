package com.sym.util.printer.component;

import com.sym.util.printer.BinaryTreeInfo;

/**
 * @author MJ Lee
 */
public abstract class AbstractPrinter {
    /**
     * 二叉树的基本信息
     */
    protected BinaryTreeInfo tree;

    public AbstractPrinter(BinaryTreeInfo tree) {
        this.tree = tree;
    }

    /**
     * 生成打印的字符串
     */
    public abstract String printString();

    /**
     * 打印后换行
     */
    public void println() {
        print();
        System.out.println();
    }

    /**
     * 打印
     */
    public void print() {
        System.out.print(printString());
    }

    protected static String repeat(String string, int count) {
        if (string == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        while (count-- > 0) {
            builder.append(string);
        }
        return builder.toString();
    }

    protected static String blank(int length) {
        if (length < 0) {
            return null;
        }
        if (length == 0) {
            return "";
        }
        return String.format("%" + length + "s", "");
    }
}
