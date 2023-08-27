package com.bryant.yygh.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bryant.yygh.model.user.UserInfo;
import com.bryant.yygh.vo.user.LoginVo;
import com.bryant.yygh.vo.user.UserAuthVo;
import com.bryant.yygh.vo.user.UserInfoQueryVo;

import java.util.Map;

public interface UserInfoService {
    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);

    Map<String, Object> loginUser(LoginVo loginVo);

    UserInfo selectWxInfoOpenId(String openid);


    void saveInfo(UserInfo userInfo);

    void userAuth(Long userId, UserAuthVo userAuthVo);

    UserInfo getById(Long userId);

    void lock(Long userId, Integer status);

    Map<String, Object> show(Long userId);

    void approval(Long userId, Integer authStatus);
}
