package com.inSoft.mapper.provider;

import com.inSoft.pojo.AppVersion;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class AppVersionProvider {
    
    // 条件查询
    public String listByCondition(AppVersion appVersion) {
        return new SQL(){{
            SELECT("*"); // 多条件查询
            FROM("app_version");
            if (appVersion.getTitle() != null ) {
                WHERE("title like CONCAT('%', #{title}, '%')");
            }
            if (appVersion.getCid()!=null){
                WHERE("cid = #{cid}");
            }
            if (appVersion.getVersion() != null && appVersion.getVersion().length() > 0){
                WHERE("version like CONCAT('%', #{version}, '%')");
            }
            ORDER_BY("updated_at DESC");
        }}.toString();
    }

    // 更新数据
    public String update(AppVersion appVersion) {
        return new SQL(){{
            UPDATE("app_version");
            if (appVersion.getTitle() != null) {
                SET("title = #{title}");
            }
            if (appVersion.getDownUrl() != null) {
                SET("down_url = #{downUrl}");
            }
            if (appVersion.getDownPwd() != null) {
                SET("down_pwd = #{downPwd}");
            }
            if (appVersion.getVersion() != null) {
                SET("version = #{version}");
            }
            if (appVersion.getUpLog() != null) {
                SET("up_log = #{upLog}");
            }
            if (appVersion.getDownType() != null) {
                SET("down_type = #{downType}");
            }
            if (appVersion.getCid() != null) {
                SET("cid = #{cid}");
            }
            if (appVersion.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            if (appVersion.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    //批量删除
    public String removeBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("app_version");
            SET("is_deleted = 1");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    //批量恢复
    public String recoveryBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("app_version");
            SET("is_deleted = 0");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }
    
}
