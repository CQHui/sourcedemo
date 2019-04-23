package com.qihui.sourcedemo;

import com.qihui.sourcedemo.util.ArrayList;
import com.qihui.sourcedemo.util.HashMap;
import com.qihui.sourcedemo.util.LinkedList;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author chenqihui
 * @date 2019/4/22
 */
public class ListTest {
    @Test
    public void arrayTest() {
        List<String> array = new ArrayList<>();
        array.add("qihui");
        array.add("xiaogegge");
        array.add("pengfei");
        array.add(1, "zhouyi");

        System.out.println(array);
    }

    @Test
    public void linkedTest() {
        List<String> linkedList = new LinkedList<>();
        linkedList.add("qihui");
        linkedList.add("xiaogegge");
        linkedList.add("pengfei");
        linkedList.add(1, "zhouyi");

        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }
    }
    
    @Test
    public void map() {
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("1号", "1号");// 0
        hashMap.put("2号", "1号");// 1
        hashMap.put("3号", "1号");// 2
        hashMap.put("4号", "1号");// 3
        hashMap.put("6号", "1号");// 4
        hashMap.put("7号", "1号");
        hashMap.put("14号", "1号");

        hashMap.put("22号", "1号");
        hashMap.put("26号", "1号");
        hashMap.put("27号", "1号");
        hashMap.put("28号", "1号");
        hashMap.put("66号", "66");
        hashMap.put("30号", "1号");
        System.out.println("扩容前数据....");
        hashMap.print();
        System.out.println("扩容后数据....");
        hashMap.put("31号", "1号");
        hashMap.put("66号", "123466666");
        hashMap.print();
        System.out.println(hashMap.get("66号"));
    }
}
