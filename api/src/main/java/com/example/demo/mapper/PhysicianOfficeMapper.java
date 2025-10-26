package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.OfficeEntity;

@Mapper
public interface PhysicianOfficeMapper {
    @Select({
        "SELECT `office`.* FROM `physician_office`", 
        "LEFT JOIN `physician` ON(`physician`.`id` = `physician_office`.`physicianId`)",
        "LEFT JOIN `office` ON(`office`.`id` = `physician_office`.`officeId`)",
        "WHERE `physician_office`.`physicianId`=#{physicianId} AND `physician`.`deletedAt` IS NULL"
    })
    public List<OfficeEntity> findByPhysicianId(@Param("physicianId") Integer physicianId);

    @Insert({
        "INSERT INTO `physician_office`(`physicianId`, `officeId`)",
        "VALUE(#{physicianId}, #{officeId})"
    })
    public Integer create(@Param("physicianId") Integer physicianId, @Param("officeId") Integer officeId);

    @Delete({
        "DELETE FROM `physician_office`", 
        "WHERE `physicianId`=#{physicianId} AND `officeId`=#{officeId}"
    })
    public Integer remove(@Param("physicianId") Integer physicianId, @Param("officeId") Integer officeId);
}
