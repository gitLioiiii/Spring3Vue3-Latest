package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.PhysicianEntity;

@Mapper
public interface PhysicianMapper {
    
    @SelectProvider(type = PhysicianSQLProvider.class, method = "count")
    public Integer count(Map<String,Object> condition);
    
    @SelectProvider(type = PhysicianSQLProvider.class, method = "find")
    @Result(column = "officeId", property = "officeId")
    @Result(column = "office_id", property = "office.id")
    @Result(column = "office_name", property = "office.name")
    @Result(column = "office_description", property = "office.description")
    public List<PhysicianEntity> find(Map<String,Object> condition);

    @Insert({
        "INSERT `physician`(`username`,`password`,`name`,`age`,`gender`,`officeId`,`phone`,`serve`,`registeredAt`)",
        "VALUE(#{username}, #{password}, #{name}, #{age}, #{gender}, #{officeId}, #{phone}, #{serve}, NOW())"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer create(PhysicianEntity Physician);

    @Select({
        "SELECT * FROM `physician`", 
        "WHERE `deletedAt` IS NULL",
        "AND `id`=#{id}"
    })
    public PhysicianEntity findById(@Param("id") Integer id);

    @Update({
        "UPDATE `physician` SET `deletedAt` = #{deletedAt}",
        "WHERE `id` = #{id}"
    })
    public Integer remove(PhysicianEntity physician);

    @Update({
    "UPDATE `physician`", 
    "SET `username`=#{username}, `password`=#{password}, `name`=#{name}, `age`=#{age}, `gender`=#{gender}, ",
    "`officeId`=#{officeId}, `phone` =#{phone}, `serve`=#{serve}",
    "WHERE `id`=#{id}"
    })
    public Integer update(PhysicianEntity physician);

    @Select({
        "SELECT * FROM `physician` WHERE `deletedAt` IS NULL"
    })
    public List<PhysicianEntity> findAll();
}
