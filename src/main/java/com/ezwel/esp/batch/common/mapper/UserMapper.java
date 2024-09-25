package com.ezwel.esp.batch.common.mapper;

import com.ezwel.esp.batch.common.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAllUsers();

    String selectUserName(@Param("id") int id);

    void updateUserName(@Param("id") int id, @Param("name") String name);
}