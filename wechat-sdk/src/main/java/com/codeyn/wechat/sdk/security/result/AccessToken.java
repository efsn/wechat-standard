package com.codeyn.wechat.sdk.security.result;

import org.apache.commons.lang.StringUtils;

import com.codeyn.wechat.sdk.base.model.WcResult;

/**
 * 微信接口调用凭证，有效2小时
 * 多系统需要做缓存避免失效和重复获取
 * @author Arthur
 */
public class AccessToken extends WcResult{
	
	private String access_token;
	
	public boolean isAvailable() {
	    if (!isSuccess() || getExpiredTime() == null || getExpiredTime() <= System.currentTimeMillis())
			return false;
		return StringUtils.isNotBlank(access_token);
	}
	
	public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

}
