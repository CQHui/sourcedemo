package com.qihui.sourcedemo.connectionpool;

import java.sql.Connection;

/**
 * 数据库连接池
 * @author chenqihui
 * @date 2019/4/21
 */
public interface IConnectionPool {
    Connection getConnection();

    void releaseConnection(Connection connection);
}
