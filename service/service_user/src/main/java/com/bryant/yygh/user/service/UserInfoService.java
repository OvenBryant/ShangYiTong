package com.bryant.yygh.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bryant.yygh.model.user.UserInfo;
import com.bryant.yygh.vo.user.LoginVo;
import com.bryant.yygh.vo.user.UserInfoQueryVo;

import java.util.Map;

public interface UserInfoService {
    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);

    Map<String, Object> loginUser(LoginVo loginVo);
}
