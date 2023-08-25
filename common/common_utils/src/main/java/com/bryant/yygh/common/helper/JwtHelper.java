package com.bryant.yygh.common.helper;

import io.jsonwebtoken.*;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: JwtHelper
 * Author:   swust-liuchuan
 * Date:     2022/7/19 18:24
 * Description:
 * History:
 */

public class JwtHelper {
    //token过期时间
    private static long tokenExpiration = 24 * 60 * 60 * 1000;
    //签名密钥
    private static String tokenSignKey = "123456";

    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                .setSubject("YYGH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    public static Long getUserId(String token) {
        if (StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    public static String getUserName(String token) {
        if (StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("userName");
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "Bryant");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserName(token));
    }
}