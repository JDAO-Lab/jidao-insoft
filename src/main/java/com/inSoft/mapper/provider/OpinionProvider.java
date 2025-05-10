package com.inSoft.mapper.provider;

import com.inSoft.pojo.Opinion;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class OpinionProvider {

    //批量删除
    public String removeBatch(List<Integer> ids)
    {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("web_opinion");
            SET("is_deleted = 1");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    //更新
    public String update(Opinion opinion)
    {
        return new SQL(){{
            UPDATE("web_opinion");
            if (opinion.getTitle()!=null){
                SET("title=#{title}");
            }
            if (opinion.getContent()!=null){
                SET("content=#{content}");
            }
            if (opinion.getUrl()!=null){
                SET("url=#{url}");
            }
            if (opinion.getIsDeleted()!=null){
                SET("is_deleted=#{isDeleted}");
            }
            if (opinion.getIp()!=null){
                SET("ip=#{ip}");
            }
            if (opinion.getUpdatedAt()!=null){
                SET("updated_at=#{updatedAt}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }


    //条件查询
    public String listByCondition(Opinion opinion)
    {
        return new SQL(){{
            SELECT("*"); // 多条件查询
            FROM("web_opinion");
            if (opinion.getTitle()!=null){
                WHERE("title like CONCAT('%', #{title}, '%')");
            }
            if (opinion.getContent()!=null){
                WHERE("content like CONCAT('%', #{content}, '%')");
            }
            WHERE("is_deleted=0");
            ORDER_BY("id DESC");
        }}.toString();
    }

}
