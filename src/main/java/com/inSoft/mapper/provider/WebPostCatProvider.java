package com.inSoft.mapper.provider;

import com.inSoft.pojo.WebPostCat;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class WebPostCatProvider {

    //根据条件查询
    public String listByCondition(WebPostCat webPostCat) {
        return new SQL(){{
            SELECT("*"); // 多条件查询
            FROM("web_post_cat");
            if (webPostCat.getName() != null ) {
                WHERE("name like CONCAT('%', #{name}, '%')");
            }
            ORDER_BY("updated_at DESC");
        }}.toString();
    }

    // 更新数据
    public String update(WebPostCat webPostCat) {
        return new SQL(){{
            UPDATE("web_post_cat");
            if (webPostCat.getName() != null) {
                SET("name = #{name}");
            }
            if (webPostCat.getDescription() != null) {
                SET("description = #{description}");
            }
            if (webPostCat.getIsDeleted() != null) {
                SET("is_deleted = #{isDeleted}");
            }
            if (webPostCat.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            if (webPostCat.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    //批量删除
    public String removeBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("web_post_cat");
            SET("is_deleted = 1");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    //批量恢复
    public String recoveryBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL(){{
            UPDATE("web_post_cat");
            SET("is_deleted = 0");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }
}
