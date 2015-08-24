package com.codeyn.wechat.wc.enums;

/**
 * 微信事件类型
 * @author Arthur
 *
 */
public enum WcEventType {
	
	DEFAULT(0, "默认回复"),
	
	SUBSTRIBE(1, "关注"),
	
	SENDTEXT(2, "文字消息"),
	
	SENDIMAGE(3, "图片消息"),
	
	SENDMUTIPLE(4, "图文消息"),
	
	VOICE(5, "语音消息"),
	
	SENDVIDEO(6, "视频消息"),
	
	LOCATION(7, "地理位置"),
	
	MESSAGE(8, "推广消息"),
	
	ROBOT(9, "被动回复机器人");
	
	private Integer flag;
    private String name;
    
    private WcEventType(Integer flag, String name){
        this.flag = flag;
        this.name = name;
    }
    
    public static WcEventType get(Integer flag){
        for(WcEventType type : values()){
            if(type.getFlag().equals(flag)){
                return type;
            }
        }
        return null;
    }

    public Integer getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }
	
}
