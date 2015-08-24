package com.codeyn.wechat.wc.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.codeyn.base.common.Assert;
import com.codeyn.wechat.sdk.base.WcClientFactory;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.security.AccessTokenClient;
import com.codeyn.wechat.sdk.security.result.AccessToken;
import com.codeyn.wechat.sdk.user.OauthClient;
import com.codeyn.wechat.sdk.user.result.OauthAccessToken;
import com.codeyn.wechat.service.WcAccessTokenService;
import com.codeyn.wechat.system.model.WcConfig;
import com.codeyn.wechat.system.service.WcConfigService;
import com.codeyn.wechat.wc.model.WcAccessToken;

/**
 * @author Codeyn
 *
 */
public final class WcCache {

    private static final Logger logger = LoggerFactory.getLogger(WcCache.class);
    private static final Map<String, Object> cache = new WeakHashMap<>();

    /**
     * 获取微信基本配置
     */
    public static WcBase getWxBase(String tenantId) {
        String key = WcBase.class.getSimpleName() + tenantId;
        WcBase wxBase = (WcBase) cache.get(key);
        if (wxBase == null) {
            synchronized (cache) {
                wxBase = buildWxBase(tenantId);
                cache.put(key, wxBase);
            }
        }
        Assert.notNull(wxBase, "WxBase");
        logger.debug(JSON.toJSONString(wxBase));
        return wxBase;
    }

    public synchronized static String getAccessToken(String tenantId) {
        String key = AccessToken.class.getSimpleName() + tenantId;
        AccessToken at = (AccessToken) cache.get(key);
        if (at == null || !at.isAvailable()) {
            at = getRemoteAccessToken(tenantId);
            if (at != null && at.isAvailable()) {
                cache.put(key, at);
            }
        }
        Assert.notNull(at, "AccessToken");
        logger.debug(JSON.toJSONString(at));
        return at.getAccess_token();
    }

    /**
     * 微信用户授权
     */
    public synchronized static OauthAccessToken getOauthAccessToken(String code, String tenantId) {
        String key = OauthAccessToken.class.getSimpleName() + tenantId;
        OauthAccessToken oat = (OauthAccessToken) cache.get(key);
        OauthClient client = WcClientFactory.getClient(OauthClient.class, WcCache.getWxBase(tenantId));
        if (oat == null) {
            oat = client.getOauthAccessToken(code);
        } else if (!oat.isAvailable()) {
            oat = client.refreshOauthAccessToken(oat.getRefresh_token());
            if (oat == null || !oat.isAvailable()) {
                oat = client.getOauthAccessToken(code);
            }
        }
        if (oat != null && oat.isAvailable()) {
            cache.put(key, oat);
        }
        Assert.notNull(oat, "OauthAccessToken");
        logger.debug(JSON.toJSONString(oat));
        return oat;
    }

    private static WcBase buildWxBase(String tenantId) {
        WcConfig wxConfig = WcConfigService.getConfigByTenantId(tenantId);
        Assert.notNull(wxConfig, "The wxConfig of " + tenantId);

        return new WcBase(wxConfig.getStr("wx_no"), wxConfig.getStr("name"), wxConfig.getStr("token"),
                wxConfig.getStr("domain"), wxConfig.getStr("origin_id"), wxConfig.getStr("app_id"),
                wxConfig.getStr("app_secret"), wxConfig.getStr("aes_key"), 0);// TODO
                                                                              // wx_config
                                                                              // 添加字段
                                                                              // 明文模式
    }

    /**
     * 支持多服务器同步AccessToken
     */
    private static AccessToken getRemoteAccessToken(String tenantId) {
        WcAccessToken rs = WcAccessToken.dao.getToken(tenantId);
        AccessToken token = null;
        if (rs == null || !new Date().before(rs.getTimestamp("create_time"))) {
            WcBase wxBase = getWxBase(tenantId);
            AccessTokenClient client = WcClientFactory.getClient(AccessTokenClient.class, wxBase);
            token = client.refreshAccessToken();

            rs = rs == null ? new WcAccessToken() : rs;
            rs.set("token", token.getAccess_token());
            rs.set("tenant_id", tenantId);
            rs.set("create_time", new Timestamp(token.getExpiredTime()));
            WcAccessTokenService.save(rs);
        } else {
            token = new AccessToken();
            token.setAccess_token(rs.getStr("token"));
            token.setExpiredTime(rs.getTimestamp("create_time").getTime());
        }
        return token;
    }

    /**
     * 如果 api 调用返回结果表明 access_token 无效，则刷新它 正常情况下不会出现使用本方法刷新 access_token
     * 的操作，除非以下情况发生： 1：Admin和Front 在AccessToken同时过期竞争状态下会出现Cache中的无效
     * 2：使用微信公众平台接口调试工具获取了该公众号的 access_token 3：微信服务器重新调整了过期时间或者发生其它 access_token
     * 异常情况
     */
    public synchronized static void refreshAccessTokenIfInvalid(WcResult result, String tenantId) {
        if (result.isAccessTokenInvalid()) {
            // 处理竞争过期，从数据库刷新
            WcAccessToken rs = WcAccessToken.dao.getToken(tenantId);
            if (rs != null && new Date().before(rs.getTimestamp("create_time"))) {
                // 获取cache中的access
                String accessToken = getAccessToken(tenantId);
                AccessToken token = null;

                // 是否与数据库中一致
                if (accessToken.equals(rs.getStr("token"))) {
                    // 外部刷新
                    WcBase wxBase = getWxBase(tenantId);
                    AccessTokenClient client = WcClientFactory.getClient(AccessTokenClient.class, wxBase);
                    token = client.refreshAccessToken();

                    rs.set("token", token.getAccess_token());
                    rs.set("tenant_id", tenantId);
                    rs.set("create_time", new Timestamp(token.getExpiredTime()));
                    WcAccessTokenService.save(rs);
                } else {
                    // 其他系统刷新
                    token = new AccessToken();
                    token.setAccess_token(rs.getStr("token"));
                    token.setExpiredTime(rs.getTimestamp("create_time").getTime());
                }

                cache.put(AccessToken.class.getSimpleName() + tenantId, token);
            }
        }
    }

}
