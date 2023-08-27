package com.bryant.yygh.user.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.bryant.yygh.common.exception.YyghException;
import com.bryant.yygh.common.helper.JwtHelper;
import com.bryant.yygh.common.result.Result;
import com.bryant.yygh.common.result.ResultCodeEnum;
import com.bryant.yygh.model.user.UserInfo;
import com.bryant.yygh.user.service.UserInfoService;
import com.bryant.yygh.user.service.impl.UserInfoServiceImpl;
import com.bryant.yygh.user.utils.ConstantPropertiesUtil;
import com.bryant.yygh.user.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: WeixinApiController
 * Author:   swust-liuchuan
 * Date:     2022/7/20 15:40
 * Description:
 * History:
 */

@Slf4j
@Api(tags = "微信接口")
@Controller
@RequestMapping("/api/ucenter/wx")
public class WeixinApiController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 生成微信扫描二维码
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/getLoginParam")
    @ResponseBody
    public Result getQrConnect() throws UnsupportedEncodingException {
        String redirectUri = URLEncoder.encode(ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL, "UTF-8");
        HashMap<String, Object> map = new HashMap<>();
        map.put("appid", ConstantPropertiesUtil.WX_OPEN_APP_ID);
        map.put("redirect_uri", redirectUri);
        map.put("scope", "snsapi_login");
        map.put("response_type","code");
        map.put("state", System.currentTimeMillis() + "");
        return Result.ok(map);
    }

    /**
     * 微信登录回调
     *
     * @param code
     * @param state
     * @return
     */
    //example:http://localhost:8160/api/ucenter/wx/callback?code=011Xl6ll2asky94WW2ml2c786m1Xl6lI&state=1658314476333
    @GetMapping("/callback")
    public String callback(String code, String state) {

        //打印临时参数
        log.info("state ================= " + state);
        log.info("code ================== " + code);
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(state)) {
            log.error("非法回调请求");
            throw new YyghException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }

        //拿着code和微信id、秘钥，请求微信固定地址，得到两个返回值
        //使用code和appid以及appscrect换取access_token
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")//%s 占位符
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");

        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantPropertiesUtil.WX_OPEN_APP_ID,//appid
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,//appsecret
                code);

        //使用httpClient请求地址
        try {
            //第一步：使用HttpClientUtils=得到微信返回的信息
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            log.info("accessTokenInfo:" + accessTokenInfo);

            //第二步：使用json工具得到 openid 和 access_token
            JSONObject jsonObject = JSONObject.parseObject(accessTokenInfo);
            String access_token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");
            log.info("access_token:" + access_token);
            log.info("openid:" + openid);

            //判断数据库中openid是否存在，存在则直接登录
            UserInfo userInfo = userInfoService.selectWxInfoOpenId(openid);

            if (userInfo == null) {  //不存在该用户，调用微信执行下面的操作，获取用户信息，并且存入数据库
                //第三步：拿着openid 和 access_token请求微信地址，最终得到用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String resultInfo = HttpClientUtils.get(userInfoUrl);//最终用户信息
                log.info("用户信息" + resultInfo);

                //第四步：使用json工具得到nickname和头像
                JSONObject resultInfoJson = JSONObject.parseObject(resultInfo);
                //昵称
                String nickname = resultInfoJson.getString("nickname");
                //头像
            String headImgUrl = resultInfoJson.getString("headimgurl");

                //第五步：将得到的信息存入数据库
                //创建对象存储信息
                userInfo = new UserInfo();
                userInfo.setNickName(nickname);
                userInfo.setOpenid(openid);
                userInfo.setStatus(1);
                userInfo.setCertificatesUrl(headImgUrl);
                //存入数据库
                userInfoService.saveInfo(userInfo);
            }

            //第六步：设置返回信息
            Map<String, Object> map = new HashMap<>();
            String name = userInfo.getName();
            //姓名为空用昵称，昵称为空用电话
            if (StringUtils.isEmpty(name)) {
                name = userInfo.getNickName();
            }
            if (StringUtils.isEmpty(name)) {
                name = userInfo.getPhone();
            }
            map.put("name", name);

            //使用微信扫码登录的才有openid,使用微信扫码根据openid为空绑定手机号
            //有电话说明使用短信验证码登录
            if (StringUtils.isEmpty(userInfo.getPhone())) {
                map.put("openid", userInfo.getOpenid());
            } else {
                map.put("openid", "");
            }

            String token = JwtHelper.createToken(userInfo.getId(), name);
            map.put("token", token);

            //跳转前端页面
            return "redirect:" +
                    ConstantPropertiesUtil.YYGH_BASE_URL
                    + "/weixin/callback?token="
                    + map.get("token")
                    + "&openid="
                    + map.get("openid")
                    + "&name="
                    + URLEncoder.encode((String) map.get("name"), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        String s = "";
        return s;
    }
}