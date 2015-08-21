package com.codeyn.wechat.sdk.account;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.sdk.account.model.Qrcode;
import com.codeyn.wechat.sdk.base.WxClient;
import com.codeyn.wechat.sdk.base.model.WxBase;
import com.codeyn.wechat.sdk.base.model.WxResult;

/**
 * 生成带参数的二维码
 */
public class QrcodeClient extends WxClient{
    
    public QrcodeClient(WxBase wxBase) {
        super(wxBase);
    }

    /**
     * 创建二维码
     * @param scene 临时是Integer，永久是String
     */
    public Qrcode create(String accessToken, final Object scene, final boolean isForever) {
        return doPost(Qrcode.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                JSONObject actionInfo = new JSONObject();
                JSONObject scene = new JSONObject();
                //有效时间，最大不超过604800秒（即7天）
                if(!isForever){
                    json.put("expire_seconds", 604800);
                    json.put("action_name", "QR_SCENE");
                    scene.put("scene_id", scene);
                }else{
                    json.put("action_name", "QR_LIMIT_STR_SCENE");
                    scene.put("scene_str", scene);
                }
                actionInfo.put("scene", scene);
                json.put("action_info", actionInfo);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/qrcode/create?access_token=" + accessToken);
    }
    
    /**
     * 获取二维码
     */
    public WxResult showQrcode(final String ticket) {
        return doService(WxResult.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                try {
                    map.put("ticket", URLEncoder.encode(ticket, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error("url encode error", e);;
                }
            }
            
        }, "https://mp.weixin.qq.com/cgi-bin/showqrcode");
    }
    
    /**
     * 长链接转短链接接口
     */
    public Qrcode longToShort(String accessToken, final String longUrl) {
        return doPost(Qrcode.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("action", "long2short");
                json.put("long_url", longUrl);
                map.put(KEY, json.toJSONString());
            }
            
        }, "/cgi-bin/shorturl?access_token=" + accessToken);
    }
    
}
