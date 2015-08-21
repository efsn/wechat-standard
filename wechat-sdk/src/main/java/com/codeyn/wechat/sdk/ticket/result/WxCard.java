package com.codeyn.wechat.sdk.ticket.result;

import com.codeyn.wechat.sdk.base.model.WxResult;

public class WxCard extends WxResult{

    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    
}
