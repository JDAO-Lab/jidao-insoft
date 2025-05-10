package com.inSoft.mapper.provider;

import com.inSoft.pojo.WebNav;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class WebNavProvider {

    //根据条件查询数据
    public String listByCondition(WebNav webNav)
    {
        return new SQL(){{
            SELECT("*"); // 多条件查询
            FROM("web_nav");
            if (webNav.getCid()!=null){
                WHERE("cid = #{cid}");
            }
            ORDER_BY("sort asc");
        }}.toString();
    }

    //统计ids的pid数据
    public String countByPids(List<Integer> ids)
    {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            SELECT("count(id)");
            FROM("web_nav");
            WHERE("pid IN (" + idStr + ")");
        }}.toString();
    }

    //批量删除
    public String removeBatch(List<Integer> ids)
    {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            DELETE_FROM("web_nav");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    //更新
    public String update(WebNav webNav)
    {
        return new SQL() {{
            UPDATE("web_nav");
            if (webNav.getId()!=null){
                SET("id = #{id}");
            }
            if (webNav.getName()!=null){
                SET("name = #{name}");
            }
            if (webNav.getKeywords()!=null){
                SET("keywords = #{keywords}");
            }
            if (webNav.getDescription()!=null){
                SET("description = #{description}");
            }
            if (webNav.getContent()!=null){
                SET("content = #{content}");
            }
            if (webNav.getEnable()!=null){
                SET("enable = #{enable}");
            }
            if (webNav.getPath()!=null){
                SET("path = #{path}");
            }
            if (webNav.getPid()!=null){
                SET("pid = #{pid}");
            }
            if (webNav.getSort()!=null){
                SET("sort = #{sort}");
            }
            if (webNav.getType()!=null){
                SET("type = #{type}");
            }
            if (webNav.getCreatedAt()!=null){
                SET("created_at = #{createdAt}");
            }
            if (webNav.getUpdatedAt()!=null){
                SET("updated_at = #{updatedAt}");
            }
            if (webNav.getCid()!=null){
                SET("cid = #{cid}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

}
