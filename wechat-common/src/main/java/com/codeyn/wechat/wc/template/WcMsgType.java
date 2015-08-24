package com.codeyn.wechat.wc.template;

public enum WcMsgType {

    APPLY_STATUS(6, "活动报名状态"),

    COUPON_USE(5, "优惠券核销通知"),

    COUPON_TIMEOUT(4, "优惠券过期"),

    EXPENSE_SUCCESS(3, "消费成功"),

    COUPON_GIVE(2, "优惠券赠送"),

    POINT_CHANGE(1, "积分变更");

    private WcMsgType(Integer flag, String title) {
        this.flag = flag;
        this.title = title;
    }

    private Integer flag;

    private String title;

    public Integer getValue() {
        return flag;
    }

    public String getTitle() {
        return title;
    }

    public static WcMsgType get(Integer flag) {
        for (WcMsgType temp : WcMsgType.values()) {
            if (temp.flag.equals(flag)) {
                return temp;
            }
        }
        return null;
    }
}
