package com.inSoft.mapper.provider;

import com.inSoft.pojo.SysStatic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDateTime;

public class SysStaticProvider {


    /**
     * 更具统计项、日期进行检索一个范围的综合数据信息
     * @param type
     * @param startDate
     * @param endDate
     * @return
     */
    public String listByCondition(@Param("type") Integer type,@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate) {
        return new SQL() {{
            SELECT("*");
            FROM("sys_static");

            if (type != null) {
                WHERE("type = #{type}");
            }

            if (startDate != null) {
                if (endDate == null) {
                    WHERE("created_at >= #{startDate}");
                } else {
                    WHERE("created_at BETWEEN #{startDate} AND #{endDate}");
                }
            } else if (endDate != null) {
                WHERE("created_at <= #{endDate}");
            }
            ORDER_BY("updated_at DESC");
        }}.toString();
    }

    public String update(SysStatic sysStatic) {
        return new SQL() {{
            UPDATE("sys_static");
            if (sysStatic.getName()!=null){
                SET("name = #{name}");
            }
            if (sysStatic.getValue()!=0){
                SET("value = value + #{value}");
            }
            if (sysStatic.getType()!=null){
                SET("type = #{type}");
            }
            if (sysStatic.getCreatedAt()!=null){
                SET("created_at = #{createdAt}");
            }
            if (sysStatic.getUpdatedAt()!=null){
                SET("updated_at = #{updatedAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

}
