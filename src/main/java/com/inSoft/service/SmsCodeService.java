package com.inSoft.service;

import com.inSoft.pojo.SmsCode;

public interface SmsCodeService {

    //保存
    boolean save(SmsCode smsCode);

    //限制发送
    boolean checkLimit(String phone, Integer type);

    //验证短信
    boolean checkCode(String phone, String code,Integer type);

    //根据手机号、验证码和类型 修改status状态
    boolean usedCode(String phone, String code,Integer type);
}
