package com.codeyn.wechat.sdk.base.model;

import org.apache.commons.lang.StringUtils;

import com.codeyn.wechat.sdk.base.enums.EncryptType;

/**
 * 微信基本参数
 * @author Arthur
 */
public class WcBase {
	
    public static final String ENCODING = "UTF-8";
    
    /**
     * 微信接口地址
     */
    private final String wxHost = "https://api.weixin.qq.com";

    /**
     * 私钥
     */
	private String token = null;
	private String appId = null;
	private String appSecret = null;
	private String encodingAesKey = null;
	
	/**
	 * 消息加密模式
	 */
	private EncryptType encryptType = EncryptType.DIRECT;
	
	/**
	 * 微信名称
	 */
	private String wxNo;
	private String name;
	private String originId;
	private String domain;
	
	public WcBase() {
		
	}
	
	public WcBase(String token) {
		setToken(token);
	}
	
	public WcBase(String token, String appId, String appSecret) {
		setToken(token);
		setAppId(appId);
		setAppSecret(appSecret);
	}
	
	public WcBase(String wxNo,
                  String name,
                  String token,
                  String domain,
                  String originId,
                  String appId,
                  String appSecret,
                  String encodingAesKey,
                  Integer messageEncrypt) {
		setToken(token);
		setAppId(appId);
		setAppSecret(appSecret);
		setEncryptMessage(messageEncrypt);
		setEncodingAesKey(encodingAesKey);
		
		setWxNo(wxNo);
		setName(name);
		setOriginId(originId);
		setDomain(domain);
	}
	
	public String getToken() {
		if (StringUtils.isBlank(token))
			throw new IllegalStateException("token 未被赋值");
		return token;
	}
	
	public void setToken(String token) {
		if (StringUtils.isBlank(token))
			throw new IllegalArgumentException("token 值不能为空");
		this.token = token;
	}
	
	public String getAppId() {
		if (StringUtils.isBlank(appId))
			throw new IllegalStateException("appId 未被赋值");
		return appId;
	}
	
	public void setAppId(String appId) {
		if (StringUtils.isBlank(appId))
			throw new IllegalArgumentException("appId 值不能为空");
		this.appId = appId;
	}
	
	public String getAppSecret() {
		if (StringUtils.isBlank(appSecret))
			throw new IllegalStateException("appSecret 未被赋值");
		return appSecret;
	}
	
	public void setAppSecret(String appSecret) {
		if (StringUtils.isBlank(appSecret))
			throw new IllegalArgumentException("appSecret 值不能为空");
		this.appSecret = appSecret;
	}
	
	public String getEncodingAesKey() {
		if (StringUtils.isBlank(encodingAesKey))
			throw new IllegalStateException("encodingAesKey 未被赋值");
		return encodingAesKey;
	}
	
	public void setEncodingAesKey(String encodingAesKey) {
		if (StringUtils.isBlank(encodingAesKey))
			throw new IllegalArgumentException("encodingAesKey 值不能为空");
		this.encodingAesKey = encodingAesKey;
	}
	
	/**
	 * 是否对消息进行加密：
	 * true : 进行加密且必须配置 encodingAesKey
     * false : 采用明文模式，同时也支持混合模式
	 */
	public boolean isEncrypt() {
		return EncryptType.SAFE.equals(encryptType);
	}
	
	/**
	 * 默认为明文模式
	 */
	public void setEncryptMessage(Integer encryptType) {
	    EncryptType type = EncryptType.get(encryptType);
		this.encryptType = type == null ? EncryptType.DIRECT : type;
	}
	
	public String getHost(){
	    return wxHost;
	}

    public String getWxNo() {
        return wxNo;
    }

    public void setWxNo(String wxNo) {
        this.wxNo = wxNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
