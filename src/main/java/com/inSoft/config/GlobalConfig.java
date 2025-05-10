package com.inSoft.config;

import com.inSoft.pojo.SysAdmin;
import com.inSoft.pojo.SysRulesMenu;
import com.inSoft.pojo.WebNav;
import com.inSoft.pojo.WebUsers;
import com.inSoft.service.*;
import com.inSoft.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;
import java.util.Map;

@Configuration
public class GlobalConfig implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(GlobalConfig.class);

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysAdminService sysAdminService;

    @Autowired
    private SysRulesMenuService sysRulesMenuService;

    @Autowired
    private WebNavService webNavService;

    @Autowired
    private WebUsersService webUserService;

    @Autowired
    private CookiesUtils cookiesUtils;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor());
    }

    class MyInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
            //获取系统配置信息 全局
            List sysConfigObj = sysConfigService.getAllSysConfigs();
            Map sys =  TransUtils.convertToSysConfMap(sysConfigObj);
            request.setAttribute("sys", sys);
            //获取菜单名称信息，并设置title头
            String href = request.getRequestURI();
            String title = "未定义页面名称";
            String keywords = "未定义页面关键字";
            String description = "未定义页面描述";
//            DeBugUtils.print(href);
            // 分流查询前后端全局页头信息
            if (href.contains("/admin")) {
                // 如果包含/admin，执行相关操作
                SysRulesMenu menuObj = sysRulesMenuService.getMenuByHref(href);
                String menu_desc = "未定义页面描述信息";
//                DeBugUtils.print(menuObj);
                if (menuObj!=null){
                    title = menuObj.getTitle();
                    menu_desc =  menuObj.getDescription();
                }
                request.setAttribute("menu_desc",menu_desc);
            }else if(href.equals("/")) {
                //如果是首页，默认不需要查询，调用sys进行输出
                title = (String) sys.get("web_site");
                keywords = (String) sys.get("web_keywords");
                description = (String) sys.get("web_description");
            }else{
                //非admin及首页情况，查询前端导航数据输出标题和关键词信息数据
                WebNav webNav = webNavService.getByPath(href);
                if (webNav!=null){
                    title = webNav.getName();
                    keywords = webNav.getKeywords();
                    description = webNav.getDescription();
                }
            }
            request.setAttribute("title", title);
            request.setAttribute("keywords", keywords);
            request.setAttribute("description", description);

            //获取用户信息 根据路径情况进行分流查询 全局通用数据
            if (href.contains("/admin")) {
                //获取管理员用户信息
                String adminTokenName =  LoginConfig.getAdminTokenName();
                String nowAdminToken = cookiesUtils.getCookieValue( adminTokenName);
                if (nowAdminToken!=null&&!nowAdminToken.isEmpty()){
                    SysAdmin userInfo = sysAdminService.getUserInfoByToken(nowAdminToken);
                    if (userInfo!=null){
                        Map userInfoMap = TransUtils.convertSysAdminToMap(userInfo);
                        request.setAttribute("userInfo",userInfoMap);
                    }
                }
            }else if (href.contains("/api")){
                //api接口通过header中的 user_token 查询用户信息
                String nowUserToken = request.getHeader(LoginConfig.getUserTokenName());
                if (nowUserToken!=null&&!nowUserToken.isEmpty()){
                    WebUsers userInfo = webUserService.getUserInfoByToken(nowUserToken);
                    if (userInfo!=null){
                        Map userInfoMap = TransUtils.convertWebUserToMap(userInfo);
                        request.setAttribute("userInfo",userInfoMap);
                    }
                }
            }else{
                //非管理后台路径查询前台用户信息
                String userTokenName =  LoginConfig.getUserTokenName();
                String nowUserToken = cookiesUtils.getCookieValue(userTokenName);
                if (nowUserToken!=null&&!nowUserToken.isEmpty()){
                    WebUsers userInfo = webUserService.getUserInfoByToken(nowUserToken);
                    if (userInfo!=null){
                        Map userInfoMap = TransUtils.convertWebUserToMap(userInfo);
                        request.setAttribute("userInfo",userInfoMap);
                    }
                }

                //输出前端主导航信息
                List<Object> webNavList =  webNavService.getNavDataByCid(1);
                request.setAttribute("webNavList",webNavList);
                //判断是否是users路径如果是则输出 user菜单
                if (href.contains("/users")){
                    List<Object> userNavList = webNavService.getNavDataByCid(2);
                    request.setAttribute("userNavList",userNavList);
                }
                //输出底部公共菜单信息
                List<Object> fotNavList = webNavService.getNavDataByCid(3);
                request.setAttribute("fotNavList",fotNavList);
            }
            request.setAttribute("thisPageHref",href);
            return true;
        }
    }
}