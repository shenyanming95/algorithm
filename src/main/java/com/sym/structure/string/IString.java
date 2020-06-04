package com.sym.structure.string;

/**
 * 字符串接口
 *
 * @author shenyanming
 * @date 2020/6/4 20:56.
 */

public interface IString {

    /**
     * 清空字符串
     */
    void clear();

    /**
     * 是否为空
     *
     * @return true-为空
     */
    boolean isEmpty();

    /**
     * 串的长度
     *
     * @return 大小
     */
    int length();

    /**
     * 定位某个字符
     *
     * @param index 下标
     * @return 字符
     */
    char charAt(int index);

    /**
     * 截取字符串
     *
     * @param begin 起始位置
     * @param end   中止位置
     * @return 新的子串
     */
    IString substring(int begin, int end);

    /**
     * 新增字符串到原串中
     *
     * @param offset 偏移量
     * @param str    新串
     * @return 新串
     */
    IString insert(int offset, IString str);

    /**
     * 删除指定区间的串
     *
     * @param begin 起始位置
     * @param end   终止位置
     * @return 新串
     */
    IString delete(int begin, int end);

    /**
     * 拼接串
     *
     * @param str 字符串
     * @return 新串
     */
    IString concat(IString str);

    /**
     * 比较串的大小
     *
     * @param str 字符串
     * @return 大于0当前串大于参数串, 等于0即相等, 小于0表示当前串小于参数串
     */
    int compareTo(IString str);

    /**
     * 查询子串在当前串中的位置
     *
     * @param str 子串
     * @return -1表示不存在, 不然返回对应的下标位置
     */
    int indexOf(IString str);
}
