package com.inSoft.controller.users;

import com.inSoft.config.LoginConfig;
import com.inSoft.config.PayConfig;
import com.inSoft.config.VipConfig;
import com.inSoft.constant.HomeConstant;
import com.inSoft.constant.UsersConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.enums.OrderStatusEnum;
import com.inSoft.enums.PayTypeEnum;
import com.inSoft.pojo.Income;
import com.inSoft.pojo.SysOrders;
import com.inSoft.pojo.WebUsers;
import com.inSoft.pojo.WebUsersVipser;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.SysOrdersVo;
import com.inSoft.service.*;
import com.inSoft.service.impl.IncomeServiceImpl;
import com.inSoft.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(UsersConstant.PATH_PREFIX)
public class UsersIndexController extends BaseController {

    private static String MODULE_PATH = UsersConstant.MODULE_PATH;

    @Autowired
    private WebUsersService userService;

    @Autowired
    private WebUsersVipserService webUsersVipserService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private SysOrdersService sysOrdersService;

    //登录页面
    @GetMapping("/login")
    public ModelAndView login(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        model.addObject("title","登录");
        model.setViewName(MODULE_PATH+"login");
        return model;
    }

    //登录请求
    @PostMapping("/login_request")
    public Result login_request(@RequestBody Map<String, Object> formData){
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
        String code = MapUtils.get(formData, "code");
        //验证码不可为空
        if (code==null||code.trim().isEmpty()){
            return Result.error("验证码不得为空！");
        }
        //验证验证码
        if (!verificationCode(code)) {
            return Result.error("验证码错误");
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
                return Result.success("登录成功！");
            }
        }

