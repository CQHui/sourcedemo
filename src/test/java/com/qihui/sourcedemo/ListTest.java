package com.qihui.sourcedemo;

import com.qihui.sourcedemo.util.ArrayList;
import com.qihui.sourcedemo.util.LinkedList;
import org.junit.Test;

import java.util.List;

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
}
