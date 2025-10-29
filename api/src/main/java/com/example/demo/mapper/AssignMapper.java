package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import com.example.demo.entity.PhysicianEntity;

@Mapper
public interface AssignMapper {

    @SelectProvider(type = AssignSQLProvider.class, method = "count")
    public Integer count(Map<String,Object> condition);

    @SelectProvider(type = AssignSQLProvider.class, method = "find")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "name", column = "name"),
        @Result(property = "age", column = "age"),
        @Result(property = "gender", column = "gender"),
        @Result(property = "officeId", column = "officeId"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "serve", column = "serve"),
        // 实现一对多
        @Result(property = "positions", column = "id", 
            many = @Many(select = "findPositionsByPhysicianId"))
    })
    public List<PhysicianEntity> find(Map<String,Object> condition);

    @SelectProvider(type = AssignSQLProvider.class, method = "findPositionsByPhysicianId")
    public List<com.example.demo.entity.PositionEntity> findPositionsByPhysicianId(Integer physicianId);
}


