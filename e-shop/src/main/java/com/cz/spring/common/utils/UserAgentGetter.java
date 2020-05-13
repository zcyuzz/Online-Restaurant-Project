package com.cz.spring.common.utils;

import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class UserAgentGetter {
    private UserAgent userAgent;
    private String userAgentString;
    private HttpServletRequest request;

    public UserAgentGetter(HttpServletRequest request) {
        this.request = request;
        userAgentString = request.getHeader("User-Agent");
        userAgent = UserAgent.parseUserAgentString(userAgentString);
    }


    public String getBrowser() {
        if (null == userAgent) {
            return "";
        }
        return userAgent.getBrowser().getName();
    }


    public String getOS() {
        if (null == userAgent) {
            return "unknown device";
        }
        return userAgent.getOperatingSystem().getName();
    }

  
    public String getDevice() {
        if (null == userAgentString) {
            return "unknown device";
        }
        if (userAgentString.contains("Android")) {
            String[] str = userAgentString.split("[()]+");
            str = str[1].split("[;]");
            String[] res = str[str.length - 1].split("Build/");
            return res[0].trim();
        } else if (userAgentString.contains("iPhone")) {
            String[] str = userAgentString.split("[()]+");
            String res = "iphone" + str[1].split("OS")[1].split("like")[0];
            return res.trim();
        } else if (userAgentString.contains("iPad")) {
            return "iPad";
        } else {
            return getOS().trim();
        }
    }


    public String getIpAddr() {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (isBlankIp(ip))
                ip = request.getHeader("Proxy-Client-IP");
            if (isBlankIp(ip))
                ip = request.getHeader("WL-Proxy-Client-IP");
            if (isBlankIp(ip))
                ip = request.getHeader("HTTP_CLIENT_IP");
            if (isBlankIp(ip))
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (isBlankIp(ip))
                ip = request.getRemoteAddr();
            if (!isBlankIp(ip) && ip.length() > 15)
                ip = ip.split(",")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    private boolean isBlankIp(String str) {
        return str == null || str.trim().isEmpty() || "unknown".equalsIgnoreCase(str);
    }
}
