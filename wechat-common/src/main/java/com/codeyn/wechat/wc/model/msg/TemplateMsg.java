package com.codeyn.wechat.wc.model.msg;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.jfinal.plugin.activerecord.Model;

@ModelMapping("wc_template_msg")
public class TemplateMsg extends Model<TemplateMsg> {

    private static final long serialVersionUID = 1L;

    public static final TemplateMsg dao = new TemplateMsg();

    public TemplateMsg getTemplateMsgByOriginalId(String tenantId, String originalId) {
        String sql = "select * from wc_template_msg where tenant_id = ? and original_id = ? order by id desc";
        return dao.findFirst(sql, originalId);
    }
}
