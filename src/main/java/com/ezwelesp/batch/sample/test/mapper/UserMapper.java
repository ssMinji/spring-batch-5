package com.ezwelesp.batch.sample.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    String selectUserName(@Param("id") int id);

    int selectMaxId();

    void updateUserName(@Param("id") int id, @Param("name") String name);

    void insertUser(@Param("id") int id, @Param("name") String name);
}