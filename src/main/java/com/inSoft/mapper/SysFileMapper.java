package com.inSoft.mapper;

import com.inSoft.mapper.provider.SysFileProvider;
import com.inSoft.pojo.SysFile;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysFileMapper {

    @Insert("insert into sys_file (file_name, file_path, file_md5, created_at, updated_at, ip, size, ext, local) values(#{fileName}, #{filePath}, #{fileMd5}, #{createdAt}, #{updatedAt}, #{ip}, #{size}, #{ext}, #{local})")
    void add(SysFile sysFile);

    @UpdateProvider(type = SysFileProvider.class, method = "update")
    void update(SysFile sysFile);

    @Select("select file_path from sys_file where file_md5 = #{fileMd5}")
    String getFilePathByMd5(String fileMd5);

}