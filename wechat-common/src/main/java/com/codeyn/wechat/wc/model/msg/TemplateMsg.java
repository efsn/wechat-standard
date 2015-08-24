package com.codeyn.wechat.wc.model.msg;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.jfinal.plugin.activerecord.Model;

@ModelMapping("wx_template_msg")
public class TemplateMsg extends Model<TemplateMsg> {

    private static final long serialVersionUID = 1L;

    public static final TemplateMsg dao = new TemplateMsg();

    public TemplateMsg getTemplateMsgByOriginalId(String tenantId, String originalId) {
        String sql = "select * from wx_template_msg where tenant_id = ? and original_id = ? order by id desc";
        return dao.findFirst(sql, tenantId, originalId);
    }
}
