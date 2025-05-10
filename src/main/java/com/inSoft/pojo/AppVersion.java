package com.inSoft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 版本信息管理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppVersion {

    //id
    private Integer id;
    //版本号
    private String version;
    //标题
    private String title;
    //日志 up_log
    private String upLog;
    //下载地址 down_url
    private String downUrl;
    //提取码 down_pwd
    private String downPwd;
    //下载方式 down_type 0本地OSS 1阿里网盘 2蓝奏云 3小鸟云 4百度网盘
    private Integer downType;
    //客户端类型
    private Integer cid;
    //创建时间
    private Date createdAt;
    //更新时间
    private Date updatedAt;
    //是否删除
    private Integer isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpLog() {
        return upLog;
    }

    public void setUpLog(String upLog) {
        this.upLog = upLog;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getDownPwd() {
        return downPwd;
    }

    public void setDownPwd(String downPwd) {
        this.downPwd = downPwd;
    }

    public Integer getDownType() {
        return downType;
    }

    public void setDownType(Integer downType) {
        this.downType = downType;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", title='" + title + '\'' +
                ", upLog='" + upLog + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", downPwd='" + downPwd + '\'' +
                ", downType=" + downType +
                ", cid=" + cid +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
