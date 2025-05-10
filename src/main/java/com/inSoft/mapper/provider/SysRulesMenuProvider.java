package com.inSoft.mapper.provider;

import com.inSoft.pojo.SysRulesMenu;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class SysRulesMenuProvider {

    //更新数据
    public String update(SysRulesMenu sysRulesMenu) {
        return new SQL() {{
            UPDATE("sys_rules_menu");
            if (sysRulesMenu.getTitle() != null) {
                SET("title = #{title}");
            }
            if (sysRulesMenu.getHref() != null) {
                SET("href = #{href}");
            }
            if (sysRulesMenu.getPid() != null) {
                SET("pid = #{pid}");
            }
            if (sysRulesMenu.getDescription() != null) {
                SET("description = #{description}");
            }
            if (sysRulesMenu.getIcon() != null) {
                SET("icon = #{icon}");
            }
            if (sysRulesMenu.getTarget() != null) {
                SET("target = #{target}");
            }
            if (sysRulesMenu.getHide() != null) {
                SET("hide = #{hide}");
            }
            if (sysRulesMenu.getAllow() != null) {
                SET("allow = #{allow}");
            }
            if (sysRulesMenu.getSort() != null) {
                SET("sort = #{sort}");
            }
            if (sysRulesMenu.getIsDeleted() != null) {
                SET("is_deleted = #{isDeleted}");
            }
            if (sysRulesMenu.getEnable() != null) {
                SET("enable = #{enable}");
            }
            if (sysRulesMenu.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            if (sysRulesMenu.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    //统计所有子级数
    public String getSonCountByPids(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            SELECT("count(id)");
            FROM("sys_rules_menu");
            WHERE("pid IN (" + idStr + ") AND is_deleted = 0");
        }}.toString();
    }

    // 批量删除
    public String removeBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("sys_rules_menu");
            SET("is_deleted = 1");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

}
