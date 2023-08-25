package com.bryant.yygh.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.bryant.yygh.user.mapper")
public class UserConfig {
}
