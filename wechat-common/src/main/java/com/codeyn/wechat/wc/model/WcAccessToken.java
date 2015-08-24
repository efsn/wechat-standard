package com.codeyn.wechat.wc.model;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.jfinal.plugin.activerecord.Model;

@ModelMapping("wc_token_access")
public class WcAccessToken extends Model<WcAccessToken> {

    private static final long serialVersionUID = 1L;

    public static final WcAccessToken dao = new WcAccessToken();

    public WcAccessToken getToken(String tenantId) {
        String sql = "select * from wx_token where tenant_id = ?";
        return dao.findFirst(sql, tenantId);
    }

}
