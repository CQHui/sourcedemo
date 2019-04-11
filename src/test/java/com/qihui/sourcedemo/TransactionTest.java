package com.qihui.sourcedemo;

import com.qihui.sourcedemo.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author chenqihui
 * @date 2019/4/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {

    @Resource
    private SysUserService sysUserService;
    @Test
    public void transaction() {
        sysUserService.saveUser();
    }
}
