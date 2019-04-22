package com.qihui.sourcedemo.mybatis;

import com.qihui.sourcedemo.model.SysUser;
import com.qihui.sourcedemo.mybatis.mapper.UserMapper;

/**
 * @author chenqihui
 * @date 2019/4/22
 */
public class Main {
    public static void main(String[] args) {
        UserMapper mapper = SqlSession.getMapper(UserMapper.class);
//        mapper.insertUser("qihui","123456", "13588307742");
        SysUser user = mapper.getSysUserByMobile("13588307742");
        System.out.println(user.getUsername());
    }
}
