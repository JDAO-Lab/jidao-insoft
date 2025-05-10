package com.inSoft.controller;

import com.inSoft.enums.StaticTypeEnum;
import com.inSoft.pojo.WebUsers;
import com.inSoft.service.SmsCodeService;
import com.inSoft.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class BaseController {
    @Autowired
    private SysLogUtils sysLogUtils;

    @Autowired
    private HttpServletRequest breq;

    @Autowired
    private SysStaticUtil sysStaticUtil;

    @Autowired
    private CaptchaUtil captchaUtil;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private IPUtil iPUtil;

    @Autowired
    private CookiesUtils cookiesUtils;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取全局header参数返回map
     */
    public Map<String, String> getHeader(){
        Map<String, String> headersMap = HttpHeaderParamsUtils.get();
        return headersMap;
    }

    /**
     * 接收header传递过来的access_token 并解析匹配查看是否有效 有效则返回true 反之false
     * @return
     */
    public boolean checkToken(){
        Map header = getHeader();
        //获取access_token
        String access_token = MapUtils.get(header, "access_token");
        if (access_token != null&& !access_token.equals("")){
            //解析access_token 并判断是否在有效期类 并且appid 和appkey相同 才可放行
            if(ApiUtils.checkAccessToken(access_token)){
                return true;
            }
        }
        return false;
    }

    //判断是否登录中
    public boolean checkLogin(){
        if (getUserInfo()!=null){
            return true;
        }
        return false;
    }

    //验证验证码
    public boolean verificationCode(String code){
       return captchaUtil.validateCaptcha(breq,code);
    }

    //验证短信验证码
    public boolean checkSmsCode(String phone, String code,Integer type){
        //验证并消耗短信验证码
        if (smsCodeService.checkCode(phone,code,type)){
            return smsCodeService.usedCode(phone,code,type);
        }else{
            return false;
        }
    }

    //获取ip
    public String getIpAddress()
    {
        return iPUtil.getIpAddress();
    }

    //记录统计数据 type对应参数请在枚举中查看
    public void analysis(Integer type,double value)
    {
        String name = StaticTypeEnum.getByCode(type).getValue();
        sysStaticUtil.analysis(type,name,value);
    }

    //获取当前用户数据
    public Object getUserInfo(){
        Map userInfo = (Map) breq.getAttribute("userInfo");
        return userInfo;
    }

    //获取当前用户的id
    public int getUserId(){
        Map userInfo = (Map) breq.getAttribute("userInfo");
        Integer id = Integer.parseInt((String) userInfo.get("id"));
        return id;
    }

    //存入系统日志
    public void saveSysLog(String remarks,Integer result) {
        sysLogUtils.saveLog(remarks,result);
    }


    //设置cookies
    public void setCookie(String name, String value, int maxAge) {
        cookiesUtils.setCookie(name, value, maxAge);
    }

    //读取cookies
    public String getCookieValue(String name) {
        return cookiesUtils.getCookieValue(name);
    }

    //销毁cookies
    public void destroyCookie(String name) {
        cookiesUtils.destroyCookie(name);
    }

    //设置redis
    public void setRedis(String key,String value){
        redisUtil.set(key,value);
    }

    public void setRedis(String key,Object value){
        redisUtil.set(key,value);
    }

    //设置redis 包含时间
    public void setRedis(String key,String value,int expireTime){
        redisUtil.set(key,value,expireTime);
    }

    //设置redis 对象 包含时间
    public void setRedis(String key,Object value,int expireTime){
        redisUtil.set(key,value,expireTime);
    }

    //获取redis 字符串
    public String getRedis(String key){
        return redisUtil.get(key);
    }

    //获取redis 对象
    public <T> T getRedis(String key, Class<T> clazz){
        return redisUtil.get(key,clazz);
    }

    //获取map
    public Map<String, Object> getMap(String key){
        return redisUtil.getMap(key);
    }

    //判断是否存在
    public boolean existsRedis(String key){
        return redisUtil.exists(key);
    }

    //删除redis
    public void delRedis(String key){
        redisUtil.del(key);
    }

    //找回密码令牌验证
    public boolean checkForgetPwd(String t, String s){
        //空值直接跳转到登录界面
        if (t==null||s==null){
            return false;
        }
        //读取t的redis存储数据对象
        Map<String, Object> mapT = getMap(t);
        //数据空直接跳转到登录界面
        if (mapT==null){
            return false;
        }
        //汇总手机及验证码信息
        String phone = MapUtils.get(mapT, "phone");
        String sms_code = MapUtils.get(mapT, "sms_code");
        Double time_out = MapUtils.get(mapT, "time_out", Double.class);
        String slat = MapUtils.get(mapT, "slat");
        boolean isOut = DateUtils.getCurrentTimestampInSeconds()>time_out;
        boolean isSlat = s.equals(slat);
        //处理过期和缺参跳转
        if (phone==null||sms_code==null||isOut||!isSlat){
            return false;
        }
        return true;
    }
}
