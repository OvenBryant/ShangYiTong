package com.bryant.yygh.order.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bryant.yygh.model.order.OrderInfo;
import com.bryant.yygh.vo.order.OrderQueryVo;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Repository
public interface OrderService extends IService<OrderInfo> {

    //生成挂号订单
    Long saveOrder(String scheduleId, Long patientId);

    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

//    //根据订单id查询订单详情
    OrderInfo getOrder(String orderId);
//
    //订单列表（条件查询带分页）

    //取消预约
    Boolean cancelOrder(Long orderId);
//
    /**
     * 就诊提醒
     */
    void patientTips();
//
//    /**
//     * 订单详情
//     *
//     * @param orderId
//     * @return
//     */
    Map<String, Object> show(Long orderId);
}
