package com.qihui.sourcedemo.controller;

import com.qihui.sourcedemo.mvc.annotation.ExtController;
import com.qihui.sourcedemo.mvc.annotation.ExtRequestMapping;

/**
 * @author chenqihui
 * @date 2019/4/19
 */
@ExtController
@ExtRequestMapping("/index")
public class IndexController {

    @ExtRequestMapping("/index")
    public String index() {
        System.out.println("手写controller");
        return "index";
    }

    @ExtRequestMapping("/test")
    public String test() {
        System.out.println("手写controller");
        return "test";
    }
}
