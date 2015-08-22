package com.codeyn.wechat.sdk.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.codeyn.wechat.sdk.base.model.WxBase;
import com.codeyn.wechat.sdk.base.model.WxResult;

/**
 * 
 * @author Arthur
 *
 */
public abstract class WxClient {
    
    protected static final String KEY = "no_name_data";
    
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private WxBase wxBase;
	
	private Integer connectTimeout;
	
	private Integer readTimeout;
	
	protected WxClient(WxBase wxBase){
	    this.wxBase = wxBase;
	    setConnectTimeout(WxClientFactory.getGlobalConnectTimeOut());
	    setReadTimeout(WxClientFactory.getGlobalReadTimeout());
	}
	
	protected WxClient(WxBase wxBase, Integer connectTimeout, Integer readTimeout){
	    this(wxBase);
	    setConnectTimeout(connectTimeout);
	    setReadTimeout(readTimeout);
	}

	/**
	 * 用于特殊的链接
	 */
	protected <T extends WxResult> T doService(Class<T> clazz, ParamService paramService, String url) {
	    Map<String, String> map = new HashMap<>();
        paramService.init(map);
        String response = HttpUtil.get(connectTimeout, readTimeout, url, map);
        logger.debug(response);
        return StringUtils.isBlank(response) ? null : JSON.parseObject(response, clazz);
	}
	
	/**
	 * 需要传access_token，避免死循环
	 */
	protected <T extends WxResult> T doGet(Class<T> clazz, ParamService paramService, String url) {
	    Map<String, String> map = new HashMap<>();
	    paramService.init(map);
	    String response = HttpUtil.get(connectTimeout, readTimeout, wxBase.getHost() + url, map);
	    logger.debug(response);
	    return StringUtils.isBlank(response) ? null : JSON.parseObject(response, clazz);
	}
	
	/**
	 * POST just use for access_token with no_name_data
	 * @param key clazz key in response json
	 */
	protected <T extends WxResult> T doPost(Class<T> clazz, ParamService paramService, String url) {
	    return doPost(null, clazz, paramService, url);
	}
	
	protected <T extends WxResult> T doPost(String key, Class<T> clazz, ParamService paramService, String url) {
        Map<String, String> map = new HashMap<>();
        paramService.init(map);
        String response = HttpUtil.post(connectTimeout, readTimeout, wxBase.getHost() + url, map.get(KEY));
        logger.debug(response);
        return StringUtils.isBlank(response) ? null : StringUtils.isBlank(key) ? 
                JSON.parseObject(response, clazz) : JSON.parseObject(response).getObject(key, clazz);
    }
	
	public interface ParamService{
	    void init(Map<String, String> map);
	}
	
	public WxBase getWxBase(){
	    return wxBase;
	}

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }
	
}
