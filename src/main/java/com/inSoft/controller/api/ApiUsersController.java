package com.inSoft.controller.api;

import com.inSoft.config.LoginConfig;
import com.inSoft.config.VipConfig;
import com.inSoft.constant.ApiConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.WebUsers;
import com.inSoft.pojo.WebUsersVipser;
import com.inSoft.pojo.result.Result;
import com.inSoft.service.WebUsersService;
import com.inSoft.service.WebUsersVipserService;
import com.inSoft.utils.LoginUtils;
import com.inSoft.utils.MD5Util;
import com.inSoft.utils.MapUtils;
import com.inSoft.utils.VipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(ApiConstant.PATH_PREFIX+"/users")
public class ApiUsersController extends BaseController {

    @Autowired
    private WebUsersService userService;

    @Autowired
    private WebUsersVipserService webUsersVipserService;

    //获取用户信息
    @GetMapping("/info")
    public Result info() {
        if (checkToken()&&checkLogin()){
            return Result.success("获取成功~",getUserInfo());
        }
        return Result.error(ApiConstant.LOGIN_AND_TOKEN_ERROR);
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> formData) {
        String phone = MapUtils.get(formData, "phone");
        //手机号不可为空
        if (phone==null||phone.trim().isEmpty()){
            return Result.error("手机号不得为空！");
        }
        String password = MapUtils.get(formData, "password");
        //密码不可为空
        if (password==null||password.trim().isEmpty()){
            return Result.error("密码不得为空！");
        }
        //获取用户数据
        WebUsers webUser = userService.getUserInfoByPhone(phone);
        //判断用户是否存在
        if (webUser==null){
            return Result.error("用户不存在！");
        }
        //判断是否被禁用
        if (webUser.getEnable()==0){
            return Result.error("用户被禁用！");
        }
        //加密密码并验证密码是否正确
        if (LoginUtils.checkPassword(password,webUser.getPassword())){
            //更新ip
            webUser.setIp(getIpAddress());
            //更新登录时间
            webUser.setLoginAt(new java.util.Date());
            webUser.setLoginCount(webUser.getLoginCount()+1);
            //设置cookie
            Integer timeOut = LoginUtils.getTimeOut(0);
            String newToken = LoginUtils.getWebUserToken(webUser.getId(),webUser.getUsername(),webUser.getPhone(),webUser.getAvatar());
            webUser.setToken(newToken);
            if(userService.update(webUser)){
                setCookie(LoginConfig.getUserTokenName(),newToken,timeOut);
                analysis(2,1);
                setRedis(newToken,webUser);
                return Result.success("登录成功！",webUser.getToken());
            }
        }

        return Result.error("账户或密码不对~");
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody Map<String, Object> formData) {
        String phone = MapUtils.get(formData, "phone");
        String password = MapUtils.get(formData, "password");
        String sms_code = MapUtils.get(formData, "sms_code");
        //判断重要参数是否为空
        if (phone==null||password==null||sms_code==null){
            return Result.error("参数错误！");
        }
        //判断手机号是否未被注册
        if (userService.isOnlyPhone(phone)){
            return Result.error("该手机号已被注册！");
        }
        //验证短信验证码
        if (!checkSmsCode(phone,sms_code,1)){
            return Result.error("验证码失效或错误！");
        }
        //创建webUser对象，并生成昵称 设置默认头像 加密密码
        WebUsers webUser = new WebUsers();
        webUser.setPhone(phone);
        String phone_num = phone.substring(phone.length()-4);
        webUser.setNickname("用户"+phone_num);
        webUser.setUsername("user-"+phone);
        webUser.setEmail("");
        webUser.setAvatar("/default/avatar.jpg");//默认头像
        webUser.setPassword(LoginUtils.encodePassword(password));
        webUser.setEnable(1);
        webUser.setLoginCount(0);
        webUser.setCreatedAt(new java.util.Date());
        webUser.setUpdatedAt(new java.util.Date());
        webUser.setLoginAt(new java.util.Date());
        //性别保密
        webUser.setSex(0);
        //ip地址
        webUser.setIp(getIpAddress());
        webUser.setAddress("暂未填写~");
        String newToken = LoginUtils.getWebUserToken(webUser.getId(),webUser.getUsername(),webUser.getPhone(),webUser.getAvatar());
        webUser.setToken(newToken);
        if(userService.reg(webUser)){
            analysis(1,1);
            setRedis(newToken,webUser);
            return Result.success("注册成功！",webUser.getToken());
        }
        return Result.error("注册失败");
    }


    //找回密码
    @PostMapping("/forget")
    public Result forget(@RequestBody Map<String, Object> formData) {
        String phone = MapUtils.get(formData, "phone");
        if (phone==null||phone.trim().isEmpty()){
            return Result.error("手机号不得为空！");
        }
        String sms_code = MapUtils.get(formData, "sms_code");
        if (sms_code==null||sms_code.trim().isEmpty()){
            return Result.error("短信验证码不得为空！");
        }
        if (!checkSmsCode(phone,sms_code,3)){
            return Result.error("验证码失效或错误！");
        }
        String password = MapUtils.get(formData, "password");
        if (password==null||password.trim().isEmpty()){
            return Result.error("密码不得为空！");
        }
        String enPassword = LoginUtils.encodePassword(password);
        if (userService.updatePassword(phone,enPassword)){
            return Result.success("密码修改成功！");
        }
        return Result.error("密码修改失败！");
    }

    //退出登录
    @GetMapping("/logout")
    public Result logout() {
        if (checkToken()){
            String oldToken = getHeader().get(LoginConfig.getUserTokenName());
            //更新用户token
            String newToken = LoginUtils.getWebUserToken(getUserId(),"user"+oldToken,"phone"+oldToken,"avatar"+oldToken);
            if (userService.updateToken(getUserId(),newToken)){
                delRedis(oldToken);
                return Result.success("退出成功！");
            }
        }
        return Result.error("退出失败！");
    }

    //权限验证 免费用户限制 vip用户默认放行
    @GetMapping("/check")
    public Result check() {
        if (checkToken()&&checkLogin()){
            //获取vip信息
            WebUsersVipser webUsersVipser = webUsersVipserService.getByUid(getUserId());
            //免费vip及过期付费用户限制
            if (VipUtils.isFreeVip(webUsersVipser)||VipUtils.isVipExpired(webUsersVipser)){
                //优先获取当前的免费使用数
                String md5Key = MD5Util.md5(VipConfig.FREE_VIP_RECORD_KEY+getUserId());
                Integer freeCount = getRedis(md5Key,Integer.class);
                freeCount = freeCount==null?0:freeCount;
                //如果有使用的情况 则检查使用次数 如果没有则直接放行
                Integer limitFreeCount = VipConfig.FREE_VIP_LIMIT_NUM;
                if (freeCount<limitFreeCount){
                    freeCount++;
                    Integer timeout = VipConfig.FREE_VIP_LIMIT_TIME*(3600*24);
                    setRedis(md5Key,freeCount,timeout);
                    return Result.success("免费使用次数剩余:"+(limitFreeCount-freeCount)+"次");
                }
                return Result.error("免费使用次数已用完~");
            }
            //默认不限制付费用户 直接通过
            return Result.success("验证通过~");
        }
        return Result.error(ApiConstant.LOGIN_AND_TOKEN_ERROR);
    }

}
