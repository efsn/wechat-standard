package com.codeyn.wechat.wc.template;

public class WcMsgSenderFactory {

    private static TemplateMsgSender pointChangeMsgSender = new PointChangeMsgSender();

    private static TemplateMsgSender couponGiveMsgSender = new CouponGiveMsgSender();

    private static TemplateMsgSender expenseSuccessSender = new ExpenseSuccessMsgSender();

    private static TemplateMsgSender couponTimeoutSender = new CouponTimeoutMsgSender();

    private static TemplateMsgSender couponUseSender = new CouponUseMsgSender();

    private static TemplateMsgSender applyStatusSender = new ApplyStatusMsgSender();

    public static TemplateMsgSender createSender(WcMsgType msgType) {

        switch (msgType) {
            case POINT_CHANGE:
                return pointChangeMsgSender;
            case COUPON_GIVE:
                return couponGiveMsgSender;
            case EXPENSE_SUCCESS:
                return expenseSuccessSender;
            case COUPON_TIMEOUT:
                return couponTimeoutSender;
            case COUPON_USE:
                return couponUseSender;
            case APPLY_STATUS:
                return applyStatusSender;
            default:
        }
        return null;
    }
}
