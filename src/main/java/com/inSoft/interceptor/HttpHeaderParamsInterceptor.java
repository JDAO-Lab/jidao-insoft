package com.inSoft.interceptor;


import com.inSoft.utils.HttpHeaderParamsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpHeaderParamsInterceptor implements HandlerInterceptor {

    /**
     * 获取全局参数信息
     * @return
     */
    private Map<String, String> getAllRequestHeaders() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            Map<String, String> headersMap = new HashMap<>();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headersMap.put(headerName, request.getHeader(headerName));
            }
            //当前线程名称
            log.info("当前线程名称：{}", Thread.currentThread().getName());
            log.info("Request headers:{}", headersMap.size());
            return headersMap;
        } else {
            return Collections.emptyMap(); // 返回空的Map
        }
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map<String, String> headersMap = getAllRequestHeaders();
        if (headersMap != null){
            HttpHeaderParamsUtils.add(headersMap);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        HttpHeaderParamsUtils.remove();
    }
}
