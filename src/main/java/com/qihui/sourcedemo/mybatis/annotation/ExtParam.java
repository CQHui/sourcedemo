package com.qihui.sourcedemo.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @author chenqihui
 * @date 2019/4/22
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface ExtParam {
    String value();
}
