package com.inSoft.service;

import com.inSoft.pojo.SysOrders;

import java.util.List;
import java.util.Map;

public interface SysOrdersService {

    //根据条件查询数据
    List<SysOrders> listByCondition(SysOrders sysOrders);

    //获取充值记录
    List<Map<String, Object>> getRechargeByUid(int userId, int num);

    //保存
    boolean save(SysOrders sysOrders);

    //更新
    boolean update(SysOrders sysOrders);

    Integer getOrderIdByOutTradeNo(String outTradeNo, int payType);

    boolean checkIsPay(String outTradeNo, int payType);

    SysOrders getOrderByOutTradeNo(String outTradeNo, int payType);

    void upStatusById(Integer id, int status);

}
