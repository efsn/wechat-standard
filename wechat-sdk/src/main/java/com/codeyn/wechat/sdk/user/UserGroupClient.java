package com.codeyn.wechat.sdk.user;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.user.result.WcUserGroup;

/**
 * 微信用户分组管理
 */
public class UserGroupClient extends WcClient{
	
	public UserGroupClient(WcBase wxBase) {
        super(wxBase);
    }

    /**
     * 创建分组
     */
	public WcUserGroup createGroup(String accessToken, final String name) {
	    return doPost("group", WcUserGroup.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                JSONObject group = new JSONObject();
                group.put("name", name);
                json.put("group", group);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/groups/create?access_token=" + accessToken);
	}
	
	/**
	 * 查询所有分组
	 */
	public WcUserGroup getUserInfos(final String accessToken) {
	    return doGet(WcUserGroup.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/cgi-bin/groups/get");
	}
	
	/**
	 * 查询用户所在分组
	 */
	public WcUserGroup getGroupId(String accessToken, final String openId) {
        return doPost(WcUserGroup.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("openid", openId);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/groups/getid?access_token=" + accessToken);
    }
	
	/**
     * 修改分组名
     */
    public WcResult updateGroup(String accessToken, final Integer groupId, final String name) {
        return doPost(WcResult.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                JSONObject group = new JSONObject();
                group.put("id", groupId);
                group.put("name", name);
                json.put("group", group);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/groups/update?access_token=" + accessToken);
    }
	
    /**
     * 移动用户分组
     */
    public WcResult moveUser(String accessToken, final Integer toGroupId, final String openId) {
        return doPost(WcResult.class, new ParamService(){
            
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("to_groupid", toGroupId);
                json.put("openid", openId);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/groups/members/update?access_token=" + accessToken);
    }
    
    /**
     * 批量移动用户分组
     */
    public WcResult batchMoveUser(String accessToken, final Integer toGroupId, final List<String> openIds) {
        return doPost(WcResult.class, new ParamService(){
            
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("to_groupid", toGroupId);
                JSONArray list = new JSONArray();
                list.addAll(openIds);
                json.put("openid_list", list);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/groups/members/batchupdate?access_token=" + accessToken);
    }
    
    /**
     * 删除分组
     */
    public WcResult deleteGroup(String accessToken, final Integer groupId) {
        return doPost(WcResult.class, new ParamService(){
            
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                JSONObject group = new JSONObject();
                group.put("id", groupId);
                json.put("group", group);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/groups/delete?access_token=" + accessToken);
    }
	
}
