package com.codeyn.wechat.sdk.msg.model.send;

import com.codeyn.wechat.sdk.msg.enums.MsgType;
import com.codeyn.wechat.sdk.msg.model.receive.ReceivedMsg;


/**
	回复文本消息
	<xml>
	<ToUserName><![CDATA[toUser]]></ToUserName>
	<FromUserName><![CDATA[fromUser]]></FromUserName>
	<CreateTime>12345678</CreateTime>
	<MsgType><![CDATA[text]]></MsgType>
	<Content><![CDATA[你好]]></Content>
	</xml>

 */
public class SentMsg {
	
	// 接收方帐号（收到的OpenID）
	private String toUserName;
	
	// 开发者微信号
	private String fromUserName;
	
	// 消息创建时间 （整型）
	private Long createTime;
	
	/**
	 * 被动响应消息类型
	 * 1：text 文本消息
	 * 2：image 图片消息
	 * 3：voice 语音消息
	 * 4：video 视频消息
	 * 5：music 音乐消息
	 * 6：news 图文消息
	 */
	private String msgType = MsgType.TEXT.getFlag();
	
	private String content;
	
	/**
	 * 用接收到的消息初始化要发出去的消息
	 */
	public SentMsg(ReceivedMsg inMsg) {
        this.toUserName = inMsg.getFromUserName();
        this.fromUserName = inMsg.getToUserName();
        this.createTime = now();
    }
	
	public SentMsg() {
		
	}
	
	public Long now() {
		return System.currentTimeMillis() / 1000;
	}
	
	public String toXml(){
	    return "";
	}
	
	public String getToUserName() {
		return toUserName;
	}
	
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
	public String getFromUserName() {
		return fromUserName;
	}
	
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	public Long getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	public String getMsgType() {
		return msgType;
	}
	
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
