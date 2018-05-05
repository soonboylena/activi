package com.github.soonboylena.myflow.flowdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "com.github.soonboylena")
@EnableTransactionManagement
public class FlowDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowDemoApplication.class, args);
    }
}
