package com.bryant.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bryant.hosp.mapper.HospitalSetMapper;
import com.bryant.hosp.service.IHospitalSetService;
import com.bryant.yygh.common.exception.YyghException;
import com.bryant.yygh.common.result.ResultCodeEnum;
import com.bryant.yygh.model.hosp.HospitalSet;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements IHospitalSetService {


    /**
     * 根据医院编号,查询数据库,查询签名
     * @param hoscode
     * @return
     */
    @Override
    public String getSignKey(String hoscode) {

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("hoscode", hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }

//    @Override
//    public String getSignKey(String hoscode) {
//        LambdaQueryWrapper<HospitalSet> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(HospitalSet::getHoscode, hoscode);
//        HospitalSet hospitalSet = baseMapper.selectOne(queryWrapper);
//
//        if (hospitalSet == null) {
//            throw new YyghException(ResultCodeEnum.DATA_ERROR);
//        }
//
//        return hospitalSet.getSignKey();
//    }

}
