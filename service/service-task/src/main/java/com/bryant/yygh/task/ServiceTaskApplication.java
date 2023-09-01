package com.bryant.yygh.task;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: ServiceTaskApplication
 * Author:   swust-liuchuan
 * Date:     2022/9/10 10:37
 * Description:
 * History:
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.bryant"})
public class ServiceTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTaskApplication.class, args);
    }
}