package com.bryant.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@MapperScan("com.bryant.hosp.mapper")
public class HospConfig {

}