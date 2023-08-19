package com.bryant.hosp.repository;

import com.bryant.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    /**
     * 通过hoscode和hosScheduleId查询排班信息
     *
     * @param hoscode
     * @param hosScheduleId
     * @return
     */
    Schedule getByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);

    /**
     * 通过hoscode和depcode、工作日期获取排班详情
     *
     * @param hoscode
     * @param depcode
     * @param toDate
     * @return
     */
    List<Schedule> findByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date toDate);

    /**
     * 通过hoscode和depcode获取排班详情
     *
     * @param hoscode
     * @param depcode
     * @return
     */
    List<Schedule> findByHoscodeAndDepcode(String hoscode, String depcode);

    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);

    List<Schedule> findScheduleByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date toDate);
}
