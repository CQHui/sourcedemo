package com.qihui.sourcedemo.transaction;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author chenqihui
 * @date 2019/4/11
 */
@Component
@Aspect
public class AopTransaction {

    @Autowired
    private TransactionUtil transactionUtil;

    @Pointcut("@annotation(com.qihui.sourcedemo.transaction.CustomTransaction)")
    public void transaction(){}

    @Around("transaction()")
    public Object round(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("开启事务");
        TransactionStatus begin = transactionUtil.begin();
        joinPoint.proceed();
        transactionUtil.commit(begin);
        System.out.println("提交事务");
        return null;
    }
    @AfterThrowing("transaction()")
    public void afterThrowing() {
        System.out.println("程序已经回滚");
        // 获取程序当前事务 进行回滚
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }




}
