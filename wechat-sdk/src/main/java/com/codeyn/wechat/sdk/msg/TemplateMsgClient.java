package com.codeyn.wechat.sdk.msg;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.msg.result.TemplateMsgResult;


/**
 * 模板消息
 * @author Arthur
 */
public class TemplateMsgClient extends WcClient{
	
	public TemplateMsgClient(WcBase wxBase) {
        super(wxBase);
    }

	/**
	 * 发送模板消息
	 */
	public TemplateMsgResult send(String accessToken, final String jsonStr) {
	    return doPost(TemplateMsgResult.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put(KEY, jsonStr);
            }
	        
	    }, "/cgi-bin/message/template/send?access_token=" + accessToken);
	}
	
	/**
	 * 从模版库添加模版
	 */
	public TemplateMsgResult apply(String accessToken, final String shortId){
	    return doPost(TemplateMsgResult.class, new ParamService(){
	        
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("template_id_short", shortId);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/message/template/send?access_token=" + accessToken);
	}
	
	/**
	 * 设置所属行业
	 * @param id1/id2 所属行业编号
	 */
	public TemplateMsgResult setIndustry(String accessToken, final String id1, final String id2){
	    return doPost(TemplateMsgResult.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("industry_id1", id1);
                json.put("industry_id2", id2);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/template/api_set_industry?access_token=" + accessToken);
	}
	
}
