package com.example.demo.mapper;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public interface AssignSQLProvider {

    public static String count(Map<String,Object> condition) {
        return new SQL() {{
            SELECT("COUNT(DISTINCT phy.id)");
            FROM("`physician` phy");
            if (condition.get("keywords") != null) {
                WHERE("phy.`name` LIKE CONCAT('%', #{keywords}, '%')");
            }
            WHERE("phy.`deletedAt` IS NULL");
        }}.toString();
    }

    public static String find(Map<String,Object> condition) {
        return new SQL() {{
            SELECT("phy.*");
            FROM("`physician` phy");
            if (condition.get("keywords") != null) {
                WHERE("phy.`name` LIKE CONCAT('%', #{keywords}, '%')");
            }
            WHERE("phy.`deletedAt` IS NULL");
            ORDER_BY("phy.`id` ASC");
            LIMIT("#{offset},#{limit}");
        }}.toString();
    }

    public static String findPositionsByPhysicianId(Integer physicianId) {
        return new SQL() {{
            SELECT("pos.*");
            FROM("`position` pos");
            INNER_JOIN("`physician_position` phy_pos ON phy_pos.positionId = pos.id");
            WHERE("phy_pos.physicianId = #{physicianId}");
            ORDER_BY("pos.`id` ASC");
        }}.toString();
    }
}


