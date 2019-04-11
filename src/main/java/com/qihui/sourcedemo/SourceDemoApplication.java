package com.qihui.sourcedemo;

import com.qihui.sourcedemo.transaction.AopTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = "com.qihui.sourcedemo.transaction")
public class SourceDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SourceDemoApplication.class, args);
        AopTransaction bean = run.getBeanFactory().getBean(AopTransaction.class);
        System.out.println(bean);
    }

}
