package com.inSoft.mapper;


import com.inSoft.mapper.provider.AppVersionProvider;
import com.inSoft.pojo.AppVersion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AppVersionMapper {

    //根据条件查询
    @SelectProvider(type = AppVersionProvider.class, method = "listByCondition")
    List<AppVersion> listByCondition(AppVersion appVersion);

    //保存数据
    @Insert("INSERT INTO app_version (version, title, up_log, down_url, down_pwd, down_type, cid, created_at) VALUES (#{version}, #{title}, #{upLog}, #{downUrl}, #{downPwd}, #{downType}, #{cid}, #{createdAt})")
    boolean save(AppVersion appVersion);

    //根据id获取数据
    @Select("select * from app_version where id=#{id}")
    AppVersion getById(Integer id);

    //更新数据
    @UpdateProvider(type = AppVersionProvider.class, method = "update")
    boolean update(AppVersion appVersion);

    //删除
    @Update("update app_version set is_deleted=1 where id=#{id}")
    boolean remove(Integer id);

    //批量删除
    @UpdateProvider(type = AppVersionProvider.class, method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

    //恢复
    @Update("update app_version set is_deleted=0 where id=#{id}")
    boolean recovery(Integer id);

    //批量恢复
    @UpdateProvider(type = AppVersionProvider.class, method = "recoveryBatch")
    boolean recoveryBatch(List<Integer> ids);

    //获取指定客户端下版本信息
    @Select("select * from app_version where cid=#{appCatId} and is_deleted=0 order by id desc limit #{num}")
    List<AppVersion> getListByCid(@Param("appCatId") Integer appCatId,@Param("num") int num);
}
