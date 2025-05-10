package com.inSoft.mapper.provider;

import com.inSoft.pojo.SysFile;
import org.apache.ibatis.jdbc.SQL;

// SysFileProvider.java
public class SysFileProvider {
    public String update(SysFile sysFile) {
        return new SQL() {{
            UPDATE("sys_file");
            if (sysFile.getFileName() != null) {
                SET("file_name = #{fileName}");
            }
            if (sysFile.getFilePath() != null) {
                SET("file_path = #{filePath}");
            }
            if (sysFile.getFileMd5() != null) {
                SET("file_md5 = #{fileMd5}");
            }
            if (sysFile.getCreatedAt() != null) {
                SET("created_at = #{createdAt}");
            }
            if (sysFile.getUpdatedAt() != null) {
                SET("updated_at = #{updatedAt}");
            }
            if (sysFile.getIp() != null) {
                SET("ip = #{ip}");
            }
            if (sysFile.getSize() != null) {
                SET("size = #{size}");
            }
            if (sysFile.getExt() != null) {
                SET("ext = #{ext}");
            }
            if (sysFile.getLocal() != null) {
                SET("local = #{local}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }
}