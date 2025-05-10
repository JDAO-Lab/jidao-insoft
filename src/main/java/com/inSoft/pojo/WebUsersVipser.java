package com.inSoft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户会员状态记录表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebUsersVipser {

    //id
    private Integer id;

    //uid
    private Integer uid;

    //状态
    private Integer status;

    //开始时间
    private Date startTime;

    //到期时间
    private Date endTime;

    //续费累计次数
    private Integer renewals;

    //累计时长 单位秒
    private Integer totalDuration;

    //最近续费日期
    private Date lastRenewalTime;

    //增值服务关联id
    private Integer incomeId;

    //增值服务名称
    private String incomeText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getRenewals() {
        return renewals;
    }

    public void setRenewals(Integer renewals) {
        this.renewals = renewals;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Date getLastRenewalTime() {
        return lastRenewalTime;
    }

    public void setLastRenewalTime(Date lastRenewalTime) {
        this.lastRenewalTime = lastRenewalTime;
    }

    public Integer getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Integer incomeId) {
        this.incomeId = incomeId;
    }

    public String getIncomeText() {
        return incomeText;
    }

    public void setIncomeText(String incomeText) {
        this.incomeText = incomeText;
    }

    @Override
    public String toString() {
        return "WebUsersVipser{" +
                "id=" + id +
                ", uid=" + uid +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", renewals=" + renewals +
                ", totalDuration=" + totalDuration +
                ", lastRenewalTime=" + lastRenewalTime +
                ", incomeId=" + incomeId +
                ", incomeText='" + incomeText + '\'' +
                '}';
    }
}
