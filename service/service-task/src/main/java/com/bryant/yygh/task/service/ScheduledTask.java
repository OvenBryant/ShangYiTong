package com.bryant.yygh.task.service;


import com.bryant.yygh.common.constant.MqConst;
import com.bryant.yygh.common.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: ScheduledTask
 * Author:   swust-liuchuan
 * Date:     2022/9/10 10:40
 * Description: 定时任务类
 * History:
 */

@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private RabbitService rabbitService;

    /**
     * 每天8点执行 提醒就诊  service-order 监听
     */
    //@Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(cron = "0/30 * * * * ?") // 每隔30秒 后面一位不支持，当前年 https://cron.qqe2.com/
    public void task1() {
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_8, "");
    }
}