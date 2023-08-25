package com.bryant.yygh.msm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(Dog.class)
public class AppTest
{

    @Autowired
    private Dog dog;

    @Test
    public void show(){
        System.out.println(dog.toString());
    }


}



