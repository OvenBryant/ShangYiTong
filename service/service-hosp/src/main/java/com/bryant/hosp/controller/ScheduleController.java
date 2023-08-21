package com.bryant.hosp.controller;

import com.bryant.hosp.service.ScheduleService;
import com.bryant.yygh.common.result.Result;
import com.bryant.yygh.model.hosp.Schedule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: ScheduleController
 * Author:   swust-liuchuan
 * Date:     2022/7/15 19:11
 * Description:
 * History:
 */

@Slf4j
@Api(tags = "医院排班数据(MongoDB数据库)")
@RestController
@RequestMapping("/admin/hosp/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 根据医院编号(hoscode)和科室编号(depcode)，分页查询排班规则数据
     *
     * @param page
     * @param limit
     * @param hoscode
     * @param depcode
     * @return
     */
    //根据医院编号(hoscode)和科室编号(depcode)，分页查询排班规则数据
    @ApiOperation(value = "根据医院编号(hoscode)和科室编号(depcode)，查询排班规则数据")
    @GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable Integer page, @PathVariable Integer limit, @PathVariable String hoscode, @PathVariable String depcode) {
        Map<String, Object> map = scheduleService.getRuleSchedule(page, limit, hoscode, depcode);
        return Result.ok(map);
    }

    @ApiOperation(value = "根据医院编号(hoscode)和科室编号(depcode),工作日期(workDate)查询排班详细信息")
    @GetMapping("getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    public Result getScheduleDetail(@PathVariable String hoscode, @PathVariable String depcode, @PathVariable String workDate) {
        List<Schedule> scheduleList = scheduleService.getDetailSchedule(hoscode, depcode, workDate);
        return Result.ok(scheduleList);

    }
}