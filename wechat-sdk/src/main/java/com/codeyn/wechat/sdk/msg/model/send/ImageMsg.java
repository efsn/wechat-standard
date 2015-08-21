package com.codeyn.wechat.sdk.msg.model.send;

import com.codeyn.wechat.sdk.msg.enums.MsgType;
import com.codeyn.wechat.sdk.msg.model.receive.ReceivedMsg;

/**
	回复图片消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[image]]></MsgType>
		<Image>
			<MediaId><![CDATA[media_id]]></MediaId>
		</Image>
	</xml>
 */
public class ImageMsg extends SentMsg {
	
	private String mediaId;
	
	public ImageMsg() {
		setMsgType(MsgType.IMAGE.getFlag());
	}
	
	public ImageMsg(ReceivedMsg inMsg) {
		super(inMsg);
		setMsgType(MsgType.IMAGE.getFlag());
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
