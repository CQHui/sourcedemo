package com.qihui.sourcedemo.connectionpool;

import java.sql.Connection;

/**
 * @author chenqihui
 */
public class ConnectionPoolManager {
	private static DbBean dbBean = new DbBean();
	private static ConnectionPool connectionPool = new ConnectionPool(dbBean);

	// 获取连接(重复利用机制)
	public static Connection getConnection() {
		return connectionPool.getConnection();
	}

	// 释放连接(可回收机制)
	public static void releaseConnection(Connection connection) {
		connectionPool.releaseConnection(connection);
	}
}
