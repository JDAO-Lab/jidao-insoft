package com.inSoft.mapper;

import com.inSoft.mapper.provider.SysStaticProvider;
import com.inSoft.pojo.SysStatic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface SysStaticMapper {

    @Insert("insert into sys_static (name, value, type, created_at) values(#{name}, #{value}, #{type}, #{createdAt})")
    boolean save(SysStatic sysStatic);

    @UpdateProvider(type = SysStaticProvider.class, method = "update")
    boolean update(SysStatic sysStatic);

    @Select("SELECT IFNULL((SELECT id FROM sys_static WHERE type = #{type} AND created_at BETWEEN #{startDate} AND #{endDate}), 0)")
    int searchByNameAndTypeInToady(@Param("type") Integer type, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Select("SELECT IFNULL((SELECT sum(value) FROM sys_static WHERE type = #{type} AND created_at BETWEEN #{startDate} AND #{endDate}),0)")
    double getTypeValueByDateRange(@Param("type") Integer type,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Select("SELECT IFNULL((SELECT sum(value) FROM sys_static WHERE type = #{type}),0)")
    double sumTypeValue(Integer type);

    @SelectProvider(type = SysStaticProvider.class, method = "listByCondition")
    List<SysStatic> listByCondition(@Param("type") Integer type,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
