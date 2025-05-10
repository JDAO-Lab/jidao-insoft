package com.inSoft.mapper.provider;

import com.inSoft.pojo.WebPushProduct;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class WebPushProductProvider {
    //根据条件查询
    public String listByCondition(WebPushProduct webPushProduct) {
        return new SQL(){{
            SELECT("*"); // 多条件查询
            FROM("web_push_product");
            if (webPushProduct.getName() != null ) {
                WHERE("name like CONCAT('%', #{name}, '%')");
            }
            ORDER_BY("updated_at DESC");
        }}.toString();
    }

    // 更新数据
    public String update(WebPushProduct webPushProduct) {
        return new SQL(){{
            UPDATE("web_push_product");
            if (webPushProduct.getName() != null) {
                SET("name = #{name}");
            }
            if (webPushProduct.getLogo() != null) {
                SET("logo = #{logo}");
            }
            if (webPushProduct.getHref() != null) {
                SET("href = #{href}");
            }
            if (webPushProduct.getSort() != null) {
                SET("sort = #{sort}");
            }
            if (webPushProduct.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            if (webPushProduct.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    //批量删除
    public String removeBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            DELETE_FROM("web_push_product");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }
}
