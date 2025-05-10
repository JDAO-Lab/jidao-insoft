package com.inSoft.service.impl;

import com.inSoft.config.SmsConfig;
import com.inSoft.mapper.SmsCodeMapper;
import com.inSoft.pojo.SmsCode;
import com.inSoft.service.SmsCodeService;
import com.inSoft.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Slf4j
@Service
public class SmsCodeServiceImpl implements SmsCodeService {

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    @Autowired
    private SmsConfig smsConfig;

    @Override
    public boolean save(SmsCode smsCode) {
        return smsCodeMapper.save(smsCode);
    }

    @Override
    public boolean checkLimit(String phone, Integer type) {
        //在一定时间限制发送的条数
        Integer limitCount = smsConfig.getLimitCount();
        //在此时间单位秒内发送的短信
        Long limitTime = smsConfig.getLimitTime();
        //减去限制的时间
        String limitDateTime = DateUtils.getDateBySeconds( -limitTime, "yyyy-MM-dd HH:mm:ss");
        //检索当前的数量
        Integer nowCount = smsCodeMapper.checkLimit(phone, type, limitDateTime);
        return nowCount >= limitCount;
    }

    @Override
    public boolean checkCode(String phone, String code,Integer type) {
        //根据配置文件有效期查询 当前 手机号、type及code的短信记录
        Long expireTime = smsConfig.getExpireTime(); //多少秒之前的
        String limitDateTime = DateUtils.getDateBySeconds( -expireTime, "yyyy-MM-dd HH:mm:ss");
        //查询是否存在此条内容 如果存在则返回true
        return smsCodeMapper.checkCode(phone, code, type, limitDateTime);
    }

    @Override
    public boolean usedCode(String phone, String code, Integer type) {
        return smsCodeMapper.usedCode(phone, code, type);
    }
}
