package com.bryant.hosp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bryant.hosp.service.IHospitalSetService;
import com.bryant.yygh.common.result.Result;
import com.bryant.yygh.common.utils.MD5;
import com.bryant.yygh.model.hosp.HospitalSet;
import com.bryant.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.internal.util.xml.impl.ReaderUTF8;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Api(tags = "医院设置信息管理(Mysql)")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private IHospitalSetService hospitalSetService;

    /**
     * 查找所有医院设置信息
     *
     * @return
     */
    //http:localhost:8201/admin/hosp/hospitalSet/findAll
    @ApiOperation(value = "获取所有医院设置信息")
    @GetMapping("/findAll")
    public Result findAll() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    /**
     * 逻辑删除HospSet
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id逻辑删除id信息")
    @DeleteMapping("{id}")
    public Result DeleteById(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        return flag?Result.ok():Result.fail();
    }

    /**
     * 分页接口
     * 参数：当前页，大小，参数对象（医院名称，医院代号）
     *
     * @param current
     * @param limit
     * @param hospitalSetQueryVo
     * @return
     */
    //使用@RequestBody后使用POSTMapping获取请求的值
    @ApiOperation(value = "分页查询")
    @PostMapping("/page/{current}/{limit}")
    public Result pageAll(@PathVariable Integer current,
                          @PathVariable Integer limit,
                          @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {


        log.info("分页查询接口调用" + LocalTime.now().toString());

        Page<HospitalSet> page = new Page<>(current, limit);

        //1.模糊查询,lambdaQueryWrapper先判空
        QueryWrapper queryWrapper = new QueryWrapper();
        String hoscode = hospitalSetQueryVo.getHoscode();
        String hosname = hospitalSetQueryVo.getHosname();

        if(!StringUtils.isEmpty(hoscode)){
            queryWrapper.eq("hoscode",hoscode);
        }
        if(!StringUtils.isEmpty(hosname)){
            queryWrapper.like("hosname",hosname);
        }

        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);
        return Result.ok(hospitalSetPage);
    }

    /**
     * 添加医院信息
     *
     * @param hospitalSet
     * @return
     */
    @ApiOperation(value = "添加医院设置信息")
    @PostMapping("/add")
    public Result addHospSet(@RequestBody HospitalSet hospitalSet) {
        //1是可使用，0不可使用,默认可用1
        hospitalSet.setStatus(1);
        //随机生成秘钥
        Random random = new Random();
        String encrypt = MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000));
        hospitalSet.setSignKey(encrypt);

        boolean save = hospitalSetService.save(hospitalSet);
        return save ? Result.ok() : Result.fail();
    }

    /**
     * 根据id查找医院信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查找医院信息")
    @GetMapping("{id}")
    public Result findById(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     * 根据id更新医院信息
     *
     * @param hospitalSet
     * @return
     */
    @ApiOperation(value = "根据id更新医院信息")
    @PutMapping("/update")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet) {
        boolean update = hospitalSetService.updateById(hospitalSet);
        return update ? Result.ok() : Result.fail();
    }


    /**
     * 根据id批量删除医院信息
     *
     * @param idList
     * @return
     */
    @ApiOperation(value = "根据数组类型ids批量删除医院信息")
    @DeleteMapping("/deleteSelected")
    public Result deleteSelected(@RequestBody List<Long> idList) {
//       模拟失败
        //        try {
//            int i = 1 / 0;
//        } catch (Exception e) {
//            throw new YyghException("失败", 201);
//        }
        boolean remove = hospitalSetService.removeByIds(idList);
        return remove ? Result.ok() : Result.fail();
    }

    /**
     * 设置医院锁定还是解锁
     *
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "设置医院锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id, @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        boolean update = hospitalSetService.updateById(hospitalSet);
        return update ? Result.ok() : Result.fail();
    }

    /**
     * 根据id发送密钥
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "发送密钥")
    @PutMapping("/sendKey/{id}")
    public Result sendKey(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信 已经完成了
        return Result.ok();
    }

}

