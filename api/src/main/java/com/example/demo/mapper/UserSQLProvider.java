package com.example.demo.mapper;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class UserSQLProvider {

    public static String find(Map<String, Object> condition) {
        return new SQL() {{
            SELECT("*");
            FROM("`user`");
            if (condition.get("keywords") != null) {
                WHERE("`username` LIKE CONCAT('%', #{keywords}, '%')");
            }
            WHERE("`deletedAt` IS NULL");
        }}.toString();
    }

}
