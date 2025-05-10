package com.inSoft.mapper.provider;

import com.inSoft.pojo.SysLog;
import org.apache.ibatis.jdbc.SQL;

public class SysLogProvider {


    /**
     * 查询日志数据 条件查询
     * @param sysLog
     * @return
     */
    public String listByCondition(SysLog sysLog) {
        return new SQL(){{
            SELECT("*");
            FROM("sys_log");
            if (sysLog.getRemarks() != null) {
                WHERE("remarks like  CONCAT('%', #{remarks}, '%')");
            }
            if (sysLog.getIp() != null ) {
                WHERE("ip like CONCAT('%', #{ip}, '%')");
            }
            if (sysLog.getPath() != null ) {
                WHERE("path like CONCAT('%', #{path}, '%')");
            }
            ORDER_BY("created_at DESC");
        }}.toString();
    }
}
