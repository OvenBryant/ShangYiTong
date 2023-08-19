package com.bryant.cmn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("oven")
public class MongoUser {

    @Id// 必须指定id列
    private String id;
    private String name;
    private String msg;
}
