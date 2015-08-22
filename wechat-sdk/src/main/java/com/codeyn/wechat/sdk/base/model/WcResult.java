package com.codeyn.wechat.sdk.base.model;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Arthur
 *
 */
public class WxResult {
    
	private Integer errcode;
	private String errmsg = "SUCCESS";
	
	/**
     * 正确获取到 access_token 时有值
     */
    private Integer expires_in;
    
    /**
     * 正确获取到 access_token 时有值，存放过期时间
     */
    private Long expiredTime;
    
    /**
     * 微信服务器IP地址
     */
    private List<String> ip_list;
	
    /**
	 * 如果 api 调用返回结果表明 access_token 无效，则刷新它
	 * 正常情况下不会出现使用本方法刷新 access_token 的操作，除非以下情况发生：
	 * 1：另一程序重新获取了该公众号的 access_token
	 * 2：使用微信公众平台接口调试工具获取了该公众号的 access_token，此情况本质上与 1 中情况相同
	 * 3：微信服务器重新调整了过期时间或者发生其它 access_token 异常情况
	 */
	
	/*
	private void refreshAccessTokenIfInvalid() {
		if (isAccessTokenInvalid())
			AccessTokenClient.refreshAccessToken();
	}
	*/
	
	public String toJsonString() {
		return JSONObject.toJSONString(this);
	}
	
   public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
	
	/**
	 * errorCode 为 0 时也可以表示为成功
	 */
	public boolean isSuccess() {
		return errcode == null || errcode == 0;
	}
	
	public Integer getErrcode() {
		return errcode;
	}
	
	public String getErrmsg() {
		if (errcode != null) {
			String result = ErrorCode.get(errcode);
			if (result != null)
				return result;
		}
		return errmsg;
	}
	
	/**
	 * 判断 API 请求结果失败是否由于 access_token 无效引起的
	 * 无效可能的情况 error_code 值：
	 * 40001 = 获取access_token时AppSecret错误，或者access_token无效(刷新后也可以引起老access_token失效)
	 * 42001 = access_token超时
	 * 42002 = refresh_token超时
	 * 40014 = 不合法的access_token
	 */
	public boolean isAccessTokenInvalid() {
		return errcode != null && (errcode == 40001 || errcode == 42001 || errcode == 42002 || errcode == 40014);
	}
	
	public Integer getExpires_in() {
        return expires_in;
    }

    /**
     * 提前5秒
     */
    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
        if (expires_in != null)
            this.setExpiredTime(System.currentTimeMillis() + ((expires_in -5) * 1000));
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public List<String> getIp_list() {
        return ip_list;
    }

    public void setIp_list(List<String> ip_list) {
        this.ip_list = ip_list;
    }
	
}
