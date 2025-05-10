package com.inSoft.filter;

import com.inSoft.utils.DeBugUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * api请求过滤器
 */
@Slf4j
@WebFilter(urlPatterns = "/api/*")
public class ApiFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }
}
