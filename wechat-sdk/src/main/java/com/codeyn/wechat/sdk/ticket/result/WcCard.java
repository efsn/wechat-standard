package com.codeyn.wechat.sdk.ticket.result;

import com.codeyn.wechat.sdk.base.model.WcResult;

public class WcCard extends WcResult{

    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    
}
