package com.codeyn.wechat.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.jfinal.base.WcBaseController;
import com.codeyn.wechat.system.model.WcConfig;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;

public class WcUtil {

    protected final static Logger logger = LoggerFactory.getLogger(WcUtil.class);

    /**
     * 封装微信认证重定向URL
     */
    public static String encaAuthorizeUrl(String appId, WcBaseController wxJFinalBaseController) {
        StringBuilder redirectUrl = new StringBuilder();
        redirectUrl.append(PropKit.get("wx.oauth2.authorize.url"));
        redirectUrl.append("?appid=").append(appId);
        redirectUrl.append("&redirect_uri=").append(getSourceUrl(wxJFinalBaseController));
        redirectUrl.append("&response_type=code&scope=snsapi_base&state=wx#wechat_redirect");
        logger.info("wx request:" + redirectUrl.toString());
        return redirectUrl.toString();
    }

    /**
     * 获取当前URL-特殊，其它模块不要使用
     */
    public static String getSourceUrl(WcBaseController wxJFinalBaseController) {
        StringBuffer source = wxJFinalBaseController.getRequest().getRequestURL();
        String queryString = wxJFinalBaseController.getRequest().getQueryString();

        // 过滤微信code参数
        if (StringUtils.isNotBlank(queryString)) {
            queryString = queryString.replaceAll("code=.*state=wx", "");
            if (StringUtils.isNotBlank(queryString)) {
                source.append("?").append(queryString);
            }
        }

        try {
            return URLEncoder.encode(source.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    /**
     * 获取openId
     */
    public static String getOpenId(WcConfig wxConfig, String code) {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(PropKit.get("wx.oauth2.access.token.url")).append("&appid=").append(wxConfig.getStr("app_id"));
        sbUrl.append("&secret=").append(wxConfig.getStr("app_secret"));
        sbUrl.append("&code=").append(code);

        String jsonResult = HttpKit.get(sbUrl.toString());
        logger.info("wx request:" + jsonResult);
        JSONObject jsonTexts = (JSONObject) JSON.parse(jsonResult);
        return String.valueOf(jsonTexts.get("openid"));
    }
}
