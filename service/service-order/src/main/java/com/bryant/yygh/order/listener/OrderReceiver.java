package com.bryant.yygh.order.listener;


import com.bryant.yygh.common.constant.MqConst;
import com.bryant.yygh.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.plugin2.message.Message;

import java.io.IOException;
import java.nio.channels.Channel;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: OrderReceiver
 * Author:   swust-liuchuan
 * Date:     2022/9/10 10:44
 * Description: mq监听
 * History:
 */

@Component
public class OrderReceiver {

    @Autowired
    private OrderService orderService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_TASK_8, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_TASK),
            key = {MqConst.ROUTING_TASK_8}
    ))
    public void patientTips(Message message, Channel channel) throws IOException {
        orderService.patientTips();
    }
}