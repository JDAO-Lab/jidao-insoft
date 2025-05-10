package com.inSoft.mapper;

import com.inSoft.pojo.SmsCode;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface SmsCodeMapper {

    //保存
    @Insert("INSERT INTO sms_code (phone, code, send_time, ip, type) VALUES (#{phone}, #{code}, #{sendTime}, #{ip}, #{type})")
    boolean save(SmsCode smsCode);

    //查询 某个时间后的数量
    @Select("SELECT COUNT(id) FROM sms_code WHERE phone = #{phone} AND type = #{type} AND send_time >= #{limitDateTime}")
    Integer checkLimit(@Param("phone") String phone,@Param("type") Integer type,@Param("limitDateTime") String limitDateTime);

    //查询 某个手机号、类型的验证码还未使用
    @Select("SELECT count(id) FROM sms_code WHERE phone = #{phone} AND type = #{type} AND code = #{code} AND send_time >= #{limitDateTime} AND status=0")
    boolean checkCode(@Param("phone") String phone,@Param("code") String code,@Param("type") Integer type,@Param("limitDateTime") String limitDateTime);

    //更新为已使用
    @Update("UPDATE sms_code SET status=1 WHERE phone = #{phone} AND code = #{code} AND type = #{type}")
    boolean usedCode(@Param("phone") String phone,@Param("code") String code,@Param("type") Integer type);

}
