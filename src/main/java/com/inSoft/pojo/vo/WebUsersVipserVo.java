package com.inSoft.pojo.vo;

import com.inSoft.enums.VipserStatusEnum;
import com.inSoft.pojo.WebUsersVipser;
import lombok.Data;

@Data
public class WebUsersVipserVo extends WebUsersVipser {

    private String statusText;

    public WebUsersVipserVo(WebUsersVipser webUsersVipser) {
        super.setId(webUsersVipser.getId());
        super.setUid(webUsersVipser.getUid());
        super.setStatus(webUsersVipser.getStatus());
        super.setTotalDuration(webUsersVipser.getTotalDuration());
        super.setRenewals(webUsersVipser.getRenewals());
        super.setLastRenewalTime(webUsersVipser.getLastRenewalTime());
        super.setEndTime(webUsersVipser.getEndTime());
        super.setStartTime(webUsersVipser.getStartTime());
        super.setIncomeId(webUsersVipser.getIncomeId());
        super.setIncomeText(webUsersVipser.getIncomeText());
        this.statusText = VipserStatusEnum.getByCode(webUsersVipser.getStatus()).getValue();
    }

}
