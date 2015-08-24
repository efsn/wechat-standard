package com.codeyn.wechat.wc.model.msg;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.jfinal.plugin.activerecord.Model;

/**
 * @author Arthur
 */
@ModelMapping("wc_event_response")
public class WcEventResponse extends Model<WcEventResponse> {

    private static final long serialVersionUID = -9062454583104054955L;

    public static final WcEventResponse me = new WcEventResponse();

    public WcEventResponse getResponse(String tenantId, String key) {
        String sql = "select * from wc_event_response where tenant_id = ? and key = ?";
        return findFirst(sql, tenantId, key);
    }

}
