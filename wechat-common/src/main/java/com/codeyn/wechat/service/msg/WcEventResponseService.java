package com.codeyn.wechat.service.msg;

import com.codeyn.wechat.wc.model.msg.WcEventResponse;

/**
 * @author Arthur
 */
public class WcEventResponseService {

    public static WcEventResponse getResponse(String tenantId, String key) {
        return WcEventResponse.me.getResponse(tenantId, key);
    }

}
