package com.codeyn.wechat.sdk.base.enums;

/**
 * 消息加密模式
 * @author Arthur
 */
public enum EncryptType {

    /**
     * 明文模式下，不使用消息体加解密功能，安全系数较低
     */
    DIRECT(0, "明文模式"),
    
    /**
     * 兼容模式下，明文、密文将共存，方便开发者调试和维护
     */
    MIX(-1,"混合模式"),
    
    /**
     * 安全模式下，消息包为纯密文，需要开发者加密和解密，安全系数高
     */
    SAFE(1,"安全模式");

    private EncryptType(Integer flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    private Integer flag;

    private String msg;

    public Integer getFlag() {
        return flag;
    }

    public String getMsg() {
        return msg;
    }

    public static EncryptType get(Integer flag) {
        for (EncryptType type : EncryptType.values()) {
            if (type.flag.equals(flag)) {
                return type;
            }
        }
        return null;
    }
    
}
