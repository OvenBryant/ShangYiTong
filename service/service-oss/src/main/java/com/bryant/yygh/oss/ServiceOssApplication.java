package com.bryant.yygh.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: ServiceOssApplication
 * Author:   swust-liuchuan
 * Date:     2022/7/21 22:21
 * Description:
 * History:
 */


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置（无数据库）
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.bryant"})
public class ServiceOssApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplication.class, args);
    }
}