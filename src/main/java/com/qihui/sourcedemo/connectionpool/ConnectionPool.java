package com.qihui.sourcedemo.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenqihui
 * @date 2019/4/21
 */
public class ConnectionPool implements IConnectionPool {
    //空闲线程 没有被使用连接存放容器
    private List<Connection> freeConnections = new Vector<>();
    //活动线程 正在使用的连接存放容器
    private List<Connection> activeConnections = new Vector<>();

    private DbBean dbBean;

    //原子类确保count线程安全问题
    private AtomicInteger countConne = new AtomicInteger(0);


    public ConnectionPool(DbBean dbBean) {
        // 获取配置文件信息
        this.dbBean = dbBean;
        init();
    }

    // 初始化线程池(初始化空闲线程)
    private void init() {
        if (dbBean == null) {
            return;// 注意最好抛出异常
        }
        // 1.获取初始化连接数
        for (int i = 0; i < dbBean.getInitConnections(); i++) {
            // 2.创建Connection连接
            Connection newConnection = newConnection();
            if (newConnection != null) {
                // 3.存放在freeConnection集合
                freeConnections.add(newConnection);
            }
        }

    }

    // 创建Connection连接
    private Connection newConnection() {
        try {
            Class.forName(dbBean.getDriverName());
            Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUserName(),
                    dbBean.getPassword());
            countConne.incrementAndGet();
            return connection;
        } catch (Exception e) {
            return null;
        }
    }



    // 调用getConnection方法 --- 获取连接
    @Override
    public synchronized Connection getConnection() {

        try {
            Connection connection;
            // 思考：怎么知道当前创建的连接>最大连接数
            if (countConne.get() < dbBean.getMaxActiveConnections()) {
                // 小于最大活动连接数
                // 1.判断空闲线程是否有数据
                if (freeConnections.size() > 0) {
                    // 空闲线程有存在连接
                    connection = freeConnections.remove(0);
                } else {
                    // 创建新的连接
                    connection = newConnection();
                }
                // 判断连接是否可用
                boolean available = isAvailable(connection);
                if (available) {
                    // 存放在活动线程池
                    activeConnections.add(connection);
                    countConne.incrementAndGet();
                } else {
                    countConne.decrementAndGet();
                    connection = getConnection();
                }

            } else {
                // 大于最大活动连接数，进行等待
                wait(dbBean.getConnTimeOut());
                // 重试
                connection = getConnection();
            }
            return connection;
        } catch (Exception e) {
            return null;
        }
    }

    // 释放连接 回收
    @Override
    public synchronized void releaseConnection(Connection connection) {
        try {
            // 1.判断连接是否可用
            if (isAvailable(connection)) {
                // 2.判断空闲线程是否已满
                if (freeConnections.size() < dbBean.getMaxConnections()) {
                    // 空闲线程没有满
                    freeConnections.add(connection);// 回收连接
                } else {
                    // 空闲线程已经满
                    connection.close();
                }
                activeConnections.remove(connection);
                countConne.decrementAndGet();
                notifyAll();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    // 判断连接是否可用
    public boolean isAvailable(Connection connection) {
        try {
            if (connection == null || connection.isClosed()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }


}
