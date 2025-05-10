package com.inSoft.pojo.vo;

import com.inSoft.enums.SmsTypeEnum;
import com.inSoft.pojo.SmsCode;
import lombok.Data;

@Data
public class SmsCodeVo extends SmsCode{

    private String typeText;

    public SmsCodeVo(SmsCode smsCode){
        super.setId(smsCode.getId());
        super.setPhone(smsCode.getPhone());
        super.setCode(smsCode.getCode());
        super.setSendTime(smsCode.getSendTime());
        super.setIp(smsCode.getIp());
        super.setType(smsCode.getType());
        this.typeText = SmsTypeEnum.getByCode(smsCode.getType()).getValue();
    }

}
