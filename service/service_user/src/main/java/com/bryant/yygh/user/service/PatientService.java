package com.bryant.yygh.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bryant.yygh.model.user.Patient;

import java.util.*;

public interface PatientService extends IService<Patient> {

    /**
     * 获取就诊人列表
     *
     * @param userId
     * @return
     */
    List<Patient> findAllUserId(Long userId);

    /**
     * 根据id获取就诊人信息
     *
     * @param id
     * @return
     */
    Patient getPatientId(Long id);
}
