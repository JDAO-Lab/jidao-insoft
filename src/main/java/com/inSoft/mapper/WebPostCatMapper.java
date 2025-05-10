package com.inSoft.mapper;

import com.inSoft.mapper.provider.WebPostCatProvider;
import com.inSoft.pojo.WebPostCat;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WebPostCatMapper {

    //根据条件查询
    @SelectProvider(type = WebPostCatProvider.class, method = "listByCondition")
    List<WebPostCat> listByCondition(WebPostCat webPostCat);

    //保存数据
    @Insert("insert into web_post_cat(name,description,created_at) values(#{name},#{description},#{createdAt})")
    boolean save(WebPostCat webPostCat);

    //根据id获取数据
    @Select("select * from web_post_cat where id=#{id}")
    WebPostCat getById(Integer id);

    //更新数据
    @UpdateProvider(type = WebPostCatProvider.class, method = "update")
    boolean update(WebPostCat webPostCat);

    //删除
    @Update("update web_post_cat set is_deleted=1 where id=#{id}")
    boolean remove(Integer id);

    //批量删除
    @UpdateProvider(type = WebPostCatProvider.class, method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

    //恢复
    @Update("update web_post_cat set is_deleted=0 where id=#{id}")
    boolean recovery(Integer id);

    //批量恢复
    @UpdateProvider(type = WebPostCatProvider.class, method = "recoveryBatch")
    boolean recoveryBatch(List<Integer> ids);

    //select数据
    @Select("select * from web_post_cat where is_deleted=0 order by id ASC")
    List<WebPostCat> getSelect();
    
}
