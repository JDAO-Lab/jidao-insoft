package com.inSoft.mapper;

import com.inSoft.mapper.provider.SysLogProvider;
import com.inSoft.pojo.SysLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysLogMapper {

    @Insert("insert into sys_log (ip, path, remarks, created_at, result, method) values(#{ip}, #{path}, #{remarks}, #{createdAt}, #{result}, #{method})")
    void add(SysLog sysLog);

    @Select("SELECT * FROM sys_log")
    List<SysLog> list();

    @SelectProvider(type = SysLogProvider.class, method = "listByCondition")
    List<SysLog> listByCondition(SysLog sysLog);

    @Select("SELECT * FROM sys_log WHERE ip=#{ip} ORDER BY created_at DESC LIMIT #{limit}")
    List<SysLog> listByIpAndLimit(@Param("ip") String ip, @Param("limit") int limit);
}