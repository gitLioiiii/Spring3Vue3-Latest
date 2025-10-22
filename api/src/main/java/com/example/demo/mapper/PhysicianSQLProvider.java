package com.example.demo.mapper;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public interface PhysicianSQLProvider {
    
    public static String count(Map<String,Object> condition) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("`physician`");
            if (condition.get("keywords") != null) {
                WHERE("`name` LIKE CONCAT('%', #{keywords}, '%')");
                OR();
                WHERE("`phone` LIKE CONCAT('%', #{keywords}, '%')");
            }
            if (condition.get("officeId") != null && !condition.get("officeId").toString().isEmpty()) {
                WHERE("`officeId` = #{officeId}");
            }
            WHERE("`deletedAt` IS NULL");
        }}.toString();
    }

    public static String find(Map<String,Object> condition) {
        // phy和off作别名
        return new SQL() {{
            SELECT("phy.*");
            SELECT("off.id AS office_id");
            SELECT("off.name AS office_name");
            SELECT("off.description AS office_description");
            FROM("`physician` phy");
            LEFT_OUTER_JOIN("`office` off ON off.id = phy.officeId");
            if (condition.get("keywords") != null) {
                WHERE("phy.`name` LIKE CONCAT('%', #{keywords}, '%')");
                OR();
                WHERE("`phone` LIKE CONCAT('%', #{keywords}, '%')");
            }
            if (condition.get("officeId") != null && !condition.get("officeId").toString().isEmpty()) {
                WHERE("phy.`officeId` = #{officeId}");
            }
            WHERE("phy.`deletedAt` IS NULL");
            ORDER_BY("phy.`id` ASC");
            LIMIT("#{offset},#{limit}");
        }}.toString();
    }
}
