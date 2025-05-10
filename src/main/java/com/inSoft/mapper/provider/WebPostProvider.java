package com.inSoft.mapper.provider;

import com.inSoft.pojo.WebPost;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class WebPostProvider {

    //根据条件查询
    public String listByCondition(WebPost webPost) {
        return new SQL(){{
            SELECT("wp.*,pc.name as cname");
            FROM("web_post wp");// 添加LEFT JOIN语句
            LEFT_OUTER_JOIN("web_post_cat pc ON wp.cid = pc.id");
            if (webPost.getTitle() != null ) {
                WHERE("wp.title like CONCAT('%', #{title}, '%')");
            }
            if (webPost.getCname() != null ) {
                WHERE("pc.name like CONCAT('%', #{cname}, '%')");
            }
            WHERE("wp.is_deleted = 0");
            ORDER_BY("wp.updated_at DESC"); // 确保使用表别名，以避免可能的冲突
        }}.toString();
    }

    //回收站列表查询
    public String listByConditionRecovery(WebPost webPost) {
        return new SQL(){{
            SELECT("wp.*,pc.name as cname");
            FROM("web_post wp");// 添加LEFT JOIN语句
            LEFT_OUTER_JOIN("web_post_cat pc ON wp.cid = pc.id");
            if (webPost.getTitle() != null ) {
                WHERE("wp.title like CONCAT('%', #{title}, '%')");
            }
            if (webPost.getCname() != null ) {
                WHERE("pc.name like CONCAT('%', #{cname}, '%')");
            }
            WHERE("wp.is_deleted = 1");
            ORDER_BY("wp.updated_at DESC"); // 确保使用表别名，以避免可能的冲突
        }}.toString();
    }


    // 更新数据
    public String update(WebPost webPost) {
        return new SQL(){{
            UPDATE("web_post");
            if (webPost.getTitle() != null) {
                SET("title = #{title}");
            }
            if (webPost.getDescription() != null) {
                SET("description = #{description}");
            }
            if (webPost.getContent() != null) {
                SET("content = #{content}");
            }
            if (webPost.getCid() != null) {
                SET("cid = #{cid}");
            }
            if (webPost.getViews() != null) {
                SET("views = #{views}");
            }
            if (webPost.getKeywords() != null) {
                SET("keywords = #{keywords}");
            }
            if (webPost.getIsDeleted() != null) {
                SET("is_deleted = #{isDeleted}");
            }
            if (webPost.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            if (webPost.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    //批量删除
    public String removeBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("web_post");
            SET("is_deleted = 1");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    //批量恢复
    public String recoveryBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL(){{
            UPDATE("web_post");
            SET("is_deleted = 0");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

}
