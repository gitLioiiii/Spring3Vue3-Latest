package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.PositionEntity;

@Mapper
public interface PhysicianPositionMapper {

    @Select({
        "SELECT `position`.* FROM `physician_position`", 
        "LEFT JOIN `physician` ON(`physician`.`id` = `physician_position`.`physicianId`)",
        "LEFT JOIN `position` ON(`position`.`id` = `physician_position`.`positionId`)",
        "WHERE `physician_position`.`physicianId`=#{physicianId} AND `physician`.`deletedAt` IS NULL"
    })
    public List<PositionEntity> findByPhysicianId(@Param("physicianId") Integer physicianId);

    @Insert({
        "INSERT INTO `physician_position`(`physicianId`, `positionId`)",
        "VALUE(#{physicianId}, #{positionId})"
    })
    public Integer create(@Param("physicianId") Integer physicianId, @Param("positionId") Integer positionId);

    @Delete({
        "DELETE FROM `physician_position`", 
        "WHERE `physicianId`=#{physicianId} AND `positionId`=#{positionId}"
    })
    public Integer remove(@Param("physicianId") Integer physicianId, @Param("positionId") Integer positionId);
}
