package com.codeyn.wechat.sdk.msg.model.send;

import com.codeyn.wechat.sdk.msg.enums.MsgType;
import com.codeyn.wechat.sdk.msg.model.receive.ReceivedMsg;

/**
	回复视频消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[video]]></MsgType>
		<Video>
			<MediaId><![CDATA[media_id]]></MediaId>
			<Title><![CDATA[title]]></Title>
			<Description><![CDATA[description]]></Description>
		</Video>
	</xml>
 */
public class VideoMsg extends SentMsg {
	
	private String mediaId;
	private String title;
	private String description;
	
	public VideoMsg() {
		setMsgType(MsgType.SHORT_VIDEO.getFlag());
	}
	
	public VideoMsg(ReceivedMsg inMsg) {
		super(inMsg);
		setMsgType(MsgType.SHORT_VIDEO.getFlag());
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
