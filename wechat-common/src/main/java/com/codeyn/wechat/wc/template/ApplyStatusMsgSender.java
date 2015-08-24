package com.codeyn.wechat.wc.template;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class ApplyStatusMsgSender extends AbstractWxMsgSender {

	private String originalTemplateId = "TM00574";

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

		// 添加keynote1节点
		JSONObject keynote1 = new JSONObject();
		keynote1.put("color", "#0000ff");
		keynote1.put("value", params.get("keynote1"));
		data.put("keynote1", keynote1);

		// 添加keynote2节点
		JSONObject keynote2 = new JSONObject();
		keynote2.put("color", "#0000ff");
		keynote2.put("value", params.get("keynote2"));
		data.put("keynote2", keynote2);

		// 添加Remark节点
		JSONObject remark = new JSONObject();
		remark.put("color", "#0000ff");
		remark.put("value", params.get("remark"));
		data.put("remark", remark);

		return json.toJSONString();
	}

}
