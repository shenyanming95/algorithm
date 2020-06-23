package com.sym.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author shenyanming
 * Created on 2020/6/23 14:45
 */
public class RandomUtil {
    private final static String PATTERN_STRINGS = "qazwsxedcrfvtgbyhnujmiklop1234567890";

    /**
     * 获取随机字符串
     * @param length 指定长度
     * @return string
     */
    public static String getRandomString(int length) {
        return RandomStringUtils.random(length, PATTERN_STRINGS);
    }


}
