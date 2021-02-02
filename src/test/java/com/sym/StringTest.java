package com.sym;

import com.sym.structure.string.IString;
import com.sym.structure.string.impl.String;
import org.junit.Test;

/**
 * 字符串测试类
 *
 * @author shenyanming
 * Created on 2021/2/1 10:41
 */
public class StringTest {

    @Test
    public void commonTest() {
        IString s1 = new String("abc");
        System.out.println(s1.length());
        System.out.println(s1.toArray().length);
        System.out.println(s1.charAt(1));
        System.out.println(s1.isEmpty());
        s1.clear();
        System.out.println(s1);
        System.out.println(s1.isEmpty());
    }

    @Test
    public void substringTest() {
        IString s = new String("君不见黄河之水天上来");
        IString string = s.substring(3, s.length());
        System.out.println(string);
    }

    @Test
    public void insertTest() {
        IString s1 = new String("君不见黄河之水天上来, ");
        IString s2 = new String("奔流到海不复回");
        IString string = s1.insert(s1.length() - 1, s2);
        System.out.println(string);
    }

    @Test
    public void concatTest() {
        IString s1 = new String("abc");
        IString s2 = new String("gef");
        System.out.println(s1.concat(s2));
        System.out.println(s1.compareTo(s2));
    }

    @Test
    public void indexOfTest(){
        IString s1 = new String("abc");
        IString s2 = new String("gef");
        System.out.println(s1.indexOf(s2));

        s1 = new String("GOOD-JOB");
        s2 = new String("JO");
        System.out.println(s1.indexOf(s2));
    }
}
