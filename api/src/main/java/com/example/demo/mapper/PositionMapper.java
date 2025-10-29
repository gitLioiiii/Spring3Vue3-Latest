package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.PositionEntity;

@Mapper
public interface PositionMapper {

    @Select("SELECT * FROM `position`")
    public List<PositionEntity> find();

    @Select({
        "SELECT * FROM `position`", 
        "WHERE `id`=#{id}"
    })
    public PositionEntity findById(@Param("id") Integer id);

    @Insert({
        "INSERT `position`(`name`, `description`)", 
        "VALUE(#{name}, #{description})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer create(PositionEntity position);

    @Update({
        "UPDATE `position`", 
        "SET `name`=#{name}, `description`=#{description}", 
        "WHERE `id`=#{id}"
    })
    public Integer update(PositionEntity position);

    @Delete({
        "DELETE FROM `position`", 
        "WHERE `id`=#{id}"
    })
    public Integer remove(PositionEntity position);
}
