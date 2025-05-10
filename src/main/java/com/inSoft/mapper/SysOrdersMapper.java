package com.inSoft.mapper;

import com.inSoft.mapper.provider.SysOrdersProvider;
import com.inSoft.pojo.SysOrders;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysOrdersMapper {

    //根据条件查询数据
    @SelectProvider(type = SysOrdersProvider.class,method = "listByCondition")
    List<SysOrders> listByCondition(SysOrders sysOrders);

    //获取充值记录
    @SelectProvider(type = SysOrdersProvider.class,method = "getRechargeByUid")
    List<Map<String, Object>> getRechargeByUid(@Param("userId") int userId,@Param("num") int num);

    //保存数据
    @Insert("insert into sys_orders(remarks,trade_no,amount,status,created_at,pay_at,uid,pay_type,out_trade_no,income_id) values(#{remarks},#{tradeNo},#{amount},#{status},#{createdAt},#{payAt},#{uid},#{payType},#{outTradeNo},#{incomeId})")
    boolean save(SysOrders sysOrders);

    //更新数据
    @UpdateProvider(type = SysOrdersProvider.class,method = "update")
    boolean update(SysOrders sysOrders);

    //根据out_trade_no获取订单id
    @Select("select id from sys_orders where out_trade_no=#{outTradeNo} and pay_type=#{payType}")
    Integer getOrderIdByOutTradeNo(@Param("outTradeNo") String outTradeNo,@Param("payType") int payType);

    //检查状态
    @Select("select count(id) from sys_orders where out_trade_no=#{outTradeNo} and pay_type=#{payType} and status=1")
    boolean checkIsPay(@Param("outTradeNo") String outTradeNo,@Param("payType") int payType);

    @Select("select id,remarks,trade_no as tradeNo,amount,status,uid,pay_type as payType,out_trade_no as outTradeNo,income_id as incomeId from sys_orders where out_trade_no=#{outTradeNo} and pay_type=#{payType}  and status=0")
    SysOrders getOrderByOutTradeNo(@Param("outTradeNo") String outTradeNo,@Param("payType") int payType);

    //直接更新状态
    @Update("update sys_orders set status=#{status} where id=#{id}")
    void upStatusById(Integer id, int status);

}
