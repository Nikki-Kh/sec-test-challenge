package com.nikh.challenge.dao;

import com.nikh.challenge.dto.UserInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{pass} LIMIT 1")
    UserInfoBean getUser(@Param("username") String username, @Param("pass") String password);
}
