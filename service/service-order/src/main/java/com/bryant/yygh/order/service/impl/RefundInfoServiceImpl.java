package com.bryant.yygh.order.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bryant.yygh.enums.RefundStatusEnum;
import com.bryant.yygh.model.order.PaymentInfo;
import com.bryant.yygh.model.order.RefundInfo;
import com.bryant.yygh.order.mapper.RefundInfoMapper;
import com.bryant.yygh.order.service.RefundInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: RefundInfoServiceImpl
 * Author:   swust-liuchuan
 * Date:     2022/9/10 7:31
 * Description: 退款记录
 * History:
 */

@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {

    @Autowired
    private RefundInfoMapper refundInfoMapper;

    @Override
    public RefundInfo saveRefundInfo(PaymentInfo paymentInfo) {
        QueryWrapper<RefundInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", paymentInfo.getOrderId());
        queryWrapper.eq("payment_type", paymentInfo.getPaymentType());
        RefundInfo refundInfo = refundInfoMapper.selectOne(queryWrapper);
        if (null != refundInfo) {
            return refundInfo;
        }
        // 保存交易记录
        refundInfo = new RefundInfo();
        refundInfo.setCreateTime(new Date());
        refundInfo.setOrderId(paymentInfo.getOrderId());
        refundInfo.setPaymentType(paymentInfo.getPaymentType());
        refundInfo.setOutTradeNo(paymentInfo.getOutTradeNo());
        refundInfo.setRefundStatus(RefundStatusEnum.UNREFUND.getStatus());
        refundInfo.setSubject(paymentInfo.getSubject());
        //paymentInfo.setSubject("test");
        refundInfo.setTotalAmount(paymentInfo.getTotalAmount());
        refundInfoMapper.insert(refundInfo);
        return refundInfo;
    }
}