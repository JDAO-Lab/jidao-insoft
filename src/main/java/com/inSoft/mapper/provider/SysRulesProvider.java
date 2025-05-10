package com.inSoft.mapper.provider;


import com.inSoft.pojo.SysRules;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class SysRulesProvider {

    // 批量删除
    public String removeBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("sys_rules");
            SET("is_deleted = 1");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    //批量恢复
    public String recoveryBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("sys_rules");
            SET("is_deleted = 0");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    //更新数据
    public String update(SysRules sysRules) {
        return new SQL() {{
            UPDATE("sys_rules");
            if (sysRules.getName() != null) {
                SET("name = #{name}");
            }
            if (sysRules.getRules() != null) {
                SET("rules = #{rules}");
            }
            if (sysRules.getIsDeleted() != null) {
                SET("is_deleted = #{isDeleted}");
            }
            if (sysRules.getDescription() != null){
                SET("description = #{description}");
            }
            if (sysRules.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            if (sysRules.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    public String listByCondition(SysRules sysRules) {
        return new SQL(){{
            SELECT("*");
            FROM("sys_rules");
            if (sysRules.getName() != null ) {
                WHERE("name like CONCAT('%', #{name}, '%')");
            }
            ORDER_BY("id ASC");
        }}.toString();
    }


}
