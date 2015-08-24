package com.codeyn.wechat.wc.template;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class CouponUseMsgSender extends AbstractWxMsgSender {

	private String originalTemplateId = "OPENTM202243887";

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

		// 添加keyword1节点
		JSONObject keyword1 = new JSONObject();
		keyword1.put("color", "#0000ff");
		keyword1.put("value", params.get("keyword1"));
		data.put("keyword1", keyword1);

		// 添加keyword2节点
		JSONObject keyword2 = new JSONObject();
		keyword2.put("color", "#0000ff");
		keyword2.put("value", params.get("keyword2"));
		data.put("keyword2", keyword2);

		// 添加keyword3节点
		JSONObject keyword3 = new JSONObject();
		keyword3.put("color", "#0000ff");
		keyword3.put("value", params.get("keyword3"));
		data.put("keyword3", keyword3);

		// 添加Remark节点
		JSONObject remark = new JSONObject();
		remark.put("color", "#0000ff");
		remark.put("value", params.get("remark"));
		data.put("remark", remark);

		return json.toJSONString();
	}

}
