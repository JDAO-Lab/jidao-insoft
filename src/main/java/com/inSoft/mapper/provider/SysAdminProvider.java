package com.inSoft.mapper.provider;

import com.inSoft.pojo.SysAdmin;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class SysAdminProvider {

    // 批量删除
    public String removeBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("sys_admin");
            SET("is_deleted = 1");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    // 条件查询
    public String listByCondition(SysAdmin sysAdmin) {
        return new SQL(){{
            SELECT("sa.*, sr.name as rule_name"); // 多条件查询
            FROM("sys_admin sa");
            LEFT_OUTER_JOIN("sys_rules sr ON sa.rule_id = sr.id"); // 左连接sys_rules表，并指定关联条件
            if (sysAdmin.getUsername() != null ) {
                WHERE("sa.username like CONCAT('%', #{username}, '%')");
            }
            WHERE("sa.is_deleted = 0");
            ORDER_BY("sa.id ASC");
        }}.toString();
    }

    public String update(SysAdmin sysAdmin) {
        return new SQL() {{
            UPDATE("sys_admin");
            if (sysAdmin.getUsername() != null) {
                SET("username = #{username}");
            }
            if (sysAdmin.getPassword() != null) {
                SET("password = #{password}");
            }
            if (sysAdmin.getToken() != null) {
                SET("token = #{token}");
            }
            if (sysAdmin.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            if (sysAdmin.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            if (sysAdmin.getIsDeleted() != null) {
                SET("is_deleted = #{isDeleted}");
            }
            if (sysAdmin.getRuleId() != null) {
                SET("rule_id = #{ruleId}");
            }
            if (sysAdmin.getEnable() != null) {
                SET("enable = #{enable}");
            }
            if (sysAdmin.getAvatar() != null) {
                SET("avatar = #{avatar}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }
}