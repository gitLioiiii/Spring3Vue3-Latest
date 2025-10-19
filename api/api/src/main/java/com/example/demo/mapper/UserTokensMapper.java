package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.TokenEntity;

@Mapper
public interface UserTokensMapper {

    @Select("SELECT * FROM `user_tokens` WHERE `token`=#{token}")
    @Result(id = true, column = "id", property = "id")
    @Result(
        column = "userId", 
        property = "user",
        one = @One(
            select = "com.example.demo.mapper.UserMapper.findById"
        )
    )
    TokenEntity find(@Param("token") String token);

    @Insert(
        "INSERT INTO `user_tokens`(`token`, `userId`, `expireAt`, `createdAt`) " + 
        "VALUE(#{token}, #{userId}, #{expireAt}, #{createdAt})"
    )
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Integer create(TokenEntity token);

}
