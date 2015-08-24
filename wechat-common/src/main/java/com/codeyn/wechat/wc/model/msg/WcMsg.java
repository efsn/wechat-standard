package com.codeyn.wechat.wc.model.msg;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@ModelMapping("wx_msg")
public class WcMsg extends Model<WcMsg> {

    private static final long serialVersionUID = 1L;

    public static final WcMsg dao = new WcMsg();

    public Page<WcMsg> getPagationList(String tenantId, int pageNumber, int pageSize, String contentKeys) {
        String select = "select wx_msg.*,wx_fans.nickname,wx_fans.headimgurl ";
        StringBuilder sqlBuilder = new StringBuilder(
                "from wx_msg left join wx_fans on wx_msg.fromuser=wx_fans.id where 1=1 and msg_type='text' ");
        List<Object> params = new ArrayList<>();
        if (StringUtils.hasText(tenantId)) {
            sqlBuilder.append(" and wx_msg.tenant_id=? ");
            params.add(tenantId);
        }
        if (StringUtils.hasText(contentKeys)) {
            sqlBuilder.append(" and wx_msg.msg_content like ? ");
            params.add("%" + contentKeys + "%");
        }
        sqlBuilder.append(" order by wx_msg.id desc");
        return dao.paginate(pageNumber, pageSize, select, sqlBuilder.toString(), params.toArray());
    }

    /**
     * 这里只查询了用户的openid
     * 
     * @param tenantId
     * @return
     */
    public List<WcMsg> getAccessUser(String tenantId, Timestamp startTime) {
        String select = "select distinct fromuser from wx_msg where tenant_id=? and create_time>?";
        return dao.find(select, tenantId, startTime);
    }
}
