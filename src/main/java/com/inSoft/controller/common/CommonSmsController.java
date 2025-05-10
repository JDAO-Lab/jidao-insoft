package com.inSoft.controller.common;

import com.inSoft.config.SmsConfig;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.SmsCode;
import com.inSoft.pojo.result.Result;
import com.inSoft.service.SmsCodeService;
import com.inSoft.utils.AliSmsUtils;
import com.inSoft.utils.IPUtil;
import com.inSoft.utils.StringUtils;
import com.inSoft.utils.TencentSmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class CommonSmsController extends BaseController{

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private AliSmsUtils aliSmsUtils;

    @Autowired
    private TencentSmsUtils tencentSmsUtils;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private IPUtil ipUtils;


    /**
     * 发送短信验证码
     */
    @PostMapping("/send_code")
    public Result sendCode(@RequestParam(name = "phone", required = false) String phone,
                           @RequestParam(name = "stype", required =false ) Integer stype,
                           @RequestParam(name = "vtype", required = false) String vtype,
                           @RequestParam(name = "code", required = false) String code) throws Exception {

        //检查phone和stype是否存在 只要有其中之一不存在就报错
        if(StringUtils.isEmpty(phone)||stype==null){
            return Result.error("缺少参数~");
        }

        //检测验证类型
        if(vtype.equals("api")){
            //验证api
            if (!checkToken()){
                return Result.error("access_token无效");
            }
        }else {
            //验证码 验证
            if(!verificationCode(code)){
                return Result.error("验证码错误");
            }
        }

        //检查这个手机在指定时间内时候超出限制条数
        if(smsCodeService.checkLimit(phone,stype)){
            return Result.error("短信发送太频繁，请"+smsConfig.getLimitTime()+"秒后再试");
        }

        //步骤一、生成验证码
        String numCode = StringUtils.getRandomCode(6);
        //步骤二、根据现有色smsConfid的默认配置 匹配发送的短信平台
        log.info("当前使用的短信平台为：" + smsConfig.getDefaultType());
        String defaultType = smsConfig.getDefaultType();
        boolean isSend = false;
        switch (defaultType) {
            case "alisms":
                isSend = aliSmsUtils.sendCode(phone, numCode) ;
                break;
            case "tensms":
                isSend = tencentSmsUtils.sendCode(phone, numCode) ;
                break;
            default:
                log.info("暂不支持该短信平台");
                break;
        }
        if(isSend){
            //保存sms短信下发的验证码到smsCode
            SmsCode smsCode = new SmsCode(null, phone, numCode, new Date(), ipUtils.getIpAddress(), stype);
            if (smsCodeService.save(smsCode)){
                return Result.success("短信发送成功");
            };
        }
        return Result.success("短信发送失败");

    }

}
