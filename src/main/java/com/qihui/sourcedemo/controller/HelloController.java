package com.qihui.sourcedemo.controller;

import com.qihui.sourcedemo.limiter.ExtRateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenqihui
 * @date 2019/5/14
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    @ExtRateLimiter(permitPerSecond = 0.5, timeout = 100)
    public String hello() {
        return "hello world";
    }
}
