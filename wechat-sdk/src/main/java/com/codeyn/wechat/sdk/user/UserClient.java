package com.codeyn.wechat.sdk.user;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.user.result.UserInfoList;
import com.codeyn.wechat.sdk.user.result.WcFollowUser;
import com.codeyn.wechat.sdk.user.result.WcUser;

/**
 * 微信用户管理
 */
public class UserClient extends WcClient{
	
	public UserClient(WcBase wxBase) {
        super(wxBase);
    }

    /**
     * 获取用户基本信息
     */
	public WcUser getUserInfo( final String accessToken, final String openId, final String language) {
	    return doGet(WcUser.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
                map.put("openid", openId);
                map.put("lang", language);
            }
            
        }, "/cgi-bin/user/info");
	}
	
	/**
	 * 批量获取用户基本信息
	 */
	public UserInfoList getUserInfos(String accessToken, final String language ,final String... openIds) {
	    return doPost(UserInfoList.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                JSONArray arr = new JSONArray();
                if(openIds != null){
                    for(String openId : openIds){
                        JSONObject json = new JSONObject();
                        json.put("openid", openId);
                        json.put("lang", language);
                    }
                }
                map.put(KEY, arr.toJSONString());
            }
            
        }, "/cgi-bin/user/info/batchget?access_token=" + accessToken);
	}
	
	/**
	 * 设置备注名
	 */
	public WcResult updateRemark(String accessToken, final String openId ,final String remark) {
	    return doPost(WcResult.class, new ParamService(){
	        
	        @Override
	        public void init(Map<String, String> map) {
	            JSONObject json = new JSONObject();
                json.put("openid", openId);
                json.put("remark", remark);
	            map.put(KEY, json.toJSONString());
	        }
	        
	    }, "/cgi-bin/user/info/updateremark?access_token=" + accessToken);
	}
	
	/**
	 * 获取关注用户列表
	 * 
	 * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
	 */
	public WcFollowUser getUserInfo( final String accessToken, final String nextOpenId) {
        return doGet(WcFollowUser.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
                map.put("next_openid", nextOpenId);
            }
            
        }, "/cgi-bin/user/get");
    }
	
}
