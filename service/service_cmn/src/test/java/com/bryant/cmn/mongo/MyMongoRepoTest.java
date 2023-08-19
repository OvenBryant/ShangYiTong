package com.bryant.cmn.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyMongoRepoTest {


    @Autowired
    private MongoUserRepo mongoUserRepo;


    @Test
    public void t1(){
        MongoUser save = mongoUserRepo.save(new MongoUser("", "linux", "系统"));
        System.out.println(save);
    }
}
