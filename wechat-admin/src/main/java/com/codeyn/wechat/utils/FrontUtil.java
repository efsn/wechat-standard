package com.codeyn.wechat.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.codeyn.utils.HttpUtil;

public class FrontUtil {

    public static boolean isAjax(HttpServletRequest request) {
        return request.getRequestURI().endsWith(".json") || HttpUtil.isAjax(request);
    }

    /**
     * 获取根路径
     */
    public static String getBasePath(HttpServletRequest request) {
        StringBuffer sbBaseUrl = new StringBuffer();
        sbBaseUrl.append(request.getScheme() + "://" + request.getServerName());
        if (request.getServerPort() != 80) {
            sbBaseUrl.append(":" + request.getServerPort());
        }

        if (StringUtils.isNotBlank(request.getContextPath())) {
            sbBaseUrl.append(request.getContextPath());
        }
        return sbBaseUrl.toString();
    }

    /**
     * 编码url
     */
    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
