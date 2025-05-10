package com.inSoft.mapper.provider;

import com.inSoft.pojo.AppCat;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class AppCatProvider {

    // 条件查询
    public String listByCondition(AppCat appCat) {
        return new SQL(){{
            SELECT("*"); // 多条件查询
            FROM("app_cat");
            if (appCat.getName() != null ) {
                WHERE("name like CONCAT('%', #{name}, '%')");
            }
            ORDER_BY("updated_at DESC");
        }}.toString();
    }

    // 更新数据
    public String update(AppCat appCat) {
        return new SQL(){{
            UPDATE("app_cat");
            if (appCat.getName() != null) {
                SET("name = #{name}");
            }
            if (appCat.getDescription() != null) {
                SET("description = #{description}");
            }
            if (appCat.getIsDeleted() != null) {
                SET("is_deleted = #{isDeleted}");
            }
            if (appCat.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            if (appCat.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    //批量删除
    public String removeBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("app_cat");
            SET("is_deleted = 1");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    //批量恢复
    public String recoveryBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL(){{
            UPDATE("app_cat");
            SET("is_deleted = 0");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

}
