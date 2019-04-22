package com.qihui.sourcedemo.mybatis.mapper;

import com.qihui.sourcedemo.model.SysUser;
import com.qihui.sourcedemo.mybatis.annotation.ExtInsert;
import com.qihui.sourcedemo.mybatis.annotation.ExtParam;
import com.qihui.sourcedemo.mybatis.annotation.ExtSelect;

/**
 * @author chenqihui
 * @date 2019/4/22
 */
public interface UserMapper {
    @ExtInsert("insert into sys_user (username, password, mobile) values (#{userName}, #{password}, #{mobile})")
    void insertUser(@ExtParam("userName")String userName,
                    @ExtParam("password")String password,
                    @ExtParam("mobile")String mobile);

    @ExtSelect("select * from sys_user where mobile = #{mobile}")
    SysUser getSysUserByMobile(@ExtParam("mobile")String mobile);
}
