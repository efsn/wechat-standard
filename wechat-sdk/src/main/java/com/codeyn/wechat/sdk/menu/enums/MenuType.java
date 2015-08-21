package com.codeyn.wechat.sdk.menu.enums;

/**
 * 事件推送类型
 * @author Arthur
 *
 *
 */
public enum MenuType {
    
    CLICK("CLICK", "点击推事件"),
    
    VIEW("VIEW", "跳转URL"),
    
    /**
     * 以下3~8仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户
     */
    
    SCAN("scancode_push", "扫码推事件"),
    
    SCAN_MSG("scancode_waitmsg", "扫码推事件且弹出提示框"),
    
    PHONO("pic_sysphoto", "弹出系统拍照发图"),
    
    PHONO_ALBUM("pic_photo_or_album", "弹出拍照或者相册发图"),
    
    PIC_WX("pic_weixin", "弹出微信相册发图器"),
    
    LOCATION("location_select", "弹出地理位置选择器"),
    
    
    /**
     * 以下是专门给第三方平台旗下未微信认证的订阅号准备的事件类型，
     * 它们是没有事件推送的，能力相对受限，其他类型的公众号不必使用。
     */
    
    /**
     * 下发消息（除文本消息）
     */
    MEDIA("media_id", "下发消息"),
    
    /**
     * 永久素材id对应的图文消息URL
     */
    VIEW_LIMITED("view_limited", "跳转图文消息URL");
    
    private MenuType(String flag, String msg) {
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

    public static MenuType get(String flag) {
        for (MenuType type : MenuType.values()) {
            if (type.flag.equals(flag)) {
                return type;
            }
        }
        return null;
    }
}
