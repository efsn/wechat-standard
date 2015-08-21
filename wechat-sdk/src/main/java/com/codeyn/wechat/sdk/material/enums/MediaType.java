package com.codeyn.wechat.sdk.material.enums;


/**
 * 媒体文件类型
 * @author Arthur
 */
public enum MediaType {

    IMAGE("image", "图片"),
    
    VOICE("voice", "语音"),
    
    VIDEO("video", "视频"),
    
    THUMB("thumb", "缩略图");
    
    private MediaType(String flag, String msg) {
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

    public static MediaType get(String flag) {
        for (MediaType type : MediaType.values()) {
            if (type.flag.equals(flag)) {
                return type;
            }
        }
        return null;
    }
    
}
