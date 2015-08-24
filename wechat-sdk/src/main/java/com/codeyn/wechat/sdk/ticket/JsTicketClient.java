package com.codeyn.wechat.sdk.ticket;

import java.util.Map;

import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.ticket.result.WcCard;

/**
 * 微信卡券接口签名凭证api_ticket
 */
public class JsTicketClient extends WcClient{
    
    public JsTicketClient(WcBase wxBase) {
        super(wxBase);
    }

    public WcCard getTicket(final String accessToken) {
        return doGet(WcCard.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/cgi-bin/ticket/getticket");
    }
    
}
