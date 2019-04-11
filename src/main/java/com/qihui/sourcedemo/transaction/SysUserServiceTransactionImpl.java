package com.qihui.sourcedemo.transaction;

import com.qihui.sourcedemo.mapper.UserMapper;
import com.qihui.sourcedemo.model.SysUser;
import com.qihui.sourcedemo.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenqihui
 * @date 2019/4/11
 */
@Service
public class SysUserServiceTransactionImpl implements SysUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @CustomTransaction
    public void saveUser() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("小明");
        sysUser.setMobile("13566778899");
        sysUser.setPassword("123456");
        userMapper.insertSelective(sysUser);
        int i = 1 / 0;
        sysUser.setUsername("小红");
        sysUser.setMobile("13612341234");
        sysUser.setPassword("abc");
        userMapper.insertSelective(sysUser);
    }
}
