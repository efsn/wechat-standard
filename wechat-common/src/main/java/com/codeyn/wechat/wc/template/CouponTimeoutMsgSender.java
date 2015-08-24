package com.codeyn.wechat.wc.template;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class CouponTimeoutMsgSender extends AbstractWxMsgSender{
	
	private static final String originalTemplateId = "TM00853";
	
	@Override
    protected String getOriginalMsgTemplateId(String tenantId) {
        return originalTemplateId;
    }

    @Override
    protected String assembleMsg(String templateId, String openId, Map<String, Object> params) {
        JSONObject json = new JSONObject();
        json.put("touser", openId);
        json.put("template_id", templateId);
        json.put("topcolor", "#FF0000");

        JSONObject data = new JSONObject();
        json.put("data", data);

        // 添加first节点
        JSONObject first = new JSONObject();
        first.put("color", "#0000ff");
        first.put("value", params.get("first"));
        data.put("first", first);

        // 添加orderTicketStore节点
        JSONObject orderTicketStore = new JSONObject();
        orderTicketStore.put("color", "#0000ff");
        orderTicketStore.put("value", params.get("orderTicketStore"));
        data.put("orderTicketStore", orderTicketStore);

        // 添加orderTicketRule节点
        JSONObject orderTicketRule = new JSONObject();
        orderTicketRule.put("color", "#0000ff");
        orderTicketRule.put("value", params.get("orderTicketRule"));
        data.put("orderTicketRule", orderTicketRule);

        // 添加remark节点
        JSONObject remark = new JSONObject();
        remark.put("color", "#0000ff");
        remark.put("value", params.get("remark"));
        data.put("remark", remark);

        return json.toJSONString();
    }

}
