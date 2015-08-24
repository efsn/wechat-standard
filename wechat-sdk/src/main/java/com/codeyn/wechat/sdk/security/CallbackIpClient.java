package com.codeyn.wechat.sdk.security;

import java.util.Map;

import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.base.model.WcResult;


/**
 * 获取微信服务器IP地址
 */
public class CallbackIpClient extends WcClient{
    
	public CallbackIpClient(WcBase wxBase) {
        super(wxBase);
    }

	/**
	 * 获取微信服务器IP地址
	 */
	public WcResult getCallbackIp(final String accessToken) {
	    return doGet(WcResult.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/cgi-bin/getcallbackip");
	    
	}
}
