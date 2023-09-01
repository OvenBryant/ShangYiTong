package com.bryant.yygh.order.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.bryant.yygh.enums.PaymentTypeEnum;
import com.bryant.yygh.enums.RefundStatusEnum;
import com.bryant.yygh.model.order.OrderInfo;
import com.bryant.yygh.model.order.PaymentInfo;
import com.bryant.yygh.model.order.RefundInfo;
import com.bryant.yygh.order.service.OrderService;
import com.bryant.yygh.order.service.PaymentService;
import com.bryant.yygh.order.service.RefundInfoService;
import com.bryant.yygh.order.service.WeixinService;
import com.bryant.yygh.order.utils.ConstantPropertiesUtilsForWeXin;
import com.bryant.yygh.order.utils.HttpClient;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: WeixinServiceImpl
 * Author:   swust-liuchuan
 * Date:     2022/9/10 6:51
 * Description:
 * History:
 */

@Service
public class WeixinServiceImpl implements WeixinService {

    @Autowired
    private OrderService orderService;

    @Resource
    private PaymentService paymentService;
    @Resource
    private RefundInfoService refundInfoService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据订单号下单，生成支付链接
     */
    @Override
    public Map createNative(Long orderId) {
        try {
            Map payMap = (Map) redisTemplate.opsForValue().get(orderId.toString());
            if (null != payMap) return payMap;
            //根据id获取订单信息
            OrderInfo order = orderService.getById(orderId);
            // 保存交易记录
            paymentService.savePaymentInfo(order, PaymentTypeEnum.WEIXIN.getStatus());
            //1、设置参数
            Map paramMap = new HashMap();
            paramMap.put("appid", ConstantPropertiesUtilsForWeXin.APPID);
            paramMap.put("mch_id", ConstantPropertiesUtilsForWeXin.PARTNER);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            String body = order.getReserveDate() + "就诊" + order.getDepname();
            paramMap.put("body", body);
            paramMap.put("out_trade_no", order.getOutTradeNo());
            //paramMap.put("total_fee", order.getAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap.put("total_fee", "1");
            paramMap.put("spbill_create_ip", "127.0.0.1");
            paramMap.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            paramMap.put("trade_type", "NATIVE");
            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtilsForWeXin.PARTNERKEY));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、封装返回结果集
            Map map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("totalFee", order.getAmount());
            map.put("resultCode", resultMap.get("result_code"));
            map.put("codeUrl", resultMap.get("code_url"));
            if (null != resultMap.get("result_code")) {
                //微信支付二维码2小时过期，可采取2小时未支付取消订单
                redisTemplate.opsForValue().set(orderId.toString(), map, 1000, TimeUnit.MINUTES);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    @Override
    public Map queryPayStatus(Long orderId, String paymentType) {
        try {
            OrderInfo orderInfo = orderService.getById(orderId);
            //1、封装参数
            Map paramMap = new HashMap<>();
            paramMap.put("appid", ConstantPropertiesUtilsForWeXin.APPID);
            paramMap.put("mch_id", ConstantPropertiesUtilsForWeXin.PARTNER);
            paramMap.put("out_trade_no", orderInfo.getOutTradeNo());
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtilsForWeXin.PARTNERKEY));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据，转成Map
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、返回
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean refund(Long orderId) {
        try {
            PaymentInfo paymentInfoQuery = paymentService.getPaymentInfo(orderId, PaymentTypeEnum.WEIXIN.getStatus());

            RefundInfo refundInfo = refundInfoService.saveRefundInfo(paymentInfoQuery);
            if (refundInfo.getRefundStatus().intValue() == RefundStatusEnum.REFUND.getStatus().intValue()) {
                return true;
            }
            Map<String, String> paramMap = new HashMap<>(8);
            paramMap.put("appid", ConstantPropertiesUtilsForWeXin.APPID);       //公众账号ID
            paramMap.put("mch_id", ConstantPropertiesUtilsForWeXin.PARTNER);   //商户编号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paramMap.put("transaction_id", paymentInfoQuery.getTradeNo()); //微信订单号
            paramMap.put("out_trade_no", paymentInfoQuery.getOutTradeNo()); //商户订单编号
            paramMap.put("out_refund_no", "tk" + paymentInfoQuery.getOutTradeNo()); //商户退款单号
//       paramMap.put("total_fee",paymentInfoQuery.getTotalAmount().multiply(new BigDecimal("100")).longValue()+"");
//       paramMap.put("refund_fee",paymentInfoQuery.getTotalAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap.put("total_fee", "1");
            paramMap.put("refund_fee", "1");
            String paramXml = WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtilsForWeXin.PARTNERKEY);
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/secapi/pay/refund");
            client.setXmlParam(paramXml);
            client.setHttps(true);
            client.setCert(true);
            client.setCertPassword(ConstantPropertiesUtilsForWeXin.PARTNER);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            if (null != resultMap && WXPayConstants.SUCCESS.equalsIgnoreCase(resultMap.get("result_code"))) {
                refundInfo.setCallbackTime(new Date());
                refundInfo.setTradeNo(resultMap.get("refund_id"));
                refundInfo.setRefundStatus(RefundStatusEnum.REFUND.getStatus());
                refundInfo.setCallbackContent(JSONObject.toJSONString(resultMap));
                refundInfoService.updateById(refundInfo);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}