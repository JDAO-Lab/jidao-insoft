package com.inSoft.mapper.provider;

import com.inSoft.pojo.WebCharacteristic;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class WebCharacteristicProvider {
    //根据条件查询
    public String listByCondition(WebCharacteristic webCharacteristic) {
        return new SQL(){{
            SELECT("*"); // 多条件查询
            FROM("web_characteristic");
            if (webCharacteristic.getTitle() != null ) {
                WHERE("title like CONCAT('%', #{title}, '%')");
            }
            ORDER_BY("updated_at DESC");
        }}.toString();
    }

    // 更新数据
    public String update(WebCharacteristic webCharacteristic) {
        return new SQL(){{
            UPDATE("web_characteristic");
            if (webCharacteristic.getTitle() != null) {
                SET("title = #{title}");
            }
            if (webCharacteristic.getThumb() != null) {
                SET("thumb = #{thumb}");
            }
            if (webCharacteristic.getDescription() != null) {
                SET("description = #{description}");
            }
            if (webCharacteristic.getShortDescription() != null) {
                SET("short_description = #{shortDescription}");
            }
            if (webCharacteristic.getSort() != null) {
                SET("sort = #{sort}");
            }
            if (webCharacteristic.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            if (webCharacteristic.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    //批量删除
    public String removeBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            DELETE_FROM("web_characteristic");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }
}
