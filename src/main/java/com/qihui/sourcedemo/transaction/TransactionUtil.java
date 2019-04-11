package com.qihui.sourcedemo.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * @author chenqihui
 * @date 2019/4/11
 */
@Component
public class TransactionUtil {

    @Autowired
    private DataSourceTransactionManager transactionManager;

    public TransactionStatus begin() {
        return transactionManager.getTransaction(new DefaultTransactionAttribute());
    }

    public void commit(TransactionStatus transactionStatus) {
        transactionManager.commit(transactionStatus);
    }

    public void rollback(TransactionStatus transactionStatus) {
        transactionManager.rollback(transactionStatus);
    }
}
