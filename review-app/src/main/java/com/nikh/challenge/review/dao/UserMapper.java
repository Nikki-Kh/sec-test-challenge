package com.nikh.challenge.review.dao;

import com.nikh.challenge.review.dto.UserInfoBean;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{pass} LIMIT 1")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password")
    })
    UserInfoBean getUser(@Param("username") String username, @Param("pass") String password);
}
