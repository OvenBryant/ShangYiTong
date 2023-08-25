package com.bryant.yygh.msm.utils;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "aliyun.sms")
@Component
@Data
public class Aliyun {

    private String regionId;
    private String accessKeyId;
    private String secret;

    public static String REGION_ID;
    public static String ACCESS_KEY_ID;
    public static String SECRET;


    @PostConstruct
    public void show(){
        REGION_ID = regionId;
        ACCESS_KEY_ID = accessKeyId;
        SECRET = secret;

        System.out.println("==================================");
        System.out.println(REGION_ID+":"+ACCESS_KEY_ID+":"+SECRET);
        System.out.println("==================================");
    }
}
