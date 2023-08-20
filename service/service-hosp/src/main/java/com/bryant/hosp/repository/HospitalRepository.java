package com.bryant.hosp.repository;

import com.bryant.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {

    /**
     * 通过hosCode查询是否存在这个医院信息
     *        // 符合规范,不需要具体的实现
     * @param hoscode
     * @return
     */
    Hospital getHospitalByHoscode(String hoscode);

//    /**
//     * 通过hosname模糊查询
//     *
//     * @param hosname
//     * @return
//     */
//    ArrayList<Hospital> findByHosnameLike(String hosname);

    /**        // 符合规范,不需要具体的实现
     * 根据hosname模糊查询
     * @param hosname
     * @return
     */
    List<Hospital> findHospitalByHosnameLike(String hosname);



}