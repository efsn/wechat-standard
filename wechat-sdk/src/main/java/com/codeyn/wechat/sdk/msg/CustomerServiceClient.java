package com.codeyn.wechat.sdk.msg;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.msg.enums.MsgType;
import com.codeyn.wechat.sdk.msg.result.CustomerService;

/**
 * 多客服功能</br> 
 * 必须先在公众平台官网为公众号设置微信号后才能使用该能力
 */
public class CustomerServiceClient extends WcClient{
    
    public CustomerServiceClient(WcBase wxBase) {
        super(wxBase);
    }

    public WcResult saveAccount(String accessToken, boolean isNew, final String account, final String name, final String pw){
        return doPost(WcResult.class, new ParamService() {
            
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("kf_account", account);
                json.put("nickname", name);
                json.put("password", pw);
                map.put(KEY, json.toJSONString());
            }
            
        }, (isNew ? "/customservice/kfaccount/add?access_token=" : "/customservice/kfaccount/update?access_token=") + accessToken);
    }
    
    /**
     * 删除客服帐号
     * 避免@被encoding
     */
    public WcResult deleteAccount(final String accessToken, String account){
        return doGet(WcResult.class, new ParamService() {
            
            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/customservice/kfaccount/del?kf_account=" + account);
    }

    /**
     * 获取所有客服账号
     */
    public CustomerService getAccountList(boolean isOnline, final String accessToken) {
        return doGet(CustomerService.class, new ParamService() {
            
            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/cgi-bin/customservice/" + (isOnline ? "getonlinekflist" : "getkflist"));
    }
    
    /**
     * 发送客服消息
     */
    public WcResult sendMsg(String accessToken, final String toUser, final MsgType type, final Map<String, Object> data){
        return doPost(WcResult.class, new ParamService() {
            
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("touser", toUser);
                json.put("msgtype", type.getFlag());
                
                JSONObject msg = new JSONObject();
                msg.putAll(data);
                json.put(type.getFlag(), msg);
                
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/message/custom/send?access_token=" + accessToken);
    }
    
    
    /**
     * 获取客服聊天记录
     */
    public WcResult getChatRecords(String accessToken, final String json) {
        return doPost(WcResult.class, new ParamService() {
            
            @Override
            public void init(Map<String, String> map) {
                map.put(KEY, json);
            }
            
        }, "/customservice/msgrecord/getrecord?access_token=" + accessToken);
    }
}
