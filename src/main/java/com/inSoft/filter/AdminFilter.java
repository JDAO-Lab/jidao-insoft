package com.inSoft.filter;

import com.inSoft.config.LoginConfig;

import com.inSoft.pojo.SysRulesMenu;
import com.inSoft.pojo.result.Result;
import com.inSoft.service.SysAdminService;
import com.inSoft.service.SysRulesMenuService;
import com.inSoft.service.SysRulesService;
import com.inSoft.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * admin后台管理系统过滤器
 */
@Slf4j
@WebFilter(urlPatterns = "/admin/*")
public class AdminFilter implements Filter{

    private String tokenName =  LoginConfig.getAdminTokenName();

    @Resource
    private SysAdminService sysAdminService;

    @Resource
    private SysRulesMenuService sysRulesMenuService;

    @Resource
    private SysRulesService sysRulesService;

    @Resource
    private SysLogUtils sysLogUtils;

    @Resource
    private IPUtil ipUtil;

    @Resource
    private CookiesUtils cookiesUtils;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Boolean isAuth = false;
        String authMsg = "被拦截~";
        //1.获取请求url。
        String url = req.getRequestURL().toString();
        String method = req.getMethod();
        DeBugUtils.print("请求路径："+url);
        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        if(url.contains("login")){
            DeBugUtils.print("放行~");
            isAuth = true;
            authMsg = "放行~";
        }else {
            //3.获取请求头中的令牌（token）。
            String access_token = cookiesUtils.getCookieValue(tokenName);
            //未登录及登录验证，根据token查用户数据，有才放
            if(!StringUtils.hasLength(access_token)){
                DeBugUtils.print("未登录，已拦截~");
            }else {
                //token查询数据，有才放
                Integer loginNum = sysAdminService.countUserByToken(access_token);
                //检查token和redis是否都有
                if (loginNum>0){
                    //已登录检查rule_id 与url匹配，如果包含则放行，如果不包含返回权限不足的json提示
                    Integer rule_id = sysAdminService.getRuleIdByToken(access_token);
                    //根据rule_id查询rules权限信息的rules参数并将字符串1，2，3，4，5，6，7，8 数组化，并检测theUrlid是否存在如数组中。
                    String rules = sysRulesService.getRulesById(rule_id);
                    String[] rulesArray = null;
                    Integer theUrlId = -1;
                    if (!rules.isEmpty()){
                        String href = ipUtil.getRequestUrl();
                        SysRulesMenu menuObj = sysRulesMenuService.getMenuByHref(href);
                        rulesArray = rules.split(",");
                        DeBugUtils.print("权限验证："+href);
                        if (menuObj!=null){
                            theUrlId = menuObj.getId();
                        }
                    }
                    //如果本身在rules中，或者为超级管理员则放行
                    if (Arrays.asList(rulesArray).contains(String.valueOf(theUrlId))||rule_id==1) {
                        // 用户有权限，放行
                        isAuth = true;
                        authMsg = "已验证权限,放行~";
                    }else {
                        DeBugUtils.print("无权限，已拦截~");
                    }
                }else {
                    DeBugUtils.print("登录过期，已拦截~");
                }
            }
        }
        //令牌失效拦截
        if (isAuth){
            DeBugUtils.print("放行~");
            sysLogUtils.saveLog("管理系统权限验证:"+authMsg,1);
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }else {
            sysLogUtils.saveLog("管理系统权限验证:"+authMsg,2);
            if ("GET".equals(method)) {
                // GET请求跳转回首页
                resp.sendRedirect("/");
            } else {
                // 其他提交请求返回json提示
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                Result error = Result.error(authMsg);
                String error_json = JsonUtils.objectToJson(error);
                resp.getWriter().write(error_json);
            }
            return;
        }
    }

}
