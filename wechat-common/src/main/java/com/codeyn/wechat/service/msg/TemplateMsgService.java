package com.codeyn.wechat.service.msg;

import java.sql.Timestamp;

import com.codeyn.wechat.wc.model.msg.TemplateMsg;

public class TemplateMsgService {

    public static TemplateMsg getTemplateMsgByOriginalId(String tenantId, String originalId) {
        return TemplateMsg.dao.getTemplateMsgByOriginalId(tenantId, originalId);
    }

    public static void saveTemplateMsg(String tenantId, String originalId, String templateId, String description) {
        TemplateMsg msg = new TemplateMsg();
        msg.set("tenant_id", tenantId);
        msg.set("original_id", originalId);
        msg.set("template_id", templateId);
        msg.set("description", description);
        msg.set("create_time", new Timestamp(System.currentTimeMillis()));
        boolean flag = msg.save();
        if (!flag) {
            throw new RuntimeException("保存失败！");
        }
    }

}
