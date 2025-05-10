package com.inSoft.mapper.provider;

import com.inSoft.pojo.SysOrders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SysOrdersProvider {
    //根据条件查询数据
    public String listByCondition(SysOrders sysOrders) {
        return new SQL(){{
            SELECT("*");
            FROM("sys_orders");
            if (sysOrders.getRemarks() != null) {
                WHERE("remarks like  CONCAT('%', #{remarks}, '%')");
            }
            if (sysOrders.getOutTradeNo() != null ) {
                WHERE("out_trade_no like CONCAT('%', #{outTradeNo}, '%')");
            }
            if (sysOrders.getTradeNo() != null) {
                WHERE("trade_no like CONCAT('%', #{tradeNo}, '%')");
            }
            ORDER_BY("id DESC");
        }}.toString();
    }

    public String getRechargeByUid(@Param("userId") int userId,@Param("num") int num) {
        return new SQL(){{
           // 多表查询
            SELECT("o.id,o.pay_at as payAt,o.pay_type as payType,o.amount,i.name as incomeName,i.duration as incomeDuration");
            FROM("sys_orders o");
            LEFT_OUTER_JOIN("web_income i ON o.income_id = i.id");
            WHERE("o.uid = #{userId} and o.status=1");
            ORDER_BY("o.id DESC");
            LIMIT(num);
        }}.toString();
    }

    //更新 remarks,trade_no,amount,status,created_at,pay_at,uid,pay_type,out_trade_no,income_id
    public String update(SysOrders sysOrders) {
        return new SQL(){{
            UPDATE("sys_orders");
            if (sysOrders.getRemarks()!=null){
                SET("remarks=#{remarks}");
            }
            if (sysOrders.getTradeNo()!=null){
                SET("trade_no=#{tradeNo}");
            }
            if (sysOrders.getAmount()!=null){
                SET("amount=#{amount}");
            }
            if (sysOrders.getStatus()!=null){
                SET("status=#{status}");
            }
            if (sysOrders.getPayAt()!=null){
                SET("pay_at=#{payAt}");
            }
            if (sysOrders.getUid()!=null){
                SET("uid=#{uid}");
            }
            if (sysOrders.getPayType()!=null){
                SET("pay_type=#{payType}");
            }
            if (sysOrders.getOutTradeNo()!=null){
                SET("out_trade_no=#{outTradeNo}");
            }
            if (sysOrders.getIncomeId()!=null){
                SET("income_id=#{incomeId}");
            }
            if (sysOrders.getUpdatedAt()!=null){
                SET("updated_at=now()");
            }
           WHERE("id=#{id}");
        }}.toString();
    }

}
