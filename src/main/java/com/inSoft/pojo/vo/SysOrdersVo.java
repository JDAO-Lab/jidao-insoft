package com.inSoft.pojo.vo;

import com.inSoft.enums.OrderStatusEnum;
import com.inSoft.enums.PayTypeEnum;
import com.inSoft.pojo.SysOrders;
import lombok.Data;

@Data
public class SysOrdersVo extends SysOrders {
    //订单状态
    private String statusText;
    //支付方式
    private String payTypeText;

    public SysOrdersVo(SysOrders sysOrders){
        super.setId(sysOrders.getId());
        super.setAmount(sysOrders.getAmount());
        super.setCreatedAt(sysOrders.getCreatedAt());
        super.setIncomeId(sysOrders.getIncomeId());
        super.setOutTradeNo(sysOrders.getOutTradeNo());
        super.setPayAt(sysOrders.getPayAt());
        super.setPayType(sysOrders.getPayType());
        super.setStatus(sysOrders.getStatus());
        super.setTradeNo(sysOrders.getTradeNo());
        super.setUpdatedAt(sysOrders.getUpdatedAt());
        super.setUid(sysOrders.getUid());
        super.setRemarks(sysOrders.getRemarks());
        this.statusText = OrderStatusEnum.getByCode(sysOrders.getStatus()).getValue();
        this.payTypeText = PayTypeEnum.getByCode(sysOrders.getPayType()).getValue();
    }

}
