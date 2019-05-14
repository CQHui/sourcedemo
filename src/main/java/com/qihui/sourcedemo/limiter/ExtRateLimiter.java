package com.qihui.sourcedemo.limiter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义服务限流注解
 * @author chenqihui
 * @date 2019/5/14
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtRateLimiter {
    //每秒固定速率放入令牌桶
    double permitPerSecond();
    // 规定毫秒数中没有拿到令牌走降级
    long timeout();
}
