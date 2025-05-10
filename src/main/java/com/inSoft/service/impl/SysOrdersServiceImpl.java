package com.inSoft.service.impl;

import com.inSoft.mapper.SysOrdersMapper;
import com.inSoft.pojo.SysOrders;
import com.inSoft.service.SysOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysOrdersServiceImpl implements SysOrdersService {

    @Autowired
    private SysOrdersMapper sysOrdersMapper;

    //根据条件查询数据
    public List<SysOrders> listByCondition(SysOrders sysOrders) {
        return sysOrdersMapper.listByCondition(sysOrders);
    }

    @Override
    public List<Map<String, Object>> getRechargeByUid(int userId, int num) {
        return sysOrdersMapper.getRechargeByUid(userId,num);
    }

    @Override
    public boolean save(SysOrders sysOrders) {
        return sysOrdersMapper.save(sysOrders);
    }

    @Override
    public boolean update(SysOrders sysOrders) {
        return sysOrdersMapper.update(sysOrders);
    }

    @Override
    public Integer getOrderIdByOutTradeNo(String outTradeNo, int payType) {
        return sysOrdersMapper.getOrderIdByOutTradeNo(outTradeNo,payType);
    }

    @Override
    public boolean checkIsPay(String outTradeNo, int payType) {
        return sysOrdersMapper.checkIsPay(outTradeNo,payType);
    }

    @Override
    public SysOrders getOrderByOutTradeNo(String outTradeNo, int payType) {
        return sysOrdersMapper.getOrderByOutTradeNo(outTradeNo,payType);
    }

    @Override
    public void upStatusById(Integer id, int status) {
        sysOrdersMapper.upStatusById(id,status);
    }

}
