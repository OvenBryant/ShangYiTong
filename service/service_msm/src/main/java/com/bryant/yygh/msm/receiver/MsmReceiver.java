package com.bryant.yygh.msm.receiver;


import com.bryant.yygh.msm.service.MsmService;
import com.bryant.yygh.vo.msm.MsmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MsmReceiver {


//    @Autowired
//    private MsmService smsService;
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
//            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
//            key = {MqConst.ROUTING_MSM_ITEM}
//    ))
//    public void send(MsmVo msmVo, Message message, Channel channel) {
//        smsService.send(msmVo);
//    }
}
