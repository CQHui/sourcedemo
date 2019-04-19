package com.qihui.sourcedemo;

import com.qihui.sourcedemo.mvc.ExtDispatcherServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@ServletComponentScan
@SpringBootApplication
public class SourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SourceDemoApplication.class, args);
    }

}
