package com.codeyn.wechat.wc.template;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class PointChangeMsgSender extends AbstractWxMsgSender {

	private String originalTemplateId = "TM00230";

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
		
		// 添加FieldName节点
        JSONObject fieldName = new JSONObject();
        fieldName.put("color", "#0000ff");
        fieldName.put("value", params.get("FieldName"));
        data.put("FieldName", fieldName);
        
        // 添加Account节点
        JSONObject account = new JSONObject();
        account.put("color", "#0000ff");
        account.put("value", params.get("Account"));
        data.put("Account", account);

		// 添加change节点
		JSONObject change = new JSONObject();
		change.put("color", "#0000ff");
		change.put("value", params.get("change"));
		data.put("change", change);

		// 添加creditChange节点
		JSONObject creditChange = new JSONObject();
		creditChange.put("color", "#0000ff");
		creditChange.put("value", params.get("CreditChange"));
		data.put("CreditChange", creditChange);

		// 添加creditTotal节点
		JSONObject creditTotal = new JSONObject();
		creditTotal.put("color", "#0000ff");
		creditTotal.put("value", params.get("CreditTotal"));
		data.put("CreditTotal", creditTotal);

		// 添加Remark节点
		JSONObject remark = new JSONObject();
		remark.put("color", "#0000ff");
		remark.put("value", params.get("Remark"));
		data.put("Remark", remark);

		return json.toJSONString();
	}
}
