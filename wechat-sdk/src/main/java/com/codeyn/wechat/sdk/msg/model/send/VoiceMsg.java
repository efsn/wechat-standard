package com.codeyn.wechat.sdk.msg.model.send;

import com.codeyn.wechat.sdk.msg.enums.MsgType;
import com.codeyn.wechat.sdk.msg.model.receive.ReceivedMsg;

/**
	回复语音消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[voice]]></MsgType>
		<Voice>
			<MediaId><![CDATA[media_id]]></MediaId>
		</Voice>
	</xml>
 */
public class VoiceMsg extends SentMsg {
			
	private String mediaId;
	
	public VoiceMsg() {
		setMsgType(MsgType.VOICE.getFlag());
	}
	
	public VoiceMsg(ReceivedMsg inMsg) {
		super(inMsg);
		setMsgType(MsgType.VOICE.getFlag());
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
