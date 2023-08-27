package com.bryant.yygh.common.utils;



import com.bryant.yygh.common.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: AuthContextHolder
 * Author:   swust-liuchuan
 * Date:     2022/8/20 0:30
 * Description: 获取当前用户信息工具类
 * History:
 */

//获取当前用户信息工具类
public class AuthContextHolder {
    //获取当前用户id
    public static Long getUserId(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        //jwt从token获取userid
        Long userId = JwtHelper.getUserId(token);
        return userId;
    }

    //获取当前用户名称
    public static String getUserName(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        //jwt从token获取userid
        String userName = JwtHelper.getUserName(token);
        return userName;
    }
}