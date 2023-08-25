package com.bryant.yygh.msm;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dog")
@Data
class Dog{
    private String name;
    private String age;
    private String color;
}