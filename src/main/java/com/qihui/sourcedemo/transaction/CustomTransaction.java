package com.qihui.sourcedemo.transaction;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author chenqihui
 * @date 2019/4/11
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CustomTransaction {
}
