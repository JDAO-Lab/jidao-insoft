package com.inSoft.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class CookiesUtils {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    // 设置Cookie
    public void setCookie(String name, String value, int maxAge) {
        if (request == null || response == null) {
            throw new IllegalStateException("HttpServletRequest或HttpServletResponse对象未初始化");
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    // 通过name读取前端Cookie的值
    public String getCookieValue(String name) {
        if (request == null) {
            throw new IllegalStateException("HttpServletRequest对象未初始化");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
//                    DeBugUtils.print("Cookie：" + name);
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // 销毁指定名称的Cookie
    public void destroyCookie(String name) {
        if (request == null || response == null) {
            throw new IllegalStateException("HttpServletRequest或HttpServletResponse对象未初始化");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setMaxAge(0); // 将cookie的有效期设置为0，立即失效
                    cookie.setPath("/"); // 设置cookie的路径，确保能够正确删除
                    response.addCookie(cookie); // 更新响应，删除对应的cookie
                    break;
                }
            }
        }
    }
}
