package com.bryant.yygh.user.controller;

;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bryant.yygh.common.result.Result;
import com.bryant.yygh.model.user.UserInfo;
import com.bryant.yygh.user.service.UserInfoService;
import com.bryant.yygh.vo.user.UserInfoQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: UserController
 * Author:   swust-liuchuan
 * Date:     2022/8/24 23:42
 * Description:
 * History:
 */

@Slf4j
@Api(tags = "平台用户管理")
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 条件查询带分页
     *
     * @param page
     * @param limit
     * @param userInfoQueryVo 条件
     * @return
     */
    @ApiOperation(value = "条件查询带分页")
    @GetMapping("{page}/{limit}")
    public Result page(@PathVariable Long page, @PathVariable Long limit, UserInfoQueryVo userInfoQueryVo) {
        Page<UserInfo> userInfoPage = new Page<>(page, limit);
        IPage<UserInfo> userInfoIPage = userInfoService.selectPage(userInfoPage, userInfoQueryVo);
        return Result.ok(userInfoIPage);
    }

    /**
     * 锁定
     *
     * @param userId
     * @param status
     * @return
     */
//    @ApiOperation(value = "锁定")
//    @GetMapping("lock/{userId}/{status}")
//    public Result lock(@PathVariable Long userId, @PathVariable Integer status) {
//        userInfoService.lock(userId, status);
//        return Result.ok();
//    }

    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
//    @ApiOperation(value = "用户详情")
//    @GetMapping("show/{userId}")
//    public Result show(@PathVariable Long userId) {
//        Map<String, Object> map = userInfoService.show(userId);
//        return Result.ok(map);
//    }


    /**
     * 认证审批
     *
     * @param userId
     * @param authStatus
     * @return
     */
//    @ApiOperation(value = "认证审批")
//    @GetMapping("approval/{userId}/{authStatus}")
//    public Result approval(@PathVariable Long userId, @PathVariable Integer authStatus) {
//        userInfoService.approval(userId, authStatus);
//        return Result.ok();
//    }

}