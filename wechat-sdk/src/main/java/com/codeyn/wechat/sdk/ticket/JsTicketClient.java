package com.codeyn.wechat.sdk.ticket;

import java.util.Map;

import com.codeyn.wechat.sdk.base.WxClient;
import com.codeyn.wechat.sdk.base.model.WxBase;
import com.codeyn.wechat.sdk.ticket.result.WxCard;

/**
 * 微信卡券接口签名凭证api_ticket
 */
public class JsTicketClient extends WxClient{
    
    public JsTicketClient(WxBase wxBase) {
        super(wxBase);
    }

    public WxCard getTicket(final String accessToken) {
        return doGet(WxCard.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/cgi-bin/ticket/getticket");
    }
    
}
