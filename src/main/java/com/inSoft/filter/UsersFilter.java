package com.inSoft.filter;

import com.inSoft.config.LoginConfig;
import com.inSoft.service.WebUsersService;
import com.inSoft.utils.CookiesUtils;
import com.inSoft.utils.DeBugUtils;
import com.inSoft.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * web前端用户中心过滤器
 */
@Slf4j
@WebFilter(urlPatterns = "/users/*")
public class UsersFilter implements Filter {

    @Resource
    private CookiesUtils cookiesUtils;

    @Resource
    private WebUsersService webUsersService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        boolean isAuth = false;
        // 获取token 如果为空则拦截
        if (req.getRequestURI().contains("login")
                || req.getRequestURI().contains("reg")
                || req.getRequestURI().contains("logout")
                || req.getRequestURI().contains("forget")){
            isAuth = true;
        }else {
            String userToken = cookiesUtils.getCookieValue(LoginConfig.getUserTokenName());
            // 判断 userInfo 是否存在
            if (userToken != null) {
                //根据token 查询redis判断是否存在 以此判断登录
                if (redisUtil.exists(userToken)){
                    isAuth = true;
                } else {
                    // 不放行请求，重定向到登录页面
                    DeBugUtils.print("未登录，已拦截~");
                    isAuth = false;
                }
            }else {
                DeBugUtils.print("未登录，已拦截~");
                isAuth = false;
            }
        }

        //进行验证
        if(isAuth){
            filterChain.doFilter(req, resp);
            return;
        } else {
            // 不放行请求，重定向到登录页面
            DeBugUtils.print("拦截Users~");
            resp.sendRedirect("/users/login");
        }
    }

}
