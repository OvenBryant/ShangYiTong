package com.bryant.cmn.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserRepo extends MongoRepository<MongoUser,String> {
}
