package com.codeyn.wechat.sdk.msg;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.msg.result.MsgResult;

/**
 * @author Arthur
 *
 */
public class MassClient extends WcClient {

    public MassClient(WcBase wxBase) {
        super(wxBase);
    }
    
    /**
     * 根据分组进行群发
     */
    public MsgResult massMsg(String accessToken, final String groupId, final String type, final Map<String, Object> data){
        return doPost(MsgResult.class, new ParamService() {
            
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                
                JSONObject filter = new JSONObject();
                filter.put("is_to_all", StringUtils.isBlank(groupId));
                if(StringUtils.isNotBlank(groupId)){
                    filter.put("group_id", groupId);
                }
                json.put("filter", filter);
                
                json.put("msgtype", type);
                JSONObject msg = new JSONObject();
                msg.putAll(data);
                json.put(type, msg);
                
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/message/mass/sendall?access_token=" + accessToken);
    }
    
    /**
     * 根据OpenId列表进行群发</br>
     * 视频的media_id需要调用{@link #uploadVideo(String, String, String) uploadVideo}获取
     * 
     * @param toUser OpenID最少2个，最多10000个
     */
    public MsgResult sendMsg(String accessToken, final List<String> toUser, final String type, final Map<String, Object> data){
        return doPost(MsgResult.class, new ParamService() {
            
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                
                JSONArray users = new JSONArray();
                users.addAll(toUser);
                json.put("touser", users);
                
                json.put("msgtype", type);
                JSONObject msg = new JSONObject();
                msg.putAll(data);
                json.put(type, msg);
                
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/message/mass/send?access_token=" + accessToken);
    }
    
    /**
     * 1、只有已经发送成功的消息才能删除
     * 2、删除消息是将消息的图文详情页失效，已经收到的用户，还是能在其本地看到消息卡片
     * 3、删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除
     * 4、如果多次群发发送的是一个图文消息，那么删除其中一次群发，就会删除掉这个图文消息并导致所有群发都失效
     */
    public WcResult deleteMass(String accessToken, final String msgId){
        return doPost(WcResult.class, new ParamService() {
            
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("msg_id", msgId);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/message/mass/delete?access_token=" + accessToken);
    }
    
    /**
     * 群发预览
     * 
     * 在保留对openID预览能力的同时，可以对指定微信号发送预览的能力（100次/日）
     * 
     * @param touser towxname和touser同时赋值时，以towxname优先
     */
    public MsgResult preview(String accessToken, final String touser, final String towxname, final String type, final Map<String, Object> data){
        return doPost(MsgResult.class, new ParamService() {
            
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                if(StringUtils.isNotBlank(touser)){
                    json.put("touser", touser);
                }
                
                if(StringUtils.isNotBlank(towxname)){
                    json.put("towxname", towxname);
                }
                
                json.put("msgtype", type);
                JSONObject msg = new JSONObject();
                msg.putAll(data);
                json.put(type, msg);
                
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/message/mass/preview?access_token=" + accessToken);
    }
    
    /**
     * 查询群发消息发送状态
     */
    public MsgResult checkStatus(String accessToken, final String msgId){
        return doPost(MsgResult.class, new ParamService() {
            
            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("msg_id", msgId);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/message/mass/get?access_token=" + accessToken);
    }
    
}
