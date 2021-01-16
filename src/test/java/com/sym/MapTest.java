package com.sym;

import com.sym.structure.map.IMap;
import com.sym.structure.map.tree.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Map测试类
 *
 * @author shenyanming
 * Created on 2020/7/29 18:05
 */
@Slf4j
public class MapTest {

    /**
     * 创建一个TreeMap
     */
    @Test
    public void createTreeMap(){
        IMap<String, Integer> map = new TreeMap<>();
        // 默认按照key升序
        map.put("ddd", 1);
        map.put("ccc", 2);
        map.put("aaa", 3);
        map.put("rrr", 4);
        // 查看打印情况
        log.info("TreeMap遍历：{}", map);
        // 其它api
        log.info("isEmpty: {}", map.isEmpty());
        log.info("size: {}", map.size());
        log.info("containKey 'aaa': {}", map.containKey("aaa"));
        log.info("containKey 'bbb': {}", map.containKey("bbb"));
        log.info("containValue '1': {}", map.containValue(1));
        log.info("containValue '666': {}", map.containValue(666));
        // get
        log.info("get 'ccc': {}", map.get("ccc"));
        log.info("get '1': {}", map.get("1"));
        // remove
        map.remove("aaa");
        map.remove("bbb");
        map.remove("ccc");
        map.remove("rrr");
        log.info("remove: {}", map.size());
        log.info("TreeMap遍历: {}", map);
    }
}
