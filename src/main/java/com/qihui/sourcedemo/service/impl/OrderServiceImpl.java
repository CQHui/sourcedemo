package com.qihui.sourcedemo.service.impl;

import com.qihui.sourcedemo.inversion.ExtService;
import com.qihui.sourcedemo.service.OrderService;

/**
 * @author chenqihui
 * @date 2019/4/17
 */
@ExtService
public class OrderServiceImpl implements OrderService {
    @Override
    public void order() {
        System.out.println("order方法");
    }
}
