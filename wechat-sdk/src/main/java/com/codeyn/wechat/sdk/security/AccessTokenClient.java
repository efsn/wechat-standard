package com.codeyn.wechat.sdk.security;

import java.util.Map;

import com.codeyn.base.common.Assert;
import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.security.result.AccessToken;

public class AccessTokenClient extends WcClient {
	
	public AccessTokenClient(WcBase wxBase) {
        super(wxBase);
    }

    /**
	 * 强制更新 access token 值，最多三次请求
	 */
	public AccessToken refreshAccessToken() {
		AccessToken result = null;
		for (int i=0; i < 3; i++) {	
		    result = getAccessToken();
			if (result != null && result.isAvailable()){
			    break;
			}
		}
		Assert.notNull(result, "AccessToken");
		return result;
	}
	
	private AccessToken getAccessToken(){
	    return doGet(AccessToken.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("appid", getWxBase().getAppId());
                map.put("secret", getWxBase().getAppSecret());
            }
	        
	    }, "/cgi-bin/token?grant_type=client_credential");
	}
	
}
