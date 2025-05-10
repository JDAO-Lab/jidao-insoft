package com.inSoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfig {
    //当前系统环境配置
    @Value("${upload.setting.default_os}")
    private String default_os;
    @Value("${upload.setting.default_local}")
    //默认上传配置 本地local 阿里云oss alioss 腾讯cos tencos
    private String default_local;
    //文件服务其域名，所有上传类型都需要配置这里
    @Value("${upload.setting.default_local_domain}")
    private String default_local_domain;
    //默认文件上传大小，超过即提示 单位MB
    @Value("${upload.setting.default_max_size}")
    private Integer default_max_size;
    //默认限制文件类型
    @Value("${upload.setting.default_limit_suffix}")
    private String default_limit_suffix;
    //默认win系统下存储地址
    @Value("${upload.setting.default_win_path}")
    private String default_win_path;
    //默认linux系统下存储地址
    @Value("${upload.setting.default_linux_path}")
    private String default_linux_path;

    public String getDefaultLocalDomain() {
        return default_local_domain;
    }

    public String getDefaultOs() {
        return default_os;
    }

    public String getDefaultLocal() {
        return default_local;
    }

    public Integer getDefaultMaxSize() {
        return default_max_size;
    }

    public String getDefaultLimitSuffix() {
        return default_limit_suffix;
    }

    public String getDefaultWinPath() {
        return default_win_path;
    }

    public String getDefaultLinuxPath() {
        return default_linux_path;
    }

}
