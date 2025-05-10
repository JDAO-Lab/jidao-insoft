package com.inSoft.mapper;


import com.inSoft.mapper.provider.OpinionProvider;
import com.inSoft.pojo.Opinion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OpinionMapper {

    //根据条件查询数据
    @SelectProvider(type = OpinionProvider.class, method = "listByCondition")
    List<Opinion> listByCondition(Opinion opinion);

    //更新
    @UpdateProvider(type = OpinionProvider.class,method = "update")
    boolean update(Opinion opinion);

    //根据id获取数据
    @Select("select * from web_opinion where id=#{id}")
    Opinion getById(@Param("id") Integer id);

    //批量删除
    @UpdateProvider(type = OpinionProvider.class,method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

    //保存
    @Insert("INSERT INTO web_opinion (title, content, created_at, is_deleted, url, ip) VALUES (#{title}, #{content}, #{createdAt}, #{isDeleted}, #{url}, #{ip})")
    boolean save(Opinion opinion);
}
