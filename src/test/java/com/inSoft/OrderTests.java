package com.inSoft;

import com.inSoft.pojo.SysOrders;
import com.inSoft.service.SysOrdersService;
import com.inSoft.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class OrderTests {

    @Autowired
    private SysOrdersService sysOrdersService;

    //添加指定用户的订单数据
    @Test
    public void addOrder(){

        //循环添加是15条
        for (int i = 0; i < 15; i++) {
            SysOrders sysOrders = new SysOrders();
            sysOrders.setUid(14);
            sysOrders.setAmount(100.0);
            sysOrders.setPayType(1);
            sysOrders.setStatus(1);
            sysOrders.setTradeNo("123456789");
            sysOrders.setRemarks("测试数据");
            sysOrders.setCreatedAt(new Date());
            sysOrders.setUpdatedAt(new Date());
            sysOrders.setOutTradeNo("123456789");
            sysOrders.setPayAt(new Date());
            sysOrders.setIncomeId(3);
            sysOrdersService.save(sysOrders);
        }

    }


    @Test
    public void getOutTradeNo(){
        System.out.println(StringUtils.getOutTradeNo());
    }

}
