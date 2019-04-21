package com.qihui.sourcedemo.controller;

import com.qihui.sourcedemo.mvc.annotation.ExtController;
import com.qihui.sourcedemo.mvc.annotation.ExtRequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenqihui
 * @date 2019/4/19
 */
@ExtController
@ExtRequestMapping("/index")
public class IndexController {

    @ExtRequestMapping("/greeting")
    public String greeting() {
        System.out.println("手写controller");
        return "greeting";
    }

    @ExtRequestMapping("/test")
    public String test() {
        System.out.println("手写controller");
        return "test";
    }
}
