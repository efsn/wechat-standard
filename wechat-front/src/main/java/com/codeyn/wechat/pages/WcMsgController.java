package com.codeyn.wechat.pages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.codeyn.jfinal.annos.JFinalAnnos.Route;
import com.codeyn.wechat.jfinal.base.MsgController;
import com.codeyn.wechat.sdk.msg.model.receive.EventMsg;
import com.codeyn.wechat.sdk.msg.model.receive.NormalMsg;
import com.codeyn.wechat.sdk.msg.result.TemplateMsgResult;
import com.codeyn.wechat.service.user.FansService;
import com.codeyn.wechat.wc.template.TemplateMsgSender;
import com.codeyn.wechat.wc.template.WcMsgSenderFactory;
import com.codeyn.wechat.wc.template.WcMsgType;

/**
 * 微信消息控制器
 * 
 * @author Arthur
 * 
 */
@Route("/msg")
public class WcMsgController extends MsgController {

    @Override
    protected void processTextMsg(NormalMsg msg) {
        
    }

    @Override
    protected void processSubcribleEvent(EventMsg msg) {
        
    }

    @Override
    protected void processUnSubcribleEvent(EventMsg msg) {
        FansService.unUubscribe(msg.getFromUserName());
    }

    /**
     * 发送微信消息接口
     */
    public void sendTemplateMsg() {
        TemplateMsgSender wxMsgSender = WcMsgSenderFactory.createSender(WcMsgType.get(getParaToInt("msgType")));
        TemplateMsgResult result = wxMsgSender.sendMsg(getPara("tenantId"), getPara("memberId"),
                parseParaMap(getParaMap()));
        if (result != null && result.isSuccess()) {
            renderSucResult();
        } else {
            renderFailResult(result != null ? result.getErrmsg() : "内部服务异常");
        }
    }

    private Map<String, Object> parseParaMap(Map<String, String[]> map) {
        Map<String, Object> params = new HashMap<>();
        if (!map.isEmpty()) {
            for (Iterator<Map.Entry<String, String[]>> itr = map.entrySet().iterator(); itr.hasNext();) {
                Map.Entry<String, String[]> entry = itr.next();
                if (entry.getKey() != null) {
                    params.put(entry.getKey(), entry.getValue()[0]);
                }
            }
        }
        return params;
    }
}
