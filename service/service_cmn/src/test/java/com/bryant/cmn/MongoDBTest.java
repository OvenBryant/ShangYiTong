package com.bryant.cmn;


import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class MongoDBTest {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 插入
     */
    @Test
    public void InsertData(){
        MongoUser mongoUser = new MongoUser();
        mongoUser.setName("猪");
        mongoUser.setMsg("猪八戒");
        MongoUser user = mongoTemplate.insert(mongoUser);
        System.out.println(user);
    }


    /**
     * 根据id查询数据
     */
    @Test
    public void getByIdData(){
        MongoUser mongoUser = mongoTemplate.findById("64e03cc745eae452b6d459db", MongoUser.class);
        System.out.println(mongoUser);
    }

    @Test
    public void getAllData(){
        List<MongoUser> all = mongoTemplate.findAll(MongoUser.class);
        System.out.println(all);
    }

    /**
     * 根据id更新数据
     */
    @Test
    public void updateData(){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("64e03c3196ae4612d5a256c8"));

        Update update = new Update();
        update.set("name","如来");
        update.set("msg","如来神掌");
        mongoTemplate.upsert(query,update, MongoUser.class);
    }
}
