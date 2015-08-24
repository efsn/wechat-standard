package com.codeyn.wechat.wc.template;

import java.util.Map;

import com.codeyn.wechat.sdk.msg.result.TemplateMsgResult;

/**
 * 模版消息发送者统一接口
 * 
 * @author Arthur
 *
 */
public interface TemplateMsgSender {
    /**
     * 发送微信消息
     */
    public TemplateMsgResult sendMsg(String tenantId, String memberId, Map<String, Object> params);
}