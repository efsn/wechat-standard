package com.codeyn.wechat.service.msg;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.codeyn.wechat.sdk.msg.enums.MsgType;
import com.codeyn.wechat.sdk.msg.model.receive.EventMsg;
import com.codeyn.wechat.sdk.msg.model.receive.NormalMsg;
import com.codeyn.wechat.sdk.msg.model.receive.ReceivedMsg;
import com.codeyn.wechat.sdk.msg.model.send.NewsMsg;
import com.codeyn.wechat.sdk.msg.model.send.SentMsg;
import com.codeyn.wechat.sdk.msg.utils.MsgSentBuilder;
import com.codeyn.wechat.service.material.NewsArticleService;
import com.codeyn.wechat.wc.enums.WcEventType;
import com.codeyn.wechat.wc.enums.WcMaterialType;
import com.codeyn.wechat.wc.model.material.NewsArticle;
import com.codeyn.wechat.wc.model.msg.WcEvent;
import com.codeyn.wechat.wc.model.msg.WcMsg;
import com.codeyn.wechat.wc.utils.WcCache;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author Arthur
 */
public class WcMsgService {

    private static final String defaultOutMsg = "欢迎光临";

    /**
     * 被动回复（暂时只支持文本Key）
     */
    public static SentMsg getSentMsg(ReceivedMsg msg, String tenantId, WcEventType type) {
        WcEvent response = null;
        if (msg instanceof NormalMsg) {
            String content = ((NormalMsg) msg).getContent();
            response = WcEventService.getEventByContentKey(tenantId, type, content);
        } else {
            response = WcEventService.getEventByEventType(tenantId, type);
        }

        // 如果根据关键字没有匹配上的话就获取该广场默认的回复事件
        if (response == null) {
            response = WcEventService.getDefaultMsg(tenantId);
        }

        SentMsg sentMsg = null;
        if (response == null) {
            sentMsg = new SentMsg(msg);
            sentMsg.setContent(defaultOutMsg);
        } else {
            MsgType msgType = MsgType.get(WcMaterialType.get(response.getStr("reply_type")).name().toLowerCase());
            sentMsg = MsgSentBuilder.getSentMsg(msg, msgType);
            preSentMsg(response, sentMsg, tenantId);
        }
        return sentMsg;
    }

    /**
     * 给用户发送客服消息。
     */
    private static void preSentMsg(WcEvent event, SentMsg sentMsg, String tenantId) {
        switch (WcMaterialType.get(event.getStr("reply_type"))) {

        // 文字
            case TEXT:
                sentMsg.setContent(event.getStr("content"));
                break;

            // 图文,若果是图文就直接发送，jfianl 发送的
            case NEWS:
                List<NewsArticle> list = NewsArticleService.getArticles(event.getInt("content"));
                NewsMsg newsMsg = (NewsMsg) sentMsg;

                String domain = WcCache.getWxBase(tenantId).getDomain();
                for (NewsArticle detail : list) {
                    newsMsg.addNews(detail.getStr("title"), detail.getStr("digest"), detail.getStr("cover_pic_url"),
                            domain.concat("/msg/index?newsId=") + detail.getInt("id"));
                }

                break;

            default:
        }
    }

    /**
     * TODO 事件推送消息
     */
    public static void saveEventMsg(EventMsg msg, String tenantId) {
        WcMsg wxMsg = new WcMsg();
        if (msg.getMsgId() != null) {
            wxMsg.set("msg_id", msg.getMsgId().toString());
        }

        wxMsg.set("msg_content", msg.getEventKey());
        wxMsg.set("tenant_id", tenantId);
        wxMsg.set("fromuser", msg.getFromUserName());
        wxMsg.set("touser", msg.getToUserName());
        wxMsg.set("msg_type", msg.getMsgType());
        wxMsg.set("replied", 0);
        wxMsg.set("create_time", new Timestamp(msg.getCreateTime() * 1000));
        wxMsg.save();
    }

    public static void saveNormalMsg(NormalMsg msg, String tenantId) {
        WcMsg wxMsg = new WcMsg();
        if (msg.getMsgId() != null) {
            wxMsg.set("msg_id", msg.getMsgId().toString());
        }
        if (StringUtils.isNotBlank(msg.getContent())) {
            wxMsg.set("msg_content", msg.getContent());
        }
        wxMsg.set("tenant_id", tenantId);
        wxMsg.set("fromuser", msg.getFromUserName());
        wxMsg.set("touser", msg.getToUserName());
        wxMsg.set("msg_type", msg.getMsgType());
        wxMsg.set("replied", 0);
        wxMsg.set("create_time", new Timestamp(msg.getCreateTime() * 1000));
        wxMsg.save();
    }

    /**
     * 分页查询
     */
    public static Page<WcMsg> getPagationList(String tenantId, int pageNumber, int pageSize, String contentKeys) {
        return WcMsg.dao.getPagationList(tenantId, pageNumber, pageSize, contentKeys);
    }

}
