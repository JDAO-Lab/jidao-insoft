package com.inSoft;

import com.inSoft.pojo.Income;
import com.inSoft.pojo.WebUsersVipser;
import com.inSoft.service.IncomeService;
import com.inSoft.service.WebUsersVipserService;
import com.inSoft.utils.DateUtils;
import com.inSoft.utils.DeBugUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class VipTests {

    @Autowired
    private WebUsersVipserService webUsersVipserService;

    @Autowired
    private IncomeService incomeService;

    @Test
    public void test() {
        openVip(1,14);
    }

    private boolean openVip(int incomeId,int uid){

        //获取当前用户的会员基础信息
        WebUsersVipser webUsersVipser = webUsersVipserService.getByUid(uid);
        DeBugUtils.println("webUsersVipser:",webUsersVipser);
        Income incomeInfo = incomeService.getById(incomeId);
        DeBugUtils.println("incomeInfo:",incomeInfo);
        if (incomeInfo!=null){
        //如果没有则直接更具incomeInfo 创建一条会员信息
        WebUsersVipser newWebUsersVipser = new WebUsersVipser();
        newWebUsersVipser.setUid(uid);
        newWebUsersVipser.setStatus(1);
        newWebUsersVipser.setRenewals(1);
        newWebUsersVipser.setLastRenewalTime(new Date());
        newWebUsersVipser.setTotalDuration(DateUtils.getSecondsByDays(incomeInfo.getDuration()));
        newWebUsersVipser.setIncomeId(incomeId);
            if (webUsersVipser == null && incomeInfo != null){
                //添加一条会员信息
                newWebUsersVipser.setStartTime(new Date());
                newWebUsersVipser.setEndTime(DateUtils.getDateAfterDays(incomeInfo.getDuration()));
                DeBugUtils.println("添加会员信息:",newWebUsersVipser);
                if (webUsersVipserService.save(newWebUsersVipser)){
                    return true;
                }
                return false;
            }else {
                //根据现有的会员信息及incomeInfo信息 直接sql增加现有的过期时间并变更incomeId
                newWebUsersVipser.setId(webUsersVipser.getId());
                newWebUsersVipser.setEndTime(DateUtils.getDateAfterDays(webUsersVipser.getEndTime(),incomeInfo.getDuration()));
                DeBugUtils.println("更新会员信息:",newWebUsersVipser);
                if (webUsersVipserService.update(newWebUsersVipser)){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

}
