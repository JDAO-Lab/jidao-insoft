package com.inSoft.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class IPUtil {

    @Autowired
    private HttpServletRequest request;

    public String getIpAddress() {
        String ip;

        ip = request.getHeader("True-Client-IP");
        if (isValidIpAddress(ip)) {
            return ip;
        }

        ip = request.getHeader("X-Real-IP");
        if (isValidIpAddress(ip)) {
            return ip;
        }

        ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty()) {
            // Extract the first IP in case of multiple proxies
            String[] ips = ip.split(",");
            if (ips.length > 0) {
                ip = ips[0].trim();
                if (isValidIpAddress(ip)) {
                    return ip;
                }
            }
        }

        ip = request.getHeader("Proxy-Client-IP");
        if (isValidIpAddress(ip)) {
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (isValidIpAddress(ip)) {
            return ip;
        }

        ip = request.getRemoteAddr();

        return isValidIpAddress(ip) ? ip : "unknown";
    }

    private boolean isValidIpAddress(String ip) {
        return ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip);
    }

    public String getRequestUrl() {
        return request.getRequestURI();
    }

    public String getMethod(){return request.getMethod();}

}