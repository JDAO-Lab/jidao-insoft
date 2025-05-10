package com.inSoft.mapper;

import com.inSoft.mapper.provider.WebNavCatProvider;
import com.inSoft.pojo.WebNavCat;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WebNavCatMapper {

    //根据条件查询
    @SelectProvider(type = WebNavCatProvider.class, method = "listByCondition")
    List<WebNavCat> listByCondition(WebNavCat webNavCat);

    //保存数据
    @Insert("insert into web_nav_cat(name,description,created_at) values(#{name},#{description},#{createdAt})")
    boolean save(WebNavCat webNavCat);

    //根据id获取数据
    @Select("select * from web_nav_cat where id=#{id}")
    WebNavCat getById(Integer id);

    //更新数据
    @UpdateProvider(type = WebNavCatProvider.class, method = "update")
    boolean update(WebNavCat webNavCat);

    //删除
    @Update("update web_nav_cat set is_deleted=1 where id=#{id}")
    boolean remove(Integer id);

    //批量删除
    @UpdateProvider(type = WebNavCatProvider.class, method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

    //恢复
    @Update("update web_nav_cat set is_deleted=0 where id=#{id}")
    boolean recovery(Integer id);

    //批量恢复
    @UpdateProvider(type = WebNavCatProvider.class, method = "recoveryBatch")
    boolean recoveryBatch(List<Integer> ids);

    //select数据
    @Select("select * from web_nav_cat where is_deleted=0 order by id ASC")
    List<WebNavCat> getSelect();

    //获取一个id排名首位的id
    @Select("select id from web_nav_cat where is_deleted=0 order by id ASC limit 1")
    Integer getFirstId();

}
