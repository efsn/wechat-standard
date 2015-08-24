package com.codeyn.wechat.sdk.msg.result;

import com.codeyn.wechat.sdk.base.model.WcResult;

public class TemplateMsgResult extends WcResult{
	
    /**
     * 模版Id
     */
    private String template_id;
    
    /**
     * 模版消息Id
     */
    private String msgid;
    
    public String getTemplate_id() {
        return template_id;
    }
    
    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
    
    public String getMsgid() {
        return msgid;
    }
    
    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
    
}
