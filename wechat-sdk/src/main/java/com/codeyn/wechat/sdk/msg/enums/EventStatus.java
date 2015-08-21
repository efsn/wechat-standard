package com.codeyn.wechat.sdk.msg.enums;

public enum EventStatus {

    // 群发
    MASS_SUCCESS("sendsuccess", "群发成功"),
    MASS_FAIL("sendfail", "群发失败"),
    
    /**
     * 模版消息
     */
    TMP_SUCCESS("success", "成功"),
    TMP_BLOCK("block", "用户拒绝接收"),
    TMP_FAILED("failed: system failed", "发送失败（非用户拒绝）");
    
    private EventStatus(String flag, String msg) {
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

    public static EventStatus get(String flag) {
        for (EventStatus type : EventStatus.values()) {
            if (type.flag.equals(flag)) {
                return type;
            }
        }
        return null;
    }
    
}
