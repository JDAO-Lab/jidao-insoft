package com.inSoft.controller.admin;

import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.mapper.SysRulesMenuMapper;
import com.inSoft.pojo.SysRulesMenu;
import com.inSoft.service.SysConfigService;
import com.inSoft.utils.MD5Util;
import com.inSoft.pojo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX)
public class AdminSettingController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"setting/";

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysRulesMenuMapper sysRulesMenuMapper;

    /**
     * 系统基本参数配置页
     * @return
     */
    @GetMapping("/setting")
    public ModelAndView setting(ModelAndView model,HttpServletRequest request){
        //读取系统配置中的加密字串，如果不匹配则默认跳转到前台
        Map sys = (Map) request.getAttribute("sys");
        // 获取系统配置中的 admin_key
        String adminKeyStr = (String) sys.get("admin_key");
        String adminLoginUrl = "/admin/login";
        if (!adminKeyStr.isEmpty()){
            adminLoginUrl+="?k="+ MD5Util.md5(adminKeyStr);
        }
        model.addObject("adminLoginUrl", adminLoginUrl);
        model.setViewName(MODULE_PATH+"setting");
        return model;
    }


    @PostMapping("/setting")
    public Result handleSetting(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        boolean isSave = false;
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            for (String value : values) {
                // 在这里可以将数据存储到数据库或进行其他业务逻辑处理
                if (sysConfigService.updateConfigValueByName(key,value)){
                    isSave = true;
                }
            }
        }
        if (isSave){
         return Result.success("保存完毕！");
        }
        return Result.error("保存失败!");
    }

    /**
     * 显示主题配置页
     * @param model
     * @return
     */
    @GetMapping("/theme_setting")
    public ModelAndView showThemeSetting(ModelAndView model){
        //查询输出主题配置
        String themeConfigJson = sysConfigService.getConfigValueByName("theme_config");
        model.addObject("themeConfigJson", themeConfigJson);//用于前端二次传值到后端
        //读取子级菜单且为显示类型的菜单数据列表
        List<SysRulesMenu> menus =  sysRulesMenuMapper.getUserRulesMenuSon();
        model.addObject("menus", menus);//用于前端二次传值到后端
        model.setViewName(MODULE_PATH+"theme_setting");
        return model;
    }

    /**
     * 保存主题配置信息
     * @return
     */
    @PostMapping("/theme_setting")
    public Result handleThemeSetting(@RequestParam String themeConfig){
        //将接收到的json对象进行重写赋值，并转为json字符串保存。
        if (themeConfig!=null&&!themeConfig.isEmpty()){
            if (sysConfigService.updateConfigValueByName("theme_config",themeConfig)){
                return Result.success("保存完毕!");
            }
        }
        return Result.error("保存失败！");
    }

    /**
     * 违禁词配置页
     * @return
     */
    @GetMapping("/prohibited_words")
    public ModelAndView prohibitedWords(ModelAndView model){
        model.setViewName(MODULE_PATH+"prohibited_words");
        return model;
    }

    @PostMapping("/prohibited_words")
    public Result handleProhibitedWords(@RequestParam String prohibited_words) {
        if (prohibited_words==null||prohibited_words.isEmpty()){
            return Result.error("违禁词信息为空！");
        }

        if (sysConfigService.updateConfigValueByName("prohibited_words",prohibited_words)){
            return Result.success("保存完毕!");
        }

        return Result.error("保存失败！");
    }
}
