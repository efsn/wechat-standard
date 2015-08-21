package com.codeyn.wechat.sdk.msg.enums;


/**
 * 消息类型
 * @author Arthur
 *
 */
public enum MsgType {

    /**
     * Blow is commons
     */
    TEXT("text", "文本消息"),
    
    IMAGE("image", "图片消息"),
    
    VOICE("voice", "语音消息"),
    
    VIDEO("video", "视频消息"),
    
    /**
     * Blow is receive msg
     */
    SHORT_VIDEO("shortvideo", "小视频消息"),
    
    LOCATION("location", "地址位置消息"),
    
    LINK("link", "链接消息"),
    
    EVENT("event", "事件"),
    
    /**
     * Blow is send msg
     */
    NEWS("news", "图文消息"),
    
    MUSIC("music", "音乐消息");
    
    private MsgType(String flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    private String flag;

    private String msg;

    public String getFlag() {
        return flag;
    }

    public String getMsg() {
        return msg;
    }

    public static MsgType get(String flag) {
        for (MsgType type : MsgType.values()) {
            if (type.flag.equals(flag)) {
                return type;
            }
        }
        return null;
    }
    
}
