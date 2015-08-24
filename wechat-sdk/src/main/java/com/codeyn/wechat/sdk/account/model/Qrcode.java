package com.codeyn.wechat.sdk.account.model;

import com.codeyn.wechat.sdk.base.model.WcResult;

public class Qrcode extends WcResult{

    /**
     * 凭借此ticket可以在有效时间内换取二维码
     */
    private String ticket;
    
    /**
     * 二维码图片解析后的地址
     */
    private String url;
    
    private String short_url;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }
    
}
