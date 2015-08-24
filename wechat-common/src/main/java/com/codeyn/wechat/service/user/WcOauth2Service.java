package com.codeyn.wechat.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeyn.base.common.Assert;
import com.codeyn.wechat.sdk.base.WcClientFactory;
import com.codeyn.wechat.sdk.user.OauthClient;
import com.codeyn.wechat.sdk.user.result.OauthAccessToken;
import com.codeyn.wechat.wc.utils.WcCache;

/**
 * 
 * @author Arthur
 */
public class WcOauth2Service {

    private final static Logger logger = LoggerFactory.getLogger(WcOauth2Service.class);

    private static OauthClient getClient(String tenantId) {
        return WcClientFactory.getClient(OauthClient.class, WcCache.getWxBase(tenantId));
    }

    /**
     * 封装微信认证重定向URL
     */
    public static String oauth2(String tenantId, String redirectUri) {
        return getClient(tenantId).oauth2(redirectUri, OauthClient.SCOPE_BASE, "wc");
    }

    /**
     * 获取openId
     */
    public static String getOpenId(String tenantId, String code) {
        OauthAccessToken rs = getClient(tenantId).getOauthAccessToken(code);
        Assert.isTrue(rs != null && rs.isSuccess(), "获取OpenId失败: " + (rs == null ? "服务器内部异常" : rs.getErrmsg()));
        logger.info("wc request:" + rs.toJsonString());
        return rs.getOpenid();
    }

}
