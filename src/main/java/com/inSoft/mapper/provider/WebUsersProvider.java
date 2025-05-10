package com.inSoft.mapper.provider;

import com.inSoft.pojo.WebUsers;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class WebUsersProvider {

    //批量禁用
    public String disableBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("web_users");
            SET("enable=0");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    //批量启用
    public String enableBatch(List<Integer> ids) {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("web_users");
            SET("enable=1");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    // 条件查询
    public String listByCondition(WebUsers webUsers) {
        return new SQL(){{
            SELECT("*"); // 多条件查询
            FROM("web_users");
            if (webUsers.getUsername() != null ) {
                WHERE("username like CONCAT('%', #{username}, '%')");
            }
            if (webUsers.getNickname() != null ) {
                WHERE("nickname like CONCAT('%', #{nickname}, '%')");
            }
            if (webUsers.getPhone() != null ) {
                WHERE("phone like CONCAT('%', #{phone}, '%')");
            }
            if (webUsers.getEmail() != null ) {
                WHERE("email like CONCAT('%', #{email}, '%')");
            }
            ORDER_BY("login_at DESC");
        }}.toString();
    }

    //按条件更新数据
    public String update(WebUsers webUsers) {
        return new SQL() {{
            UPDATE("web_users");
            if (webUsers.getUsername() != null) {
                SET("username = #{username}");
            }
            if (webUsers.getPassword() != null) {
                SET("password = #{password}");
            }
            if (webUsers.getNickname() != null) {
                SET("nickname = #{nickname}");
            }
            if (webUsers.getEmail() != null) {
                SET("email = #{email}");
            }
            if (webUsers.getPhone() != null) {
                SET("phone = #{phone}");
            }
            if (webUsers.getAvatar() != null) {
                SET("avatar = #{avatar}");
            }
            if (webUsers.getSex() != null) {
                SET("sex = #{sex}");
            }
            if (webUsers.getIp() != null) {
                SET("ip = #{ip}");
            }
            // 注意：createdAt, updatedAt, loginAt 通常不由客户端直接更新，故省略
            if (webUsers.getEnable() != null) {
                SET("enable = #{enable}");
            }
            if (webUsers.getLoginCount() != null) {
                SET("login_count = #{loginCount}");
            }
            if (webUsers.getAddress() != null) {
                SET("address = #{address}");
            }
            if (webUsers.getToken() != null) {
                SET("token = #{token}");
            }
            if (webUsers.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            if (webUsers.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            //更新登录时间
            if (webUsers.getLoginAt() != null) {
                SET("login_at = #{loginAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }
}
