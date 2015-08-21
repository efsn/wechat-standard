package com.codeyn.wechat.sdk.security;

import java.util.Map;

import com.codeyn.wechat.sdk.base.WxClient;
import com.codeyn.wechat.sdk.base.model.WxBase;
import com.codeyn.wechat.sdk.base.model.WxResult;


/**
 * 获取微信服务器IP地址
 */
public class CallbackIpClient extends WxClient{
    
	public CallbackIpClient(WxBase wxBase) {
        super(wxBase);
    }

	/**
	 * 获取微信服务器IP地址
	 */
	public WxResult getCallbackIp(final String accessToken) {
	    return doGet(WxResult.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/cgi-bin/getcallbackip");
	    
	}
}
