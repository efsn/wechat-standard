package com.codeyn.wechat.service.msg;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.sdk.base.WcClientFactory;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.material.result.Media;
import com.codeyn.wechat.sdk.msg.CustomerServiceClient;
import com.codeyn.wechat.sdk.msg.MassClient;
import com.codeyn.wechat.sdk.msg.enums.MsgType;
import com.codeyn.wechat.service.material.NewsArticleService;
import com.codeyn.wechat.service.material.NewsService;
import com.codeyn.wechat.wc.enums.WcMaterialType;
import com.codeyn.wechat.wc.model.material.NewsArticle;
import com.codeyn.wechat.wc.model.msg.WcEvent;
import com.codeyn.wechat.wc.model.msg.WcMsg;
import com.codeyn.wechat.wc.utils.WcCache;

/**
 * 向微信服务器发送消息
 * 
 * @author Arthur
 *
 */
public class MsgService {

    public static WcResult send(Object eventId, String groupId, String tenantId) {
        WcResult result = null;
        String accessToken = WcCache.getAccessToken(tenantId);
        WcEvent event = WcEvent.dao.findById(eventId);

        MassClient client = WcClientFactory.getClient(MassClient.class, WcCache.getWxBase(tenantId));
        Map<String, Object> data = new HashMap<>();
        switch (WcMaterialType.get(event.getStr("reply_type"))) {
        // 文字
            case TEXT: {
                data.put("content", event.getStr("content"));
                result = client.massMsg(accessToken, groupId, MassService.MASS_TEXT, data);
                break;
            }

            // 图文
            case NEWS: {
                Media media = NewsService.uploadNews(tenantId, event.getInt("content"));
                data.put("media_id", media.getMedia_id());
                result = client.massMsg(accessToken, groupId, MassService.MASS_MPNEWS, data);
                break;
            }
            default:
        }

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(result, tenantId);
        return result;
    }

    public static WcResult sendKfMsg(String tenantId, String toUser, MsgType type, Map<String, Object> data) {
        String accessToken = WcCache.getAccessToken(tenantId);
        CustomerServiceClient client = WcClientFactory.getClient(CustomerServiceClient.class,
                WcCache.getWxBase(tenantId));
        WcResult rs = client.sendMsg(accessToken, toUser, type, data);

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(rs, tenantId);
        return rs;
    }

    public static WcResult sendKfTextMsg(String toUser, String content, String tenantId) {
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        WcResult rs = sendKfMsg(tenantId, toUser, MsgType.TEXT, map);

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(rs, tenantId);
        return rs;
    }

    /**
     * 发送48小时内的客服消息
     */
    public static String sendKf48Msgs(String tenantId, Integer newsId) {
        StringBuilder sb = new StringBuilder();

        Timestamp startTime = new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2);
        List<WcMsg> listUser = WcMsg.dao.getAccessUser(tenantId, startTime);
        int allCount = listUser.size();
        sb.append("48内交互的交互的用户总有：" + allCount + "个<br/>");
        int countError = 0;

        List<NewsArticle> list = NewsArticleService.getArticles(newsId);
        Map<String, Object> data = new HashMap<>();
        data.put("articles", getNewsArticlesJson(list, WcCache.getWxBase(tenantId).getDomain()));

        for (WcMsg wxMsg : listUser) {
            WcResult result = sendKfMsg(tenantId, wxMsg.getStr("fromuser"), MsgType.NEWS, data);
            countError = result == null || !result.isSuccess() ? countError + 1 : countError;
        }

        sb.append("成功发送：" + (allCount - countError) + "个<br/>");
        sb.append("失败：" + countError + "个");
        return sb.toString();
    }

    public static JSONArray getNewsArticlesJson(List<NewsArticle> list, String urlPre) {
        JSONArray array = new JSONArray();
        for (NewsArticle detail : list) {
            JSONObject tempObj = new JSONObject();
            tempObj.put("title", detail.getStr("title"));
            tempObj.put("description", detail.getStr("digest"));
            tempObj.put("url", urlPre + "/msg/index?newsId=" + detail.getInt("id"));
            tempObj.put("picurl", detail.getStr("cover_pic_url"));
            array.add(tempObj);
        }
        return array;
    }

}
