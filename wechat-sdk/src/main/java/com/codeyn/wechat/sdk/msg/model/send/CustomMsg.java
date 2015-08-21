package com.codeyn.wechat.sdk.msg.model.send;

import com.codeyn.wechat.sdk.msg.model.receive.ReceivedMsg;

/**
    转发多客服消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[transfer_customer_service]]></MsgType>
	</xml>
 */
public class CustomMsg extends SentMsg {

	public CustomMsg() {
		setMsgType("transfer_customer_service");
	}

	public CustomMsg(ReceivedMsg inMsg) {
		super(inMsg);
		setMsgType("transfer_customer_service");
	}
	
}
