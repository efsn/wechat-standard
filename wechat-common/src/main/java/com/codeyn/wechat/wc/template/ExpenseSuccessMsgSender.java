package com.codeyn.wechat.wc.template;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 消费成功提醒
 * @author Arthur
 *
 */
public class ExpenseSuccessMsgSender extends AbstractWxMsgSender {

	private String originalTemplateId = "TM00504";

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
        JSONObject time = new JSONObject();
        time.put("color", "#0000ff");
        time.put("value", params.get("time"));
        data.put("time", time);
        
        // 添加Account节点
        JSONObject org = new JSONObject();
        org.put("color", "#0000ff");
        org.put("value", params.get("org"));
        data.put("org", org);

		// 添加change节点
		JSONObject type = new JSONObject();
		type.put("color", "#0000ff");
		type.put("value", params.get("type"));
		data.put("type", type);

		// 添加creditChange节点
		JSONObject money = new JSONObject();
		money.put("color", "#0000ff");
		money.put("value", params.get("money"));
		data.put("money", money);

		// 添加creditTotal节点
		JSONObject point = new JSONObject();
		point.put("color", "#0000ff");
		point.put("value", params.get("point"));
		data.put("point", point);

		// 添加Remark节点
		JSONObject remark = new JSONObject();
		remark.put("color", "#0000ff");
		remark.put("value", params.get("remark"));
		data.put("remark", remark);

		return json.toJSONString();
	}
}
