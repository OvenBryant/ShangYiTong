package com.bryant.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bryant.yygh.model.hosp.HospitalSet;
import com.bryant.yygh.vo.order.SignInfoVo;

public interface IHospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);

    SignInfoVo getSignInfoVo(String hoscode);

}
