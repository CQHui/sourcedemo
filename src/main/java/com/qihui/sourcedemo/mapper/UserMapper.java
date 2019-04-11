package com.qihui.sourcedemo.mapper;

import com.qihui.sourcedemo.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenqihui
 * @date 2019/4/11
 */
@Mapper
public interface UserMapper extends MyMapper<SysUser> {
}
