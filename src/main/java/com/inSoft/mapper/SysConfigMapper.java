package com.inSoft.mapper;

import com.inSoft.pojo.SysConfig;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface SysConfigMapper {

    @Select("select * from sys_config")
     List<SysConfig> getByAll();

    @Select("select value from sys_config where name=#{name}")
     String getConfigByName(String name);

    @Update("update sys_config set value=#{value} where name=#{name}")
     boolean updateConfigValueByName(@Param("name") String name,@Param("value") String value);

}
