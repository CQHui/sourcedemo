package com.qihui.sourcedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@ServletComponentScan
@SpringBootApplication
public class SourceDemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SourceDemoApplication.class, args);
    }

}
