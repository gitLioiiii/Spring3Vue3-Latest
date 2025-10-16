package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.UserEntity;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    public UserEntity login(String username, String password);

}
