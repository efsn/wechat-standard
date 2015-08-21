package com.codeyn.wechat.sdk.msg.enums;

/**
 * 事件推送类型
 * @author Arthur
 *
 */
public enum EventType {
    
    /**
     * 客服
     */
    KF_CREATE_SESSION("kf_create_session", "接入会话"),
    KF_CLOSE_SESSION("kf_close_session", "关闭会话"),
    KF_SWITCH_SESSION("kf_switch_session", "转接会话"),
    
    /**
     * 关注
     */
    SUBSCRIBE("subscribe", "关注"),
    UNSUBSCRIBE("unsubscribe", "取消关注"),
    
    /**
     * 上报地理位置
     */
    LOCATION("LOCATION", "上报地理位置"),
    
    /**
     * 群发
     */
    MASSSENDJOBFINISH("MASSSENDJOBFINISH", "群发"),
    
    /**
     * 菜单点击
     */
    CLICK("CLICK", "菜单拉取消息"),
    VIEW("VIEW", "菜单跳转链接"),
    
    /**
     * 扫描带参数二维码
     */
    SCAN("SCAN", "扫描带参数二维码"),
    
    /**
     * 模版消息发送状态
     */
    TEMPLATESENDJOBFINISH("TEMPLATESENDJOBFINISH", "模版消息发送状态");
    
    private EventType(String flag, String msg) {
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

    public static EventType get(String flag) {
        for (EventType type : EventType.values()) {
            if (type.flag.equals(flag)) {
                return type;
            }
        }
        return null;
    }
}
