package com.inSoft.filter;

import com.inSoft.utils.DeBugUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * web前端过滤器
 */
@Slf4j
@WebFilter(urlPatterns = "/**")
public class HomeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        DeBugUtils.print("放行Home~");
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }
}
