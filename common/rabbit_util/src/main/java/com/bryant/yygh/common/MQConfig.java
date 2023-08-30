package com.bryant.yygh.common;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: MQConfig
 * Author:   swust-liuchuan
 * Date:     2022/9/1 20:32
 * Description:
 * History:
 */

@Configuration
public class MQConfig {
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}