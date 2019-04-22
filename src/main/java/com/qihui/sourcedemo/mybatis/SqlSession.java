package com.qihui.sourcedemo.mybatis;

import java.lang.reflect.Proxy;

/**
 * @author chenqihui
 * @date 2019/4/22
 */
public class SqlSession {
    // 获取getMapper
    public static <T> T getMapper(Class<T> clas)
            throws IllegalArgumentException{
        return (T) Proxy.newProxyInstance(clas.getClassLoader(), new Class[] { clas },
                new MapperInvocationHandler(clas));
    }
}
