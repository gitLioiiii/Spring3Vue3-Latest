package com.example.demo.mapper;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class UserSQLProvider {

        public static String count(Map<String, Object> condition) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("`user`");
            if (condition.get("keywords") != null) {
                WHERE(
                    "`name` LIKE CONCAT('%', #{keywords}, '%') OR " + 
                    "`username` LIKE CONCAT('%', #{keywords}, '%')"
                );
            }
            AND();
            WHERE("`deletedAt` IS NULL");
        }}.toString();
    }

    public static String find(Map<String, Object> condition) {
        return new SQL() {{
            SELECT("*");
            FROM("`user`");
            if (condition.get("keywords") != null) {
                WHERE(
                    "`name` LIKE CONCAT('%', #{keywords}, '%') OR " + 
                    "`username` LIKE CONCAT('%', #{keywords}, '%')"
                );
            }
            AND();
            WHERE("`deletedAt` IS NULL");
            ORDER_BY("`id` ASC");
            LIMIT("#{offset},#{limit}");
        }}.toString();
    }

}
