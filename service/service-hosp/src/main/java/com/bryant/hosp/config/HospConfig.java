package com.atguigu.yygh.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: HospConfig
 * Author:   swust-liuchuan
 * Date:     2022/6/28 20:42
 * Description: HospConfig 配置类，方便扫描mapper
 * History:
 */

@Configuration
@MapperScan("com.atguigu.yygh.hosp.mapper")
public class HospConfig {

}