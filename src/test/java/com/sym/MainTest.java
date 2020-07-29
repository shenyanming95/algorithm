package com.sym;

import com.sym.util.HashcodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 测试类
 *
 * @author shenyanming
 * Created on 2020/7/6 16:27
 */
@Slf4j
public class MainTest {

    /**
     * long 强转为 int, 会丢失高32位的精度
     */
    @Test
    public void test01(){
        long l = 0x7fffffff << 1;
        System.out.println(Long.toBinaryString(l));

        int i = (int) l;
        System.out.println(Integer.toBinaryString(i));
    }

    /**
     * hashcode测试
     */
    @Test
    public void test02(){
        // 整数
        Integer i = 100;
        System.out.println(i.hashCode());
        System.out.println(HashcodeUtil.hashcode(i));

        // 长整数
        Long l = Long.valueOf("10102521101254");
        System.out.println(l.hashCode());
        System.out.println(HashcodeUtil.hashcode(l));

        // 浮点数
        Float f = 1.112f;
        System.out.println(f.hashCode());
        System.out.println(HashcodeUtil.hashcode(f));

        // 浮点数
        Double d = 6.235414;
        System.out.println(d.hashCode());
        System.out.println(HashcodeUtil.hashcode(d));

        // 字符串
        String s = "abc";
        System.out.println(s.hashCode());
        System.out.println(HashcodeUtil.hashcode(s));
    }

    /**
     * {@link HashMap}是乱序, {@link TreeMap}是有序(默认按照key升序)
     */
    @Test
    public void test03(){
        Map<String, String> map = new HashMap<>(4);
        map.put("a213", "111");
        map.put("213c", "111");
        map.put("e321", "111");
        map.put("3fb", "111");
        map.put("dasdd", "111");
        System.out.println(map);


        map = new TreeMap<>();
        map.put("a213", "111");
        map.put("213c", "111");
        map.put("e321", "111");
        map.put("3fb", "111");
        map.put("dasdd", "111");
        System.out.println(map);
    }

    @Test
    public void test04(){

    }
}
