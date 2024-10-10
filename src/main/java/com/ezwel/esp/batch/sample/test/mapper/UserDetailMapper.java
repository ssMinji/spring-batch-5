package com.ezwel.esp.batch.sample.test.mapper;

import com.ezwel.esp.batch.sample.test.domain.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserDetailMapper {

    void updateEndDate(@Param("empId") String empId, @Param("endDate") LocalDate endDate);
    void updateActiveStatus(@Param("empId") String empId, @Param("activeStatus") String activeStatus);
    List<UserDetail> selectUserDetailById(@Param("id") int id);
}