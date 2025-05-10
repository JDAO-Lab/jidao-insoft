package com.inSoft.mapper;

import com.inSoft.mapper.provider.AppCatProvider;
import com.inSoft.pojo.AppCat;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AppCatMapper {

    //根据条件查询
    @SelectProvider(type = AppCatProvider.class, method = "listByCondition")
    List<AppCat> listByCondition(AppCat appCat);

    //保存数据
    @Insert("insert into app_cat(name,description,created_at) values(#{name},#{description},#{createdAt})")
    boolean save(AppCat appCat);

    //根据id获取数据
    @Select("select * from app_cat where id=#{id}")
    AppCat getById(Integer id);

    //更新数据
    @UpdateProvider(type = AppCatProvider.class, method = "update")
    boolean update(AppCat appCat);

    //删除
    @Update("update app_cat set is_deleted=1 where id=#{id}")
    boolean remove(Integer id);

    //批量删除
    @UpdateProvider(type = AppCatProvider.class, method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

    //恢复
    @Update("update app_cat set is_deleted=0 where id=#{id}")
    boolean recovery(Integer id);

    //批量恢复
    @UpdateProvider(type = AppCatProvider.class, method = "recoveryBatch")
    boolean recoveryBatch(List<Integer> ids);

    //select数据
    @Select("select * from app_cat where is_deleted=0 order by id ASC")
    List<AppCat> getSelect();

    //获取一个id排名首位的id
    @Select("select id from app_cat where is_deleted=0 order by id ASC limit 1")
    Integer getFirstId();

}
