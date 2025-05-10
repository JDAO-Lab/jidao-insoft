package com.inSoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TencentCosConfig {
    // 存储桶的名称 附近 所属地区(ap-nanjing)内的英文
    @Value("cloud.tencent.cos.region")
    private String region;
    // 访问管理 访问密钥 api密钥中 创建的 secretId
    @Value("cloud.tencent.cos.secretId")
    private String secretId;
    // 访问管理 访问密钥 api密钥中 创建的 secretKey
    @Value("cloud.tencent.cos.secretKey")
    private String secretKey;
    // 存储桶的名称 需公共读私有写权限
    @Value("cloud.tencent.cos.bucketName")
    private String bucketName;

    public String getSecretId() {
        return secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getRegion() {
        return region;
    }

    public String getBucketName() {
        return bucketName;
    }

}
