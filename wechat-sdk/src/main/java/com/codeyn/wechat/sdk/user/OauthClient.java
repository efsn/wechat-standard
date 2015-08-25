package com.codeyn.wechat.sdk.user;

import java.util.Map;

import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.user.result.OauthAccessToken;
import com.codeyn.wechat.sdk.user.result.WcUser;

/**
 * 网页授权获取用户基本信息
 * @author Arthur
 */
public class OauthClient extends WcClient{
    
    /**
     * 不弹出授权页面，直接跳转，只能获取用户openid
     */
    public static final String SCOPE_BASE = "snsapi_base";
    
    /**
     * 弹出授权页面，可通过openid拿到昵称、性别、所在地
     */
    public static final String SCOPE_USER_INFO = "snsapi_userinfo";
    
    public OauthClient(WcBase wxBase) {
        super(wxBase);
    }
    
    /**
     * 用户同意授权，获取code
     * 需要使用重定向的方式
     * @param url 授权后重定向的回调链接地址
     * @param scope snsapi_base/snsapi_userinfo
     */
    public String oauth2(String url, String scope, String state) {
        StringBuffer sb = new StringBuffer(
                "https://open.weixin.qq.com/connect/oauth2/authorize?appid=").append(getWcBase().getAppId())
                .append("&redirect_uri=").append(url).append("&response_type=code")
                .append("&scope=").append(scope).append("&state=")
                .append(state).append("#wechat_redirect");
        return sb.toString();
    }
    
    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * @param language zh_CN 简体，zh_TW 繁体，en 英语
     */
    public WcUser getUserInfo(final String oauthAccessToken, final String openId, final String language) {
        return doGet(WcUser.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", oauthAccessToken);
                map.put("openid", openId);
                map.put("lang", language);
            }
            
        }, "/sns/userinfo");
    }
    
    /**
     * 检验授权凭证oauthAccessToken是否有效
     */
    public WcResult isOauthValid(final String oauthAccessToken, final String openId) {
        return doGet(WcResult.class, new ParamService(){
            
            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", oauthAccessToken);
                map.put("openid", openId);
            }
            
        }, "/sns/auth");
    }
    
    /**
     * 通过code换取网页授权access_token
     */
    public OauthAccessToken getOauthAccessTokenForThree(String code){
        OauthAccessToken result = null;
        for (int i=0; i < 3; i++) { 
            result = getOauthAccessToken(code);
            if (result != null && result.isAvailable()){
                break;
            }
        }
        return result;
    }
    
    /**
     * 每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
     */
    public OauthAccessToken getOauthAccessToken(final String code){
        return doGet(OauthAccessToken.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("appid", getWcBase().getAppId());
                map.put("secret", getWcBase().getAppSecret());
                map.put("code", code);
            }
            
        }, "/sns/oauth2/access_token?grant_type=authorization_code");
    }
    
    /**
     * 刷新access_token
     */
    public OauthAccessToken refreshOauthAccessToken(final String refreshToken){
        return doGet(OauthAccessToken.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("appid", getWcBase().getAppId());
                map.put("refresh_token", refreshToken);
            }
            
        }, "/sns/oauth2/refresh_token?grant_type=refresh_token");
    }
    
}
