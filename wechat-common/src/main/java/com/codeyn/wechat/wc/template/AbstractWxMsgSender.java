package com.codeyn.wechat.wc.template;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeyn.base.common.Assert;
import com.codeyn.wechat.sdk.base.WcClientFactory;
import com.codeyn.wechat.sdk.msg.TemplateMsgClient;
import com.codeyn.wechat.sdk.msg.result.TemplateMsgResult;
import com.codeyn.wechat.service.msg.TemplateMsgService;
import com.codeyn.wechat.wc.model.msg.TemplateMsg;
import com.codeyn.wechat.wc.utils.WcCache;

public abstract class AbstractWxMsgSender implements TemplateMsgSender {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract String getOriginalMsgTemplateId(String tenantId);

    /**
     * 根据广场获取消息模板ID
     */
    private String getMsgTemplateId(String tenantId) {
        String originalTemplateId = getOriginalMsgTemplateId(tenantId);

        // 先从数据库中获取模板ID
        TemplateMsg templateMsg = TemplateMsgService.getTemplateMsgByOriginalId(tenantId, originalTemplateId);
        if (templateMsg != null) {
            return templateMsg.getStr("template_id");
        }

        // 从微信服务器获取对应的模板ID
        String accessToken = WcCache.getAccessToken(tenantId);
        TemplateMsgClient client = WcClientFactory.getClient(TemplateMsgClient.class, WcCache.getWxBase(tenantId));
        TemplateMsgResult result = client.apply(accessToken, originalTemplateId);
        String templateId = result.getTemplate_id();
        TemplateMsgService.saveTemplateMsg(tenantId, originalTemplateId, templateId, "");
        return templateId;
    }

    @Override
    public TemplateMsgResult sendMsg(String tenantId, String openId, Map<String, Object> params) {
        // 封装消息
        String templateId = getMsgTemplateId(tenantId);
        Assert.hasText(openId, "the openId searched by memberId");
        String msg = assembleMsg(templateId, openId, params);

        // 发送消息
        TemplateMsgClient client = WcClientFactory.getClient(TemplateMsgClient.class, WcCache.getWxBase(tenantId));
        return client.send(WcCache.getAccessToken(tenantId), msg);
    }

    /**
     * 封装消息
     */
    protected abstract String assembleMsg(String templateId, String openId, Map<String, Object> params);

}