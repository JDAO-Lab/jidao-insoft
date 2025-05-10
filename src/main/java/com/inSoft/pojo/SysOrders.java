package com.inSoft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysOrders {

    //订单ID
    private Integer id;
    //订单备注
    private String remarks;
    //订单编号
    private String tradeNo;
    //支付金额
    private Double amount;
    //支付状态 0未支付 1已支付 2已取消
    private Integer status;
    //创建日期
    private Date createdAt;
    //更新日期
    private Date updatedAt;
    //支付时间
    private Date payAt;
    //用户id
    private Integer uid;
    //支付类型
    private Integer payType;
    //平台编号
    private String outTradeNo;
    //购买的服务id
    private Integer incomeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getPayAt() {
        return payAt;
    }

    public void setPayAt(Date payAt) {
        this.payAt = payAt;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Integer incomeId) {
        this.incomeId = incomeId;
    }

    @Override
    public String toString() {
        return "SysOrders{" +
                "id=" + id +
                ", remarks='" + remarks + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", payAt=" + payAt +
                ", uid=" + uid +
                ", payType=" + payType +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", incomeId=" + incomeId +
                '}';
    }
}
