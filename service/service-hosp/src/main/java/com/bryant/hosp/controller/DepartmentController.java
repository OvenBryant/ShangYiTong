package com.bryant.hosp.controller;


import com.bryant.hosp.service.DepartmentService;
import com.bryant.yygh.common.result.Result;
import com.bryant.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: DepartmentController
 * Author:   swust-liuchuan
 * Date:     2022/7/14 22:18
 * Description:
 * History:
 */

@Slf4j
@Api(tags = "医院部门数据(MongoDB数据库)")
@RestController
@RequestMapping("/admin/hosp/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "根据hoscode获取所有科室列表")
    @GetMapping("/getDeptList/{hoscode}")
    public Result getDepartmentList(@PathVariable String hoscode) {
        List<DepartmentVo> departmentList = departmentService.findDeptTree(hoscode);
        return Result.ok(departmentList);
    }
}