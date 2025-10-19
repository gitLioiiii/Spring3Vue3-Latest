package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.OfficeEntity;

@Mapper
public interface OfficeMapper {
    @Select("SELECT * FROM `office`")
    public List<OfficeEntity> find();

    @Select({
        "SELECT * FROM `office`", 
        "WHERE `id`=#{id}"
    })
    public OfficeEntity findById(@Param("id") Integer id);

    @Insert({
        "INSERT `office`(`name`, `description`)", 
        "VALUE(#{name}, #{description})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer create(OfficeEntity office);

    @Update({
        "UPDATE `office`", 
        "SET `name`=#{name}, `description`=#{description}", 
        "WHERE `id`=#{id}"
    })
    public Integer update(OfficeEntity office);

    @Delete({
        "DELETE FROM `office`", 
        "WHERE `id`=#{id}"
    })
    public Integer remove(OfficeEntity office);
}
