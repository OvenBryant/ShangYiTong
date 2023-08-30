package com.bryant.yygh.order.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bryant.yygh.common.exception.YyghException;
import com.bryant.yygh.common.helper.HttpRequestHelper;
import com.bryant.yygh.common.result.ResultCodeEnum;
import com.bryant.yygh.common.utils.MD5;
import com.bryant.yygh.enums.OrderStatusEnum;
import com.bryant.yygh.model.order.OrderInfo;
import com.bryant.yygh.model.user.Patient;
import com.bryant.yygh.order.mapper.OrderMapper;
import com.bryant.yygh.order.service.OrderService;
import com.bryant.yygh.order.service.WeixinService;
import com.bryant.yygh.order.utils.DateUtil;
import com.bryant.yygh.vo.hosp.ScheduleOrderVo;
import com.bryant.yygh.vo.msm.MsmVo;
import com.bryant.yygh.vo.order.OrderMqVo;
import com.bryant.yygh.vo.order.OrderQueryVo;
import com.bryant.yygh.vo.order.SignInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderServiceImpl extends
        ServiceImpl<OrderMapper, OrderInfo> implements OrderService {



    @Override
    public Long saveOrder(String scheduleId, Long patientId) {
        return null;
    }

//    @Autowired
//    private PatientFeignClient patientFeignClient;
//
//    @Autowired
//    private HospitalFeignClient hospitalFeignClient;
//
//    @Autowired
//    private RabbitService rabbitService;
//
//    @Autowired
//    private WeixinService weixinService;

    //生成挂号订单

//    //根据订单id查询订单详情
//    @Override
//    public OrderInfo getOrder(String orderId) {
//        OrderInfo orderInfo = baseMapper.selectById(orderId);
//        return this.packOrderInfo(orderInfo);
//    }
//
//    //订单列表（条件查询带分页）
//    @Override
//    public IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo) {
//        //orderQueryVo获取条件值
//        String name = orderQueryVo.getKeyword(); //医院名称
//        Long patientId = orderQueryVo.getPatientId(); //就诊人名称
//        String orderStatus = orderQueryVo.getOrderStatus(); //订单状态
//        String reserveDate = orderQueryVo.getReserveDate();//安排时间
//        String createTimeBegin = orderQueryVo.getCreateTimeBegin();
//        String createTimeEnd = orderQueryVo.getCreateTimeEnd();
//
//        //对条件值进行非空判断
//        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
//        if (!StringUtils.isEmpty(name)) {
//            wrapper.like("hosname", name);
//        }
//        if (!StringUtils.isEmpty(patientId)) {
//            wrapper.eq("patient_id", patientId);
//        }
//        if (!StringUtils.isEmpty(orderStatus)) {
//            wrapper.eq("order_status", orderStatus);
//        }
//        if (!StringUtils.isEmpty(reserveDate)) {
//            wrapper.ge("reserve_date", reserveDate);
//        }
//        if (!StringUtils.isEmpty(createTimeBegin)) {
//            wrapper.ge("create_time", createTimeBegin);
//        }
//        if (!StringUtils.isEmpty(createTimeEnd)) {
//            wrapper.le("create_time", createTimeEnd);
//        }
//        //调用mapper的方法
//        IPage<OrderInfo> pages = baseMapper.selectPage(pageParam, wrapper);
//        //编号变成对应值封装
//        pages.getRecords().stream().forEach(item -> {
//            this.packOrderInfo(item);
//        });
//        return pages;
//    }
//
//    @Override
//    public Boolean cancelOrder(Long orderId) {
//        OrderInfo orderInfo = this.getById(orderId);
//        //当前时间大约退号时间，不能取消预约
//        DateTime quitTime = new DateTime(orderInfo.getQuitTime());
//        if (quitTime.isBeforeNow()) {
//            throw new YyghException(ResultCodeEnum.CANCEL_ORDER_NO);
//        }
//        SignInfoVo signInfoVo = hospitalFeignClient.getSignInfoVo(orderInfo.getHoscode());
//        if (null == signInfoVo) {
//            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
//        }
//        Map<String, Object> reqMap = new HashMap<>();
//        reqMap.put("hoscode", orderInfo.getHoscode());
//        reqMap.put("hosRecordId", orderInfo.getHosRecordId());
//        reqMap.put("timestamp", HttpRequestHelper.getTimestamp());
////        String sign = HttpRequestHelper.getSign(reqMap, signInfoVo.getSignKey());
//        String sign = MD5.encrypt(signInfoVo.getSignKey());
//        reqMap.put("sign", sign);
//
////        JSONObject result = HttpRequestHelper.sendRequest(reqMap, signInfoVo.getApiUrl() + "/order/updateCancelStatus");
//        JSONObject result = HttpRequestHelper.sendRequest(reqMap, "http://localhost:9998/order/updateCancelStatus");
//
//        if (result.getInteger("code") != 200) {
//            throw new YyghException(result.getString("message"), ResultCodeEnum.FAIL.getCode());
//        } else {
//            //是否支付 退款
//            if (orderInfo.getOrderStatus().intValue() == OrderStatusEnum.PAID.getStatus().intValue()) {
//                //已支付 退款
//                boolean isRefund = weixinService.refund(orderId);
//                if (!isRefund) {
//                    throw new YyghException(ResultCodeEnum.CANCEL_ORDER_FAIL);
//                }
//            }
//            //更改订单状态
//            orderInfo.setOrderStatus(OrderStatusEnum.CANCLE.getStatus());
//            this.updateById(orderInfo);
//            //发送mq信息更新预约数 我们与下单成功更新预约数使用相同的mq信息，不设置可预约数与剩余预约数，接收端可预约数减1即可
//            OrderMqVo orderMqVo = new OrderMqVo();
//            orderMqVo.setScheduleId(orderInfo.getScheduleId());
//            //短信提示
//            MsmVo msmVo = new MsmVo();
//            msmVo.setPhone(orderInfo.getPatientPhone());
//            msmVo.setTemplateCode("SMS_194640722");
//            String reserveDate = new DateTime(orderInfo.getReserveDate()).toString("yyyy-MM-dd") + (orderInfo.getReserveTime() == 0 ? "上午" : "下午");
//            Map<String, Object> param = new HashMap<String, Object>() {{
//                put("title", orderInfo.getHosname() + "|" + orderInfo.getDepname() + "|" + orderInfo.getTitle());
//                put("reserveDate", reserveDate);
//                put("name", orderInfo.getPatientName());
//            }};
//            msmVo.setParam(param);
//            orderMqVo.setMsmVo(msmVo);
//            rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER, MqConst.ROUTING_ORDER, orderMqVo);
//        }
//        return true;
//    }
//
//    @Override
//    public void patientTips() {
//        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("reserve_date", new DateTime().toString("yyyy-MM-dd"));
//        List<OrderInfo> orderInfoList = baseMapper.selectList(queryWrapper);
//        for (OrderInfo orderInfo : orderInfoList) {
//            //短信提示
//            MsmVo msmVo = new MsmVo();
//            msmVo.setPhone(orderInfo.getPatientPhone());
//            String reserveDate = new DateTime(orderInfo.getReserveDate()).toString("yyyy-MM-dd") + (orderInfo.getReserveTime() == 0 ? "上午" : "下午");
//            Map<String, Object> param = new HashMap<String, Object>() {{
//                put("title", orderInfo.getHosname() + "|" + orderInfo.getDepname() + "|" + orderInfo.getTitle());
//                put("reserveDate", reserveDate);
//                put("name", orderInfo.getPatientName());
//            }};
//            msmVo.setParam(param);
//            boolean sendMessage = rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM_ITEM, msmVo);
//            log.info(sendMessage ? "发送就诊提醒短信成功" : "发送就诊提醒短信失败");
//        }
//    }
//
//    @Override
//    public Map<String, Object> show(Long orderId) {
//        Map<String, Object> map = new HashMap<>();
//        OrderInfo orderInfo = this.packOrderInfo(this.getById(orderId));
//        map.put("orderInfo", orderInfo);
//        Patient patient
//                = patientFeignClient.getPatientOrder(orderInfo.getPatientId());
//        map.put("patient", patient);
//        return map;
//    }
//
//    private OrderInfo packOrderInfo(OrderInfo orderInfo) {
//        orderInfo.getParam().put("orderStatusString", OrderStatusEnum.getStatusNameByStatus(orderInfo.getOrderStatus()));
//        return orderInfo;
//    }

}
