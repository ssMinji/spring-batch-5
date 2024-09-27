package com.ezwel.esp.batch.sample.test.mapper;

import com.ezwel.esp.batch.sample.test.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    String selectUserName(@Param("id") int id);

    int selectMaxId();

    void updateUserName(@Param("id") int id, @Param("name") String name);

    void insertUser(@Param("id") int id, @Param("name") String name);
}