        return Result.error("账户或密码不对~");
    }

    //注册页面
    @GetMapping("/reg")
    public ModelAndView reg(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        // 加入一个属性，用来在模板中读取
        model.addObject("title","注册");
        model.setViewName(MODULE_PATH+"reg");
        return model;
    }

    //注册请求
    @PostMapping("/reg_request")
    public Result reg_request(@RequestBody Map<String, Object> formData ){
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
            //设置cookie
            Integer timeOut = LoginUtils.getTimeOut(0);
            setCookie(LoginConfig.getUserTokenName(),newToken,timeOut);
            analysis(1,1);
            setRedis(newToken,webUser);
            return Result.success("注册成功！");
        }
        return Result.error("注册失败");
    }

    //找回密码页面
    @GetMapping("/forget")
    public ModelAndView forget(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        // 加入一个属性，用来在模板中读取
        model.addObject("title","找回密码");
        model.setViewName(MODULE_PATH+"forget");
        return model;
    }

    //提交验证并存储redis 的校验信息 返回t值信息 给到下一个界面
    @PostMapping("/forget_request")
    public Result forget_request(@RequestBody Map<String, Object> formData){
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
        //创建验证map信息
        Map<String, Object> checkMap = new HashMap<>();
        checkMap.put("phone",phone);
        checkMap.put("sms_code",sms_code);
        Long expireTime = UsersConstant.FORGET_TIMEOUT;
        Long time_out = DateUtils.getCurrentTimestampInSeconds()+expireTime;
        checkMap.put("time_out",time_out);
        String roadNum = StringUtils.getRandomNumber(10);
        String t = MD5Util.md5("forget_"+phone+"_"+sms_code+time_out+roadNum);
        String s = MD5Util.md5(phone+"_"+time_out+roadNum);
        checkMap.put("slat",s);
        //存储redis
        setRedis(t,checkMap);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("t",t);
        resMap.put("s",s);
        return Result.success("验证成功！",resMap);
    }


    //设置新密码 页面
    @GetMapping("/forget_pwd")
    public ModelAndView forget_pwd(ModelAndView model,
                                   @RequestParam(value = "t",required = false) String t,
                                   @RequestParam(value = "s",required = false) String s){
        //判断是否有效如果无效则跳转到登录界面
        if (!checkForgetPwd(t,s)){
            return new ModelAndView("redirect:/users/login");
        }
        //获取剩余时间及手机号，用于前台显示使用
        Map<String, Object> mapT = getMap(t);
        String phone = MapUtils.get(mapT, "phone");
        Double time_out = MapUtils.get(mapT, "time_out", Double.class);
        Long expireTime = (long) (time_out-DateUtils.getCurrentTimestampInSeconds());
        //记录访问信
        analysis(0,1);
        // 加入一个属性，用来在模板中读取
        model.addObject("title","设置新密码");
        model.addObject("t",t);
        model.addObject("s",s);
        model.addObject("phone",phone);
        model.addObject("expireTime",expireTime);
        model.setViewName(MODULE_PATH+"forget_pwd");
        return model;
    }


    @PostMapping("/forget_pwd_request")
    public Result forget_pwd_request(@RequestBody Map<String, Object> formData){
        String t = MapUtils.get(formData, "t");
        String s = MapUtils.get(formData, "s");
        String password = MapUtils.get(formData, "newpwd");
        String confirm_newpwd = MapUtils.get(formData, "confirm_newpwd");

        //检查参数是否为空
        if (StringUtils.isEmpty(t)||StringUtils.isEmpty(s)||StringUtils.isEmpty(password)||StringUtils.isEmpty(confirm_newpwd)){
            return Result.error("缺少必要参数！");
        }

        //验证参数
        if (!checkForgetPwd(t,s)){
            return Result.error("无效链接，请返回上页重新填写！");
        }

        //判断是否有效如果无效则跳转到登录界面
        if (!checkForgetPwd(t,s)){
            return Result.error("无效链接，请重新申请！");
        }

        //判断密码是否一致
        if (!password.equals(confirm_newpwd)){
            return Result.error("两次密码不一致！");
        }

        //获取手机号
        Map<String, Object> mapT = getMap(t);
        String phone = MapUtils.get(mapT, "phone");
        //根据phone 更新密码
        String enPassword = LoginUtils.encodePassword(password);
        if (userService.updatePassword(phone,enPassword)){
            //删除redis
            delRedis(t);
            return Result.success("密码修改成功！");
        }
        return Result.error("密码修改失败！");
    }


    //资料设置
    @GetMapping("/setting")
    public ModelAndView setting(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        // 加入一个属性，用来在模板中读取
        model.addObject("title","资料设置");
        model.setViewName(MODULE_PATH+"setting");
        return model;
    }

    //保存资料
    @PostMapping("/save_setting")
    public Result save_setting(@RequestBody Map<String, Object> formData){
        String nickname = MapUtils.get(formData, "nickname");
        String sex = MapUtils.get(formData, "sex");
        String email = MapUtils.get(formData, "email");
        String address = MapUtils.get(formData, "address");
        //声明webUsers对象
        WebUsers webUsers = new WebUsers();
        webUsers.setId(getUserId());
        webUsers.setNickname(nickname);
        webUsers.setSex(Integer.parseInt(sex));
        webUsers.setEmail(email);
        webUsers.setAddress(address);
        //更新用户数据
        if (userService.update(webUsers)){
            return Result.success("资料保存成功！");
        }
        return Result.error("资料保存失败！");
    }

    //会员信息
    @GetMapping("/info")
    public ModelAndView info(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        // 加入一个属性，用来在模板中读取
        model.addObject("title","会员信息");
        //获取用户id
        Integer userId = getUserId();
        //更具userId查询输出增值信息 如果没有则返回免费vip参数
        WebUsersVipser webUsersVipser = webUsersVipserService.getByUid(userId);
        //判断是否存在如果不存在则默认返回免费用户数据
        List<Map<String, Object>> privilegeList = new ArrayList<Map<String, Object>>();
        String privilegeStr = VipConfig.FREE_VIP_PRIVILEGE;
        Date endDate = null;//永久
        String vipType = VipConfig.FREE_VIP_NAME;
        if (webUsersVipser!=null){
            //判断privilege是否存在
            Income income = incomeService.getById(webUsersVipser.getIncomeId());
            if (income != null) {
                privilegeStr = income.getPrivilege();
            }
            //转为年月日
            endDate = webUsersVipser.getEndTime();
            vipType = income.getName();
        }

        if (privilegeStr != null && !privilegeStr.isEmpty()) {
            // 将 getPrivilege 转为数组
            String[] privileges =privilegeStr.split(",");
            for (String privilege : privileges) {
                // 分割 privilege 按:号
                String[] split = privilege.split(":");
                // 输出 map 对象 0 和 1
                Map<String, Object> map = new HashMap<>();
                map.put("name", split[0]); // 服务特权项
                map.put("allow", split[1]); // 0代表 不允许  1代表允许
                privilegeList.add(map);
            }
        }
        model.addObject("vipType",vipType);
        model.addObject("endDate",endDate);
        model.addObject("privilegeList",privilegeList);
        model.setViewName(MODULE_PATH+"info");
        return model;
    }

    //充值记录
    @GetMapping("/recharge")
    public ModelAndView recharge(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        // 加入一个属性，用来在模板中读取
        model.addObject("title","充值记录");
        //查询充值记录 根据orderService
        List<Map<String, Object>> list = sysOrdersService.getRechargeByUid(getUserId(),15);
        //判断list是否存在如果存在
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                map.put("id", MapUtils.get(map, "id"));
                map.put("incomeName", MapUtils.get(map, "incomeName"));
                map.put("incomeId", MapUtils.get(map, "incomeId"));
                //createdAt
                map.put("payAt", MapUtils.get(map, "payAt"));
                //incomeDuration
                map.put("incomeDuration", MapUtils.get(map, "incomeDuration"));
                //amount
                map.put("amount", MapUtils.get(map, "amount"));
                map.put("payType", MapUtils.get(map, "payType"));
                //payTypeText
                map.put("payTypeText", PayTypeEnum.getByCode(MapUtils.get(map, "payType",Integer.class)).getValue());
            }
        }
        model.addObject("list",list);
        model.setViewName(MODULE_PATH+"recharge");
        return model;
    }

    //订单结算页面 支付界面
    @GetMapping("/order")
    public ModelAndView order(ModelAndView model,
                              @RequestParam(value = "income_id",required = false) Integer income_id){
        //记录访问信息
        analysis(0,1);
        // 加入一个属性，用来在模板中读取
        model.addObject("title","订单支付");
        //查询增值服务并输出
        Income income = incomeService.getById(income_id);
        model.addObject("income",income);
        //写入默认支付方式
        model.addObject("payType", PayConfig.DEFAULT_PAY_TYPE);//默认显示不受其他影响
        model.addObject("allEnable", PayConfig.ALL_TYPE_ENABLE);//是否打开全部支付方式
        model.setViewName(MODULE_PATH+"order");
        return model;
    }

    //修改密码
    @GetMapping("/change_pwd")
    public ModelAndView change_pwd(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        // 加入一个属性，用来在模板中读取
        model.addObject("title","修改密码");
        model.setViewName(MODULE_PATH+"change_pwd");
        return model;
    }

    //提交密码修改
    @PostMapping("/change_pwd_request")
    public Result change_pwd_request(@RequestBody Map<String, Object> formData){
        String oldpwd = MapUtils.get(formData, "oldpwd");
        String newpwd = MapUtils.get(formData, "newpwd");
        String confirm_newpwd = MapUtils.get(formData, "confirm_newpwd");

        //新密码和旧密码不能一样
        if (oldpwd.equals(newpwd)){
            return Result.error("新密码不能是旧密码！");
        }

        //检查参数是否为空
        if (StringUtils.isEmpty(oldpwd)||StringUtils.isEmpty(newpwd)||StringUtils.isEmpty(confirm_newpwd)){
            return Result.error("缺少必要参数！");
        }

        //判断密码是否一致
        if (!newpwd.equals(confirm_newpwd)){
            return Result.error("两次密码不一致！");
        }

        //获取用户信息
        WebUsers webUsers = userService.getById(getUserId());
        //判断密码是否一致
        if (!LoginUtils.encodePassword(oldpwd).equals(webUsers.getPassword())){
            return Result.error("旧密码不正确！");
        }

        //更新密码
        String enPassword = LoginUtils.encodePassword(newpwd);
        if (userService.updatePassword(webUsers.getPhone(),enPassword)){
            return Result.success("密码修改成功！");
        }
        return Result.error("密码修改失败！");
    }

    //头像设置
    @GetMapping("/avatar")
    public ModelAndView avatar(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        // 加入一个属性，用来在模板中读取
        model.addObject("title","头像设置");
        model.setViewName(MODULE_PATH+"avatar");
        return model;
    }

    @PostMapping("/save_avatar")
    public Result save_avatar(@RequestBody Map<String, Object> formData){
        String avatar = MapUtils.get(formData, "avatar");
        if (StringUtils.isEmpty(avatar)){
            return Result.error("缺少必要参数！");
        }
        //获取用户信息
        WebUsers webUsers = new WebUsers();
        webUsers.setId(getUserId());
        webUsers.setAvatar(avatar);
        if (userService.update(webUsers)){
            return Result.success("头像设置成功！");
        }
        return Result.error("头像设置失败！");
    }

    //退出登录
    @GetMapping("/logout")
    public Result logout(){
        //获取cookie 并销毁redis
        String nowToken = getCookieValue(LoginConfig.getUserTokenName());
        delRedis(nowToken);
        destroyCookie(LoginConfig.getUserTokenName());
        return Result.success("提出成功！");
    }

}
