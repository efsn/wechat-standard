package com.codeyn.wechat.api.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.codeyn.base.result.BaseResult;

/**
 * 
 * @author Codeyn
 *
 */
public class WcApiClient{
    private static Logger logger = LoggerFactory.getLogger(WcApiClient.class);

    private SysBase base;
    
    public WcApiClient(SysBase base){
        this.base = base;
    }

    protected <T extends BaseResult> T doService(Class<T> clazz, ParamService paramService, String url){
        Map<String, Object> map = new HashMap<String, Object>();

        // 系统通用参数设置
        map.put("name", base.getName());

        paramService.init(map);

        String postUrl = HttpUtil.postUrl(base.getHost() + url, map);
        logger.debug(postUrl);
        return StringUtils.isBlank(postUrl) ? null : JSON.parseObject(postUrl, clazz);
    }

    public SysBase getBase(){
        return base;
    }

    public interface ParamService{
        public void init(Map<String, Object> map);
    }

}
