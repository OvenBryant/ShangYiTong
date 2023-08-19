package com.bryant.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bryant.yygh.model.hosp.HospitalSet;

public interface IHospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);
}
