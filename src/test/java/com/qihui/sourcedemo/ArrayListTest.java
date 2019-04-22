package com.qihui.sourcedemo;

import com.qihui.sourcedemo.util.ExtArrayList;
import org.junit.Test;

import java.util.List;

/**
 * @author chenqihui
 * @date 2019/4/22
 */
public class ArrayListTest {
    @Test
    public void test() {
        List<String> array = new ExtArrayList<>();
        array.add("qihui");
        array.add("xiaogegge");
        array.add("pengfei");
        array.add(1, "zhouyi");

        System.out.println(array);
    }
}